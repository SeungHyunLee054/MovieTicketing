package com.zerobase.Oauth.service;

import com.zerobase.Oauth.domain.AuthToken;
import com.zerobase.Oauth.domain.OAuthLoginParam;
import com.zerobase.Oauth.domain.response.OAuthUserInfoResponse;
import com.zerobase.Oauth.util.AuthTokenGenerator;
import com.zerobase.domain.model.User;
import com.zerobase.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final OAuthInfoService oAuthInfoService;

    public AuthToken login(OAuthLoginParam param) {
        OAuthUserInfoResponse userInfoResponse =
                oAuthInfoService.requestUserInfo(param);
        Long userId = findOrCreateUser(userInfoResponse);

        return authTokenGenerator.generate(userId);
    }

    private Long findOrCreateUser(OAuthUserInfoResponse userInfoResponse) {
        return userRepository.findByEmail(userInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(userInfoResponse));
    }

    private Long newUser(OAuthUserInfoResponse userInfoResponse) {
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
