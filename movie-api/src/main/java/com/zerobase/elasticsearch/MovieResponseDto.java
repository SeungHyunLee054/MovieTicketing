package com.zerobase.elasticsearch;

import com.zerobase.domain.model.Movie;
import com.zerobase.domain.model.OpenMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MovieResponseDto {
    private String movieCd;
    private String movieName;
    private String movieNameEn;
    private String prdtYear;
    private LocalDate openDt;
    private String typeName;
    private String prdtStatName;
    private String nationAlt;
    private String genreAlt;
    private String directorName;
    private String companyName;

    public static MovieResponseDto from(Movie movie){
        return MovieResponseDto.builder()
                .movieCd(movie.getMovieCd())
                .movieName(movie.getMovieName())
                .movieNameEn(movie.getMovieNameEn())
                .prdtYear(movie.getPrdtYear())
                .openDt(movie.getOpenDt())
                .typeName(movie.getTypeName())
                .prdtStatName(movie.getPrdtStatName())
                .nationAlt(movie.getNationAlt())
                .genreAlt(movie.getGenreAlt())
                .directorName(movie.getDirectorName())
                .companyName(movie.getCompanyName())
                .build();

    }
}
