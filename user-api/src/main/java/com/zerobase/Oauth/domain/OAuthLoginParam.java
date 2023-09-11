package com.zerobase.Oauth.domain;

import com.zerobase.domain.type.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParam {
    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
