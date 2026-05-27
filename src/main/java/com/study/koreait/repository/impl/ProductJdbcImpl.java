package com.study.koreait.repository.impl;

import com.study.koreait.entity.Product;
import com.study.koreait.exception.ProductException;
import com.study.koreait.model.Top3SellingProduct;
import com.study.koreait.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary // 같은 타입의 bean이 여러개일 때, "이걸 먼저 주입하라"라는 우선권 부여
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductJdbcImpl implements ProductRepository {

    // spring-boot-starter-jdbc 라이브러리가 bean 등록까지 해줌
    // yaml에 작성했던 DataSource를 주입해줌
    private final DataSource dataSource;

    // rs를 Product 객체로 조립해주는 메서드
    private Product rsToProduct(ResultSet rs) throws SQLException {
        return Product.builder()
                .productId(rs.getInt("product_id"))
                .productName(rs.getString("product_name"))
                .price(rs.getInt("price"))
                .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                .build();
    }

    // 자원반납을 하는 메서드
    // 매개변수로 들어오는 타입은 같은데 실제 동작이 각각 다른 것 -> 다형성
    // 인터페이스 타입으로 캐스팅해서 타입을 통일하고, 오버라이딩된 메서드가 호출됨
    private void close(AutoCloseable ac){
        if(ac != null){
            try{
                ac.close();
            } catch(Exception e){
                String className = ac.getClass().getSimpleName();
                log.error("자원 반납 실패 = {}", className, e);
            }
        }
    }

    @Override
    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM product";

        // jdbc 사용시 중요했던 3가지 객체
        Connection conn = null; // DB와의 연결을 빌려오는 객체
        PreparedStatement ps = null; // 쿼리 조립 & 실행하는 객체
        ResultSet rs = null; // 쿼리 결과를 받아주는 객체
        List<Product> products = new ArrayList<>();

        try {
            conn = dataSource.getConnection(); // 빌려오기
            ps = conn.prepareStatement(sql); // sql 준비
            rs = ps.executeQuery(); // 쿼리 실행 -> ResultSet으로 결과 받아오기

            // rs는 필드로 전체 테이블 값을 가지고 있음
            // 한 줄씩 꺼내와서 보여준다
            // rs.next(): 다음 row가 있으면 true, 없으면 false
            // true를 리턴한다면 다음행으로 '커서' 이동
            while (rs.next()) {
                Product p = rsToProduct(rs);
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            log.info("findAllProducts 실패: {}", e);
            throw new ProductException("상품 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            // 자원 반납
            // 가져온 순서의 역순으로 반납
            close(rs);
            close(ps);
            close(conn);
        }
    }

    // 단건 조회
    @Override
    public Product findProductById(int id) {
        // sql injection 방어를 위해 미리 준비되어 있는 메서드를 사용한다.
        String sql = "SELECT * FROM product WHERE product_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            // sql 문자열에서 왼쪽부터 읽었을 때 1번째 발견되는 ?에 id값을 넣으세요
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                // 데이터 있음
                return rsToProduct(rs);
            } else {
                // 데이터 없음
                throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            log.info("findAllProducts 실패: id = {}", id, e);
            throw new ProductException("상품 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.info("rs 반납 실패", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.info("ps 반납 실패", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.info("conn 반납 실패", e);
                }
            }
        }
    }

    @Override
    public int insertProduct(Product product) {
        String sql = "INSERT INTO product (product_name, price) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getPrice());

            // SELECT가 아닌 쿼리들은 영향 받은 row의 숫자를 리턴
            // int를 리턴하는 executeUpdate를 사용
            int successCount = ps.executeUpdate();
            if(successCount == 0){
                throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
            return successCount;
        } catch(SQLException e){
            log.error("insertProduct 실패 - {}", product, e);
            throw new ProductException("상품 등록 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            close(ps);
            close(conn);
        }
    }

    @Override
    public int deleteProductByID(int id) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            int successCount = ps.executeUpdate();
            if(successCount == 0){
                throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
            return successCount;
        } catch(SQLException e){
            log.error("deleteProductByID 실패 - {}", id, e);
            throw new ProductException("상품 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally{
            close(ps);
            close(conn);
        }
    }

    @Override
    public int updateProductById(Product product) {
        // StringBuilder 사용
        // 문자열 덧셈 -> 새로운 문자열 객체 생성
        // StringBuilder는 기존 객체를 변형하는 것
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE product ");
        sb.append("SET product_name = ?, price = ? ");
        sb.append("WHERE product_id = ? ");

        String sql = sb.toString();

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getProductId());

            int successCount = ps.executeUpdate();
            if(successCount == 0){
                throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
            }
            return successCount;
        }catch(SQLException e){
            log.error("updateProductById 실패 - {}", product, e);
            throw new ProductException("상품 수정 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            close(ps);
            close(conn);
        }
    }

    @Override
    public List<Product> searchProductByName(String name) {
        String sql = "SELECT * FROM product WHERE product_name LIKE ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            // PreparedStatement를 사용하는 이유
            // 1. sql injection 공격 방어
            // 2. 이스케이프 문제로부터 자유롭기 위해
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                products.add(rsToProduct(rs));
            }
            return products;
        } catch (SQLException e){
            log.info("searchProductByName 실패: name = {}", name, e);
            throw new ProductException("상품 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.info("rs 반납 실패", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.info("ps 반납 실패", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.info("conn 반납 실패", e);
                }
            }
        }
    }

    // join한 데이터를 받아오는 2가지 방법
    // 1. 그래프 탐색으로 받아오는 방법
    // 2. model을 정의해서 받아오는 방법
    public List<Top3SellingProduct> findTop3Products(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.product_id, p.product_name, SUM(od.quantity) AS total_sold_count ");
        sb.append("FROM product p ");
        sb.append("INNER JOIN order_details od ");
        sb.append("ON p.product_id = od.product_id ");
        sb.append("GROUP BY p.product_id, p.product_name ");
        sb.append("ORDER BY total_sold_count DESC ");
        sb.append("LIMIT 3 ");

        String sql = sb.toString();

        List<Top3SellingProduct> models = new ArrayList<>();

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                int totalSoldCount = rs.getInt("total_sold_count");

                models.add(new Top3SellingProduct(id, name, totalSoldCount));
            }
            return models;
        }catch(SQLException e){
            log.info("findTop3Products 실패", e);
            throw new ProductException("상품 조회 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            close(rs);
            close(ps);
            close(conn);
        }
    }
}
