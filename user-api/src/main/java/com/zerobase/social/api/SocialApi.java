package com.zerobase.social.api;

import com.zerobase.social.domain.SocialLoginParam;
import com.zerobase.social.domain.response.SocialUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;

public interface SocialApi {
    OAuthProvider oAuthProvider();
    String getToken(SocialLoginParam param);
    SocialUserInfoResponse getUserInfo(String accessToken);
}
