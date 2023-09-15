package com.zerobase.domain.repository;

import com.zerobase.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CustomMovieSearchRepositoryImpl implements CustomMovieSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Movie> searchByMovieName(String name, Pageable pageable) {
        Criteria criteria = Criteria.where("movieName").contains(name);
        Query query = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<Movie> search = elasticsearchOperations.search(query, Movie.class);
        return search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
