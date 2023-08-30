package com.zerobase.controller;

import com.zerobase.domain.MovieDetailDto;
import com.zerobase.domain.model.MovieDetail;
import com.zerobase.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/get")
    public ResponseEntity<String> getMovies() {
        movieService.saveMovie();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }
}
