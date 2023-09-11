package com.zerobase.Oauth.api;

import com.zerobase.Oauth.domain.OAuthLoginParam;
import com.zerobase.Oauth.domain.response.OAuthUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;

public interface OauthApi {
    OAuthProvider oAuthProvider();
    String getToken(OAuthLoginParam param);
    OAuthUserInfoResponse getUserInfo(String accessToken);
}
