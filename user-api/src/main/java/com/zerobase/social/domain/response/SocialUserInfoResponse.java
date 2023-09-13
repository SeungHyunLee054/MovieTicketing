package com.zerobase.social.domain.response;

import com.zerobase.domain.type.OAuthProvider;

import java.time.LocalDate;

public interface SocialUserInfoResponse {
    String getEmail();

    String getName();

    LocalDate getBirth();

    String getPhone();

    OAuthProvider getOAuthProvider();
}
