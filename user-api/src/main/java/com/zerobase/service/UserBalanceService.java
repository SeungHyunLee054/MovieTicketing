package com.zerobase.service;

import com.zerobase.domain.ChangeBalanceForm;
import com.zerobase.domain.model.User;
import com.zerobase.domain.model.UserBalanceHistory;
import com.zerobase.domain.repository.UserBalanceHistoryRepository;
import com.zerobase.domain.repository.UserRepository;
import com.zerobase.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zerobase.exception.ErrorCode.NOT_ENOUGH_MONEY;
import static com.zerobase.exception.ErrorCode.NO_EXIST_USER;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBalanceService {
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;
    private final UserRepository userRepository;

    public UserBalanceHistory changeBalance(Long userId, ChangeBalanceForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));
        UserBalanceHistory userBalanceHistory =
                userBalanceHistoryRepository.findFirstByUser_IdOrderByIdDesc(userId)
                        .orElseGet(() -> UserBalanceHistory.builder()
                                .changeMoney(0L)
                                .currentMoney(0L)
                                .user(user)
                                .build());

        if (userBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
            throw new CustomException(NOT_ENOUGH_MONEY);
        }

        userBalanceHistory = UserBalanceHistory.builder()
                .changeMoney(form.getMoney())
                .currentMoney(userBalanceHistory.getCurrentMoney() + form.getMoney())
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .user(userBalanceHistory.getUser())
                .build();

        userBalanceHistory.getUser()
                .setBalance(userBalanceHistory.getCurrentMoney());

        log.info("잔액 변경 -> 변경 금액 : {}, 변경 후 금액 : {}",
                userBalanceHistory.getChangeMoney(), userBalanceHistory.getCurrentMoney());
        return userBalanceHistoryRepository.save(userBalanceHistory);
    }
}
