package com.zerobase.controller;

import com.zerobase.domain.response.movie.detail.MovieDetailDto;
import com.zerobase.elasticsearch.MovieResponse;
import com.zerobase.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<String> getMovies() {
        movieService.saveMovies();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }

    @GetMapping("/detail")
    public ResponseEntity<MovieDetailDto> searchMovieDetail(@RequestParam String movieCd) {
        return ResponseEntity.ok(movieService.searchMovieDetail(movieCd));
    }

    @PostMapping("/open")
    public ResponseEntity<String> saveOpenMovies() {
        movieService.saveOpenMovies();
        return ResponseEntity.ok("상영중 영화 데이터 저장 성공");
    }

    @DeleteMapping("/open")
    public ResponseEntity<String> deletePastOpenMovie() {
        movieService.deletePastOpenMovies();
        return ResponseEntity.ok("상영 종료 영화 데이터 삭제 성공");
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<MovieResponse>> search(@PathVariable String name, Pageable pageable) {
        List<MovieResponse> userResponses = movieService.searchMovieByName(name, pageable)
                .stream()
                .map(MovieResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }
}
