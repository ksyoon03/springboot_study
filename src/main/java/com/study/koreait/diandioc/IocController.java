package com.study.koreait.diandioc;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ioc")
@Data   // RequiredArgsConstructor
public class IocController {
    private final IocService service;

    // 생성자 하나일 경우 Autowired 생략 가능
    // lombok의 @RequiredConstructor와 같이 쓰임
//    public IocController(IocService service){
//        this.service = service;
//    }

    @GetMapping("/test")
    public ResponseEntity<?> diTest(){
        Integer total = service.getTotal();
        Double avg = service.getAvg();
        Map<String, Object> resMap = Map.of(
                "total", total,
                "average", avg
        );
        return ResponseEntity.ok(resMap);
    }


}
