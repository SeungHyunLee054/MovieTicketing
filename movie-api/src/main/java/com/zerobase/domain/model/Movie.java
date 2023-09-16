package com.zerobase.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {
    @Column(unique = true)
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

    public static OpenMovie from(Movie movie) {
        return OpenMovie.builder()
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
