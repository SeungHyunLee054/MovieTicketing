package com.zerobase.Oauth.domain.response;

import com.zerobase.domain.type.OAuthProvider;

import java.time.LocalDate;

public interface OAuthUserInfoResponse {
    String getEmail();

    String getName();

    LocalDate getBirth();

    String getPhone();

    OAuthProvider getOAuthProvider();
}
