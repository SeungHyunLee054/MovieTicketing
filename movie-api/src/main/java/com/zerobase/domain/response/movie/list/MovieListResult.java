package com.zerobase.domain.response.movie.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieListResult {
    private String totCnt;
    private String source;
    private List<MovieDto> movieList;
}
