package com.zerobase.elasticsearch;

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
public class MovieResponse {
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

    public static MovieResponse from(MovieResponseDto movieResponseDto){
        return MovieResponse.builder()
                .movieCd(movieResponseDto.getMovieCd())
                .movieName(movieResponseDto.getMovieName())
                .movieNameEn(movieResponseDto.getMovieNameEn())
                .prdtYear(movieResponseDto.getPrdtYear())
                .openDt(movieResponseDto.getOpenDt())
                .typeName(movieResponseDto.getTypeName())
                .prdtStatName(movieResponseDto.getPrdtStatName())
                .nationAlt(movieResponseDto.getNationAlt())
                .genreAlt(movieResponseDto.getGenreAlt())
                .directorName(movieResponseDto.getDirectorName())
                .companyName(movieResponseDto.getCompanyName())
                .build();
    }
}
