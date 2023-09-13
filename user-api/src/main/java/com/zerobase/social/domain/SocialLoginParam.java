package com.zerobase.social.domain;

import com.zerobase.domain.type.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface SocialLoginParam {
    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
