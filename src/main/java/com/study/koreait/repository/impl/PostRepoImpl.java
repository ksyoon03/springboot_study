package com.study.koreait.repository.impl;

import com.study.koreait.entity.Post;
import com.study.koreait.exception.PostException;
import com.study.koreait.exception.ProductException;
import com.study.koreait.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
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

    @Override
    public int insertPost(Post post) {
        int maxId = 0;
        for(Post p : posts){
            int postId = p.getPostId();
            if(postId > maxId){
                maxId = postId;
            }
        }

        Post newPost = Post.builder()
                .postId(maxId)
                .title(maxId + "번 게시물")
                .content(maxId + "빠")
                .build();

        return 1;
    }

    @Override
    public int deletePostById(int id) {
        Optional<Post> optPost = posts.stream()
                .filter(p -> p.getPostId() == id)
                .findFirst();

        Post post = optPost.orElseThrow(() -> new PostException("해당 ID의 게시물은 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        posts.remove(id);
        log.info("게시물 삭제 완료: {}", post);

        return 1;
    }
}
