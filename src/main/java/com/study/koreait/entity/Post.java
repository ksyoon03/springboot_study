package com.study.koreait.entity;

import com.study.koreait.dto.FindPostResDto;
import com.study.koreait.dto.FindProductResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Post {
    private int postId;
    private String title;
    private String content;
    private LocalDateTime createAt;

    // comments는 post_id를 fk로 들고 있음
    // Post:Comments는 1:N 관계
    // 그래프? post.getcomments()
    private List<Comments> comments;

    public FindPostResDto toFindPostResDto(){
        return FindPostResDto.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}