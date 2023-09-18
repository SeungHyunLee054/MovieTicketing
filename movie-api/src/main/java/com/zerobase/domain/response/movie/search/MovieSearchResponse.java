package com.zerobase.domain.response.movie.search;

import com.zerobase.domain.model.MovieElastic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearchResponse {
    List<MovieElastic> movieElastics;
}
