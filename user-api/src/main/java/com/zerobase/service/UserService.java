package com.zerobase.service;

import com.zerobase.common.UserVo;
import com.zerobase.config.TokenProvider;
import com.zerobase.domain.*;
import com.zerobase.domain.BlockInputForm;
import com.zerobase.domain.model.User;
import com.zerobase.domain.repository.UserRepository;
import com.zerobase.domain.type.OAuthProvider;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zerobase.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider provider;
    private final PasswordEncoder passwordEncoder;
    private final UserBalanceService userBalanceService;

    public void userSignUp(SignUpForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ALREADY_EXIST_USER);
        }

        User user = User.from(form);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOAuthProvider(OAuthProvider.DOMAIN);
        user.setBalance(0L);

        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    public String userLogin(SignInForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .stream()
                .filter(u ->
                        passwordEncoder.matches(form.getPassword(), u.getPassword()))
                .findFirst()
                .orElseThrow(() -> new CustomException(WRONG_ID_OR_PASSWORD));

        if (user.isBlocked()) {
            throw new CustomException(ErrorCode.BLOCKED_USER);
        }

        log.info("로그인 성공");
        return provider.createToken(user.getEmail(), user.getId(), user.isAdminYn());
    }

    public UserDto getUserInfo(String email) {
        return UserDto.from(findByEmail(email));
    }

    public void blockUser(UserVo userVo, BlockInputForm form) {
        if (!userVo.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }

        User user = findByEmail(form.getEmail());

        user.setBlocked(true);
        userRepository.save(user);
        log.info("차단된 유저 -> email : {}", user.getEmail());
    }

    public List<User> getAllUsers(UserVo userVo) {
        if (!userVo.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }
        log.info("모든 유저 조회");

        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));
    }

    public User bookingMovie(Long userId, MovieInputForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));

        user.getMovies().add(MovieDto.from(form));
        userBalanceService.changeBalance(userId,
                ChangeBalanceForm.builder()
                        .from("사이트")
                        .message("영화 예매")
                        .money(form.getSeats() * -8000L)
                        .build());
        userRepository.save(user);
        log.info("영화 예매 성공, 영화 이름 -> {}", form.getMovieName());

        return user;
    }
}
