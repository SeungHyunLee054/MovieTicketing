package com.zerobase.domain.response.movie;

import com.zerobase.domain.model.MovieElastic;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {
    private List<MovieElastic> movies;
}
