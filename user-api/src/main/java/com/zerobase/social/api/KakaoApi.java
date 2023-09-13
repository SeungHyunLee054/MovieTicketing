package com.zerobase.social.api;

import com.zerobase.social.domain.SocialLoginParam;
import com.zerobase.social.domain.response.KakaoTokenResponse;
import com.zerobase.social.domain.response.KakaoUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class KakaoApi implements SocialApi {
    private final RestTemplate restTemplate;
    @Value("${kakao.auth.uri}")
    private String KAKAO_AUTH_URI;
    @Value("${kakao.api.uri}")
    private String KAKAO_API_URI;
    @Value("${kakao.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${grant.type}")
    private String GRANT_TYPE;
    private final String CLIENT_ID = System.getProperty("kakao_client_id");

    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getToken(SocialLoginParam param) {
        String url = KAKAO_AUTH_URI + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = param.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        KakaoTokenResponse response =
                restTemplate.postForObject(url, request, KakaoTokenResponse.class);

        assert response != null;
        return response.getAccessToken();
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        String url = KAKAO_API_URI + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoUserInfoResponse.class);
    }
}
