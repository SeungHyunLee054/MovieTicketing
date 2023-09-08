package com.zerobase.domain.response.movie.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActorDto {
    private String peopleNm;
    private String peopleNmEn;
    private String cast;
    private String castEn;
}
