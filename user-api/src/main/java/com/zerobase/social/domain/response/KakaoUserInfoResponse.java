package com.zerobase.social.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.domain.type.OAuthProvider;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class KakaoUserInfoResponse implements SocialUserInfoResponse {
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    public static class KakaoProfile {
        private String nickname;
    }


    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getName() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public LocalDate getBirth() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
