package com.study.koreait.repository;

import com.study.koreait.entity.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findAllPosts();

    Post findPostById(int id);

}
