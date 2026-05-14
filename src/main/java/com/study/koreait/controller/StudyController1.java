package com.study.koreait.controller;

import com.study.koreait.dto.StudentResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// RestController 역할 부여 - json만 조달
// Controller - html 파일을 조달
@RestController
@Slf4j  // 로깅용 어노테이션
@RequestMapping("/study")
public class StudyController1 {
    // 톰캣의 주소: localhost:8080
    /*
     * Http 통신 - 웹에서 데이터를 주고 받는 규칙
     * 1. 요청/응답에 대한 자바 객체 존재
     * 2. 요청의 경우, 메서드 존재
     * - GET: 데이터 조회 요청 (body가 없음, url의 쿼리스트링으로 전달)
     * - POST: 데이터 생성 요청
     * - DELETE: 데이터 삭제 요청
     * - PUT / PATCH: 데이터 수정 요청
     * */

    // localhost:8080/study/test1
    // 해당 주소로 GET 요청 시 실행되는 메서드
    @GetMapping("/test1")
    public String test1(){
        log.info("test1 컨트롤러 수신");
        return "양호합니다";
    }

    // 쿼리스트링으로 데이터 전달받기
    // localhost:8080/study/test2?name=홍길동&age=20
    @GetMapping("/test2")
    public Map<String, Integer> test2(@RequestParam("name") String str, Integer age){
        log.info("test2 수신 완료");
        log.info("들어온 데이터: {}, {}", str, age);
        // 일반적으로 JSON <-> 자바 객체 or Map
        return Map.of(str, age);
    }

    // @PathVariable: {경로변수}와 매개변수가 이름이 같으면 (" ")를 생략 가능
    @GetMapping("/test3/{number}")
    public Map<String, Object> getStudentById(@PathVariable("number") int id){
        List<StudentResDto> dtos = List.of(
                new StudentResDto(1, "피카츄", 10),
                new StudentResDto(2, "라이츄", 20),
                new StudentResDto(3, "파이리", 30),
                new StudentResDto(4, "꼬부기", 40)
        );

        Optional<StudentResDto> optDto = dtos.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        if(optDto.isEmpty()){
            return Map.of("error", "해당 id 학생은 존재하지 않음");
        }

        StudentResDto target = null;
        for(StudentResDto dto : dtos){
            if(dto.getId() == id) {
                target = dto;
                break;
            }
        }

        if(target == null){
            return Map.of("error", "해당 id 학생은 존재하지 않음");
        }

        return Map.of("success", target);
    }
}
