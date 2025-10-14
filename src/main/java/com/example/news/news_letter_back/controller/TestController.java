package com.example.news.news_letter_back.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "테스트 입니다.";
    }
}
