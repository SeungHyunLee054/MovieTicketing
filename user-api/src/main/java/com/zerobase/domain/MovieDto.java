package com.zerobase.domain;

import com.zerobase.domain.model.Movie;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private String movieCd;
    private String movieName;

    public static MovieDto from(Movie movie) {
        return MovieDto.builder()
                .movieCd(movie.getMovieCd())
                .movieName(movie.getMovieName())
                .build();
    }
}
