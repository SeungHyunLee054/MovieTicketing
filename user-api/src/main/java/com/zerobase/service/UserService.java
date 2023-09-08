package com.zerobase.service;

import com.zerobase.config.TokenProvider;
import com.zerobase.domain.SignInForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.UserDto;
import com.zerobase.domain.model.User;
import com.zerobase.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider provider;

    public void userSignUp(SignUpForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow();

        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    public void userAdminSignUp(SignUpForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new RuntimeException();
        }

        User user = User.from(form);
        user.setAdminYn(true);
        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    public String userLoginToken(SignInForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .stream()
                .filter(u ->
                        u.getPassword().equals(form.getPassword()) && !u.isAdminYn())
                .findFirst()
                .orElseThrow();

        return provider.createToken(user.getEmail(), user.getId(), false);
    }

    public String userAdminLoginToken(SignInForm form) {
        User user = userRepository.findByEmailAndPasswordAndAdminYnIsTrue(form.getEmail(), form.getPassword())
                .orElseThrow();

        return provider.createToken(user.getEmail(), user.getId(), true);
    }

    public UserDto userInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        return UserDto.from(user);
    }
}
