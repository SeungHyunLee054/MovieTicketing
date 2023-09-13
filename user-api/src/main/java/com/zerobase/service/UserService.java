package com.zerobase.service;

import com.zerobase.config.TokenProvider;
import com.zerobase.domain.SignInForm;
import com.zerobase.domain.SignUpForm;
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
import java.util.Optional;

import static com.zerobase.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider provider;
    private final PasswordEncoder passwordEncoder;

    public void userSignUp(SignUpForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ALREADY_EXIST_USER);
        }

        User user = User.from(form);
        user.setBlocked(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOAuthProvider(OAuthProvider.DOMAIN);
        user.setBalance(0L);

        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    public void userAdminSignUp(SignUpForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ALREADY_EXIST_USER);
        }

        User user = User.from(form);
        user.setAdminYn(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOAuthProvider(OAuthProvider.DOMAIN);
        user.setBalance(0L);

        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    public String userLoginToken(SignInForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .stream()
                .filter(u ->
                        passwordEncoder.matches(form.getPassword(), u.getPassword())
                                && !u.isAdminYn())
                .findFirst()
                .orElseThrow(() -> new CustomException(WRONG_ID_OR_PASSWORD));

        if (user.isBlocked()) {
            throw new CustomException(ErrorCode.BLOCKED_USER);
        }

        log.info("로그인 성공");
        return provider.createToken(user.getEmail(), user.getId(), false);
    }

    public String userAdminLoginToken(SignInForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .stream()
                .filter(u ->
                        passwordEncoder.matches(form.getPassword(), u.getPassword())
                                && u.isAdminYn())
                .findFirst()
                .orElseThrow(() -> new CustomException(WRONG_ID_OR_PASSWORD));

        log.info("로그인 성공");
        return provider.createToken(user.getEmail(), user.getId(), true);
    }

    public void blockUser(User admin, String email) {
        if (!admin.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));

        user.setBlocked(true);
        userRepository.save(user);
    }

    public List<User> getAllUsers(User admin) {
        if (!admin.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }

        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));
    }

    public Optional<User> findByIdAndEmail(Long id, String email) {
        return userRepository.findById(id)
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
