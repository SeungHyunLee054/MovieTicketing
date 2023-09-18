package com.zerobase.service;

import com.zerobase.client.RedisClient;
import com.zerobase.domain.response.movie.search.MovieSearchResponse;
import com.zerobase.domain.model.Movie;
import com.zerobase.domain.model.MovieElastic;
import com.zerobase.domain.model.OpenMovie;
import com.zerobase.domain.repository.MovieRepository;
import com.zerobase.domain.repository.MovieSearchRepository;
import com.zerobase.domain.repository.OpenMovieRepository;
import com.zerobase.domain.response.movie.detail.MovieDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final KobisOpenAPIService kobisOpenAPIService;
    private final MovieRepository movieRepository;
    private final RedisClient redisClient;
    private final OpenMovieRepository openMovieRepository;
    private final MovieSearchRepository movieSearchRepository;

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
                log.info("중복 키 존재, 해당 값 수정");
                for (Movie m : movies) {
                    Movie movie = movieRepository.findByMovieCd(m.getMovieCd())
                            .orElse(m);
                    movie.setMovieName(m.getMovieName());
                    movie.setMovieNameEn(m.getMovieNameEn());
                    movie.setPrdtYear(m.getPrdtYear());
                    movie.setOpenDt(m.getOpenDt());
                    movie.setTypeName(m.getPrdtStatName());
                    movie.setNationAlt(m.getNationAlt());
                    movie.setGenreAlt(m.getGenreAlt());
                    movie.setDirectorName(m.getDirectorName());
                    movie.setCompanyName(m.getCompanyName());

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

    public void deletePastOpenMovies() {
        List<OpenMovie> pastOpenMovies =
                openMovieRepository.findAllByOpenDtBefore(LocalDate.now()
                        .minusMonths(1));
        if (pastOpenMovies.isEmpty()) {
            log.info("상영 종료된 영화가 없습니다.");
            return;
        }

        for (OpenMovie o : pastOpenMovies) {
            if (!ObjectUtils.isEmpty(redisClient.get(o.getMovieCd(), MovieDetailDto.class))) {
                redisClient.delete(o.getMovieCd());
                log.info("redis 서버에 해당 영화의 상세정보가 존재하므로 삭제하였습니다. 해당 영화 제목 -> {}", o.getMovieName());
            }
        }
        openMovieRepository.deleteAll(pastOpenMovies);
        log.info("{}개의 영화가 상영 종료되었으므로 DB에서 삭제되었습니다.", pastOpenMovies.size());
    }

    public void movieToElastic() {
        List<Movie> movieList = movieRepository.findAll();
        List<MovieElastic> movieElastics = movieList.stream()
                .map(m -> MovieElastic.builder()
                        .id(m.getId())
                        .movieCd(m.getMovieCd())
                        .movieName(m.getMovieName())
                        .movieNameEn(m.getMovieNameEn())
                        .build())
                .collect(Collectors.toList());
        movieSearchRepository.saveAll(movieElastics);
        log.info("db의 데이터 elasticsearch에 저장 성공");
    }

    public MovieSearchResponse searchMovieByName(String name, int size, int idx) {
        Pageable pageable = PageRequest.of(idx, size);
        Page<MovieElastic> movieElasticPage = movieSearchRepository.findByMovieName(name, pageable);
        log.info("총 {}개의 데이터 검색", movieElasticPage.getSize());

        return new MovieSearchResponse(movieElasticPage.getContent());
    }
}
