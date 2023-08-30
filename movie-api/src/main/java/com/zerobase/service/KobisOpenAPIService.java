package com.zerobase.service;

import com.zerobase.domain.model.Movie;
import com.zerobase.domain.response.movie.detail.MovieDetailDto;
import com.zerobase.domain.response.movie.detail.RootDetail;
import com.zerobase.domain.response.movie.list.MovieDto;
import com.zerobase.domain.response.movie.list.MovieListResult;
import com.zerobase.domain.response.movie.list.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class KobisOpenAPIService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${API_BASE_URL}")
    private String API_BASE_URL;
    @Value("${API_MOVIES_URL}")
    private String API_MOVIES_URL;
    @Value("${API_MOVIE_DETAIL_URL}")
    private String API_MOVIE_DETAIL_URL;
    private final String KEY = System.getProperty("apiKey");

    private MovieListResult getMovies(String page) {
        String strUrl = API_BASE_URL
                + API_MOVIES_URL
                + KEY
                + "&curPage=" + page
                + "&itemPerPage=100";

        return Objects.requireNonNull(restTemplate.getForObject(strUrl, Root.class))
                .getMovieListResult();
    }

    public List<Movie> getMovieList(String page) {
        return getMovies(page).getMovieList().stream()
                .map(MovieDto::from)
                .toList();
    }

    public MovieDetailDto getMovieDetail(String movieCd) {
        String strUrl = API_BASE_URL
                + API_MOVIE_DETAIL_URL
                + KEY
                + "&movieCd=" + movieCd;

        return Objects.requireNonNull(restTemplate.getForObject(strUrl, RootDetail.class))
                .getMovieInfoResult().getMovieInfo();
    }

    public int totalCnt() {
        return Integer.parseInt(getMovies("1").getTotCnt());
    }
}