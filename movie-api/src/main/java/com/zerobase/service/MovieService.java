package com.zerobase.service;

import com.zerobase.client.RedisClient;
import com.zerobase.domain.model.Movie;
import com.zerobase.domain.model.OpenMovie;
import com.zerobase.domain.repository.MovieRepository;
import com.zerobase.domain.repository.OpenMovieRepository;
import com.zerobase.domain.response.movie.detail.MovieDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final KobisOpenAPIService kobisOpenAPIService;
    private final MovieRepository movieRepository;
    private final RedisClient redisClient;
    private final OpenMovieRepository openMovieRepository;

    public void saveMovies() {
        int totalPage = kobisOpenAPIService.totalCnt();
        int pageEnd = totalPage / 100;
        for (int i = 1; i <= pageEnd + 1; i++) {
            String curPage = String.valueOf(i);
            List<Movie> movies =
                    kobisOpenAPIService.getMovieList(curPage);

            try {
                movieRepository.saveAll(movies);
            } catch (Exception e) {
                log.info("중복 키 존재, 해당 값 제외 후 저장");
                for (Movie movie : movies) {
                    if (movieRepository.findByMovieCd(movie.getMovieCd()).isPresent()) {
                        continue;
                    }
                    movieRepository.save(movie);
                }
            }
        }
        log.info("총 {}개 정보 저장", totalPage);
    }

    public MovieDetailDto searchMovieDetail(String movieCd) {
        MovieDetailDto movieDetailDto = redisClient.get(movieCd, MovieDetailDto.class);
        if (movieDetailDto == null) {
            log.info("redis에 저장되지 않은 영화입니다.");
            movieDetailDto = kobisOpenAPIService.getMovieDetail(movieCd);

            if (openMovieRepository.findByMovieCd(movieCd).isPresent()) {
                log.info("상영중인 영화이므로 redis에 저장");
                redisClient.put(movieDetailDto.getMovieCd(), movieDetailDto);
            }
        } else {
            log.info("redis에 저장된 영화입니다.");
        }
        log.info("해당 코드에 대한 영화 제목 -> {}", movieDetailDto.getMovieNm());
        return movieDetailDto;
    }

    public void saveOpenMovies() {
        List<OpenMovie> openMovies = movieRepository.findAllByOpenDtBetween(
                        LocalDate.now().minusMonths(1), LocalDate.now())
                .stream()
                .map(Movie::from)
                .toList();

        try {
            openMovieRepository.saveAll(openMovies);
        } catch (Exception e) {
            for (OpenMovie openMovie : openMovies) {
                log.info("중복키 존재, 해당 값 수정");
                if (openMovieRepository.findByMovieCd(openMovie.getMovieCd()).isPresent()) {
                    openMovieRepository.deleteByMovieCd(openMovie.getMovieCd());
                }
                openMovieRepository.save(openMovie);
            }
        }
        log.info("총 {}개의 영화가 상영 중", openMovies.size());
    }
}
