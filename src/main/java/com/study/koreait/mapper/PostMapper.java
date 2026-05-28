package com.study.koreait.mapper;

import com.study.koreait.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 자바 버전 mapper
// PostMapper를 implements한 실제 클래스는
// mybatis 라이브러리가 서버 시작할 때 xml을 보고 알아서 bean 등록을 함

@Mapper
public interface PostMapper {
    List<Post> findAll();
    List<Post> findAllPostsWithComments();

    int insertPost(Post post);

    // 매개변수가 2개 이상일 경우 @Param() 작성 필요
    int deletePostById(int id);

    // <if>
    List<Post> detailSearchPosts(
            @Param("titleKeyword") String titleKeyword,
            @Param("contentKeyword") String contentKeyword
    );
    // foreach
    // 파라미터로 List 하나'만' 넣고 @Param을 안할 경우,
    // collection="list"로 인식시킬 수 있긴함
    // 두 개 이상일 경우엔 안됨!
    int insertPosts(@Param("posts") List<Post> posts);
}
