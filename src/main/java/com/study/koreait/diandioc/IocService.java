package com.study.koreait.diandioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IocService {
    // @Autowired - 호출될 때 의존성 주입
    private IocRepository repository;

    // IocRepository repo = new IocRepository();
    // IocServce service = new IocService(repo);
    @Autowired  // Bean 생성 시 의존성 주입
    public IocService(IocRepository repository){
        this.repository = repository;
    }
    /*
        A는 B를 필드로 가짐
        B는 A를 필드로 가짐
        A를 만드려고 하니 B가 필요 -> B 생성
        B를 만드려고 하니 A가 필요 -> A 생성 ... 반복
     */

    public int getTotal(){
        int total = 0;
        for(Integer score : repository.getScores()){
            total += score;
        }
        return total;
    }

    public double getAvg(){
        List<Integer> scores = repository.getScores();
        return (double) getTotal() / scores.size();
    }
}
