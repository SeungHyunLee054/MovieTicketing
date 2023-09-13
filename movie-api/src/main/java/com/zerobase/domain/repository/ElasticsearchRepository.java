package com.zerobase.domain.repository;

import com.zerobase.domain.model.MovieElastic;

import java.util.List;

public interface ElasticsearchRepository extends org.springframework.data.elasticsearch.repository.ElasticsearchRepository<MovieElastic, Long> {
    List<MovieElastic> searchByMovieNameContains(String searchText);
}
