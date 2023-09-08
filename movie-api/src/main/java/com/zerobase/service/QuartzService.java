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
public class QuartzService {
    private final OpenMovieRepository openMovieRepository;
    private final MovieRepository movieRepository;
    private final KobisOpenAPIService kobisOpenAPIService;
    private final RedisClient redisClient;

    public void deletePastOpenMovies() {
        List<OpenMovie> pastOpenMovies = openMovieRepository.findAllByOpenDtBefore(LocalDate.now().minusMonths(1));
        if (!pastOpenMovies.isEmpty()) {
            for (OpenMovie o : pastOpenMovies) {
                if (!ObjectUtils.isEmpty(redisClient.get(o.getMovieCd(), MovieDetailDto.class))) {
                    redisClient.delete(o.getMovieCd());
                    log.info("redis 서버에 해당 영화의 상세정보가 존재하므로 삭제하였습니다. 해당 영화 제목 -> {}", o.getMovieName());
                }
                openMovieRepository.deleteByMovieCd(o.getMovieCd());
            }
            log.info("{}개의 영화가 상영 종료되었으므로 DB에서 삭제되었습니다.", pastOpenMovies.size());

        } else {
            log.info("상영 종료된 영화가 없습니다.");
        }
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
            log.info("중복키 존재, 해당 값 수정");
            for (OpenMovie openMovie : openMovies) {
                OpenMovie movie = openMovieRepository.findByMovieCd(openMovie.getMovieCd())
                        .orElse(openMovie);
                movie.setMovieName(openMovie.getMovieName());
                movie.setMovieNameEn(openMovie.getMovieNameEn());
                movie.setPrdtYear(openMovie.getPrdtYear());
                movie.setOpenDt(openMovie.getOpenDt());
                movie.setTypeName(openMovie.getPrdtStatName());
                movie.setNationAlt(openMovie.getNationAlt());
                movie.setGenreAlt(openMovie.getGenreAlt());
                movie.setDirectorName(openMovie.getDirectorName());
                movie.setCompanyName(openMovie.getCompanyName());
                openMovieRepository.save(movie);
            }
        }
        log.info("총 {}개의 영화가 상영 중", openMovies.size());
    }

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
}