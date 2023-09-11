package com.zerobase.Oauth.service;

import com.zerobase.Oauth.api.OauthApi;
import com.zerobase.Oauth.domain.OAuthLoginParam;
import com.zerobase.Oauth.domain.response.OAuthUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthInfoService {
    private final Map<OAuthProvider, OauthApi> apis;

    public OAuthInfoService(List<OauthApi> apis) {
        this.apis =
                apis.stream().collect(
                        Collectors.toUnmodifiableMap(
                                OauthApi::oAuthProvider, Function.identity()));
    }

    public OAuthUserInfoResponse requestUserInfo(OAuthLoginParam param) {
        OauthApi api = apis.get(param.oAuthProvider());
        String accessToken = api.getToken(param);

        return api.getUserInfo(accessToken);
    }
}
