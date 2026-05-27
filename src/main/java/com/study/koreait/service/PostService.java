package com.study.koreait.service;

import com.study.koreait.dto.AddPostReqDto;
import com.study.koreait.entity.Post;
import com.study.koreait.mapper.PostMapper;
import com.study.koreait.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public List<Post> getAllPosts(){
        return repository.findAllPosts();
    }

    public Post getPostById(int id){

        return repository.findPostById(id);
    }

    public int addPost(AddPostReqDto dto){
        return repository.insertPost(dto.toEntity());
    }

    public int removePost(int id){
        return repository.deletePostById(id);
    }

    // 추후에 dto로 교체
    public List<Post> getPostWithComments(){
        return mapper.findAllPostsWithComments();
    }
}
