package com.zerobase.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MovieDto {
    private String movieCd;
    private String movieName;
    private Integer seats;

    public static MovieDto from(MovieInputForm form) {
        return MovieDto.builder()
                .movieCd(form.getMovieCd())
                .movieName(form.getMovieName())
                .seats(form.getSeats())
                .build();
    }
}
