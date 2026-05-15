package com.study.koreait.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPostReqDto {
    // Validation 라이브러리
    @NotBlank(message = "제목은 비울 수 없습니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비울 수 없습니다.")
    @Size(max = 5000, message = "내용은 5000자를 초과할 수 없습니다.")
    private String content;

    /*
        1. 문자열
            @NotBlank - null, "", "   " 허용 X
            @NotEmpty - null, "" 허용 X / "   "은 허용
            @Size(min=, max=) 길이 제한
            @Email - 이메일 형식 검사
            @Pattern(regexp = "") - 정규식 검사

        2. 숫자
            @Min, @Max
            @Positive, @Negative

        3. 객체
            @Valid - 클래스 내부 객체를 검사
            @NotNull - null만 검사

        4. 날짜
            @Future - 미래 날짜만
            @Past - 과거 날짜만

     */

    // dto가 자동으로 생성될텐데,
    // 우리가 원하는 값이 아니면, 예의(Exception)을 일으킴.
}
