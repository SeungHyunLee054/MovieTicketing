package com.zerobase.domain.repository;

import com.zerobase.domain.model.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailRepository extends JpaRepository<MovieDetail, Long> {
}
