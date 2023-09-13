package com.zerobase.social.service;

import com.zerobase.config.TokenProvider;
import com.zerobase.domain.model.User;
import com.zerobase.domain.repository.UserRepository;
import com.zerobase.social.domain.SocialLoginParam;
import com.zerobase.social.domain.response.SocialUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final UserRepository userRepository;
    private final SocialInfoService socialInfoService;
    private final TokenProvider tokenProvider;

    public String login(SocialLoginParam param) {
        SocialUserInfoResponse userInfoResponse =
                socialInfoService.requestUserInfo(param);
        Long userId = findOrCreateUser(userInfoResponse);

        return tokenProvider.createToken(userInfoResponse.getEmail(), userId, false);
    }

    private Long findOrCreateUser(SocialUserInfoResponse userInfoResponse) {
        return userRepository.findByEmail(userInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(userInfoResponse));
    }

    private Long newUser(SocialUserInfoResponse userInfoResponse) {
        User user = User.builder()
                .email(userInfoResponse.getEmail())
                .name(userInfoResponse.getName())
                .birth(userInfoResponse.getBirth())
                .phone(userInfoResponse.getPhone())
                .oAuthProvider(userInfoResponse.getOAuthProvider())
                .adminYn(false)
                .build();

        return userRepository.save(user).getId();
    }
}
