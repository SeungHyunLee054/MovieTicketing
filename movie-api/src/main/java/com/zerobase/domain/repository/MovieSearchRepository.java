package com.zerobase.domain.repository;

import com.zerobase.domain.model.MovieElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieSearchRepository extends ElasticsearchRepository<MovieElastic, Long> {
    @Query(
            "{\"multi_match\": {" +
                    "\"query\": \"?0\"," +
                    "\"fields\": [" +
                    "\"movieName\", \"movieNameEn\"" +
                    "]," +
                    "\"type\": \"phrase_prefix\"" +
                    "}" +
                    "}" +
                    "}"
    )
    Page<MovieElastic> findByMovieName(String movieName, Pageable pageable);
}
