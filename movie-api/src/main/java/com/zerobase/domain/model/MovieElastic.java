package com.zerobase.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "movie")
@Entity
//@Mapping(mappingPath = "/elasticsearch/movie-mapping.json")
//@Setting(settingPath = "/elasticsearch/analyzer-setting.json")
public class MovieElastic extends BaseEntity {
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

    public static OpenMovie from(MovieElastic movie) {
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
