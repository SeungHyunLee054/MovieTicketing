package com.zerobase.controller;

import com.zerobase.service.KobisOpenAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class KobisOpenApiController {
    private final KobisOpenAPIService kobisOpenAPIService;

    @GetMapping("/get")
    public ResponseEntity<String> getMovies() {
        kobisOpenAPIService.getMoviesFromApi();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }
}
