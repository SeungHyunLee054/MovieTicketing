package com.zerobase.domain.repository;

import com.zerobase.domain.model.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MovieSearchRepository extends ElasticsearchRepository<Movie, Long>, CustomMovieSearchRepository {
    List<Movie> findByMovieName(String name);
}
