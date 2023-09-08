package com.zerobase.controller;

import com.zerobase.domain.response.movie.detail.MovieDetailDto;
import com.zerobase.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/save")
    public ResponseEntity<String> getMovies() {
        movieService.saveMovies();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }

    @GetMapping("/search/detail")
    public ResponseEntity<MovieDetailDto> searchMovieDetail(@RequestParam String movieCd) {
        return ResponseEntity.ok(movieService.searchMovieDetail(movieCd));
    }

    @PostMapping("/open/save")
    public ResponseEntity<String> saveOpenMovies() {
        movieService.saveOpenMovies();
        return ResponseEntity.ok("상영중 영화 데이터 저장 성공");
    }

    @PostMapping("/open/delete")
    public ResponseEntity<String> deletePastOpenMovie() {
        movieService.deletePastOpenMovies();
        return ResponseEntity.ok("상영 종료 영화 데이터 삭제 성공");
    }
}
