package com.zerobase.domain;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class MovieInputForm {
    private String movieCd;
    private String movieName;
    @Min(1)
    private Integer seats;
}
