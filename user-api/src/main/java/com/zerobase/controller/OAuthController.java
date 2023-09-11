package com.zerobase.controller;

import com.zerobase.Oauth.domain.AuthToken;
import com.zerobase.Oauth.domain.KakaoLoginParam;
import com.zerobase.Oauth.domain.NaverLoginParam;
import com.zerobase.Oauth.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthToken> signUpKakao(@RequestBody KakaoLoginParam param) {
        return ResponseEntity.ok(oAuthLoginService.login(param));
    }

    @PostMapping("/naver")
    public ResponseEntity<AuthToken> signUpNaver(@RequestBody NaverLoginParam param) {
        return ResponseEntity.ok(oAuthLoginService.login(param));
    }
}
