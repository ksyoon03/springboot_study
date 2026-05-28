package com.study.koreait.service;

import com.study.koreait.dto.AddPostReqDto;
import com.study.koreait.dto.FindPostResDto;
import com.study.koreait.dto.SearchPostReqDto;
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
        return mapper.insertPost(dto.toEntity());
    }

    public int removePost(int id){
        return mapper.deletePostById(id);
    }

    // 추후에 dto로 교체
    public List<Post> getPostWithComments(){
        return mapper.findAllPostsWithComments();
    }

    public List<FindPostResDto> searchDynamicPosts(SearchPostReqDto dto){
        return mapper.detailSearchPosts(dto.getTitleKeyword(), dto.getContentKeyword())
                .stream()
                .map(Post::toFindPostResDto)
                .toList();
    }

    public int addBulkPosts(List<AddPostReqDto> dtos){
        return mapper.insertPosts(dtos.stream().map(AddPostReqDto::toEntity).toList());
    }
}
