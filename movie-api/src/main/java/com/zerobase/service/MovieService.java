package com.zerobase.service;

import com.zerobase.domain.model.Movie;
import com.zerobase.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final KobisOpenAPIService kobisOpenAPIService;
    private final MovieRepository movieRepository;

    public void saveMovie() {
        int totalPage = kobisOpenAPIService.totalCnt(kobisOpenAPIService.getMovies("1"));
        int pageEnd = totalPage / 100;
        for (int i = 1; i <= pageEnd + 1; i++) {
            String curPage = String.valueOf(i);
            List<Movie> movies = kobisOpenAPIService.parseMovies(kobisOpenAPIService.getMovies(curPage));
            for (Movie movie : movies) {
                if (movieRepository.findByMovieCd(movie.getMovieCd()).isPresent()) {
                    continue;
                }
                movieRepository.save(movie);
            }
        }
    }
}
