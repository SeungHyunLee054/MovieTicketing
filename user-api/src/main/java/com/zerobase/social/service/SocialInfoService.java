package com.zerobase.social.service;

import com.zerobase.social.api.SocialApi;
import com.zerobase.social.domain.SocialLoginParam;
import com.zerobase.social.domain.response.SocialUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SocialInfoService {
    private final Map<OAuthProvider, SocialApi> apis;

    public SocialInfoService(List<SocialApi> apis) {
        this.apis =
                apis.stream().collect(
                        Collectors.toUnmodifiableMap(
                                SocialApi::oAuthProvider, Function.identity()));
    }

    public SocialUserInfoResponse requestUserInfo(SocialLoginParam param) {
        SocialApi api = apis.get(param.oAuthProvider());
        String accessToken = api.getToken(param);

        return api.getUserInfo(accessToken);
    }
}
