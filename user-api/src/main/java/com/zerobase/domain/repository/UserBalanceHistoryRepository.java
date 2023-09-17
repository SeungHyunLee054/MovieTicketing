package com.zerobase.domain.repository;

import com.zerobase.domain.model.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface UserBalanceHistoryRepository extends JpaRepository<UserBalanceHistory, Long> {
    Optional<UserBalanceHistory> findFirstByUser_IdOrderByIdDesc(
            @RequestParam("user_id") Long userId
    );
}
