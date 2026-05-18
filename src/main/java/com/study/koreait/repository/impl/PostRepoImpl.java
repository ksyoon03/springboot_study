package com.study.koreait.repository.impl;

import com.study.koreait.entity.Post;
import com.study.koreait.exception.ProductException;
import com.study.koreait.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepoImpl implements PostRepository {

    // DB 대용
    private final List<Post> posts = new ArrayList<>(
            Arrays.asList(
                    Post.builder().postId(1).title("1번 게시물").content("1빠").build(),
                    Post.builder().postId(2).title("2번 게시물").content("2빠").build(),
                    Post.builder().postId(3).title("3번 게시물").content("3빠").build(),
                    Post.builder().postId(4).title("4번 게시물").content("4빠").build()
            )
    );

    // 전체 리턴
    @Override
    public List<Post> findAllPosts() {
        return posts;
    }

    // id로 리턴
    @Override
    public Post findPostById(int id) {
        Optional<Post> optPost = posts.stream()
                .filter(p -> p.getPostId() == id)
                .findFirst();

        if(optPost.isEmpty()){
            throw new ProductException("해당 id의 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        return optPost.get();
    }
}
