package com.zerobase.controller;

import com.zerobase.social.domain.KakaoLoginParam;
import com.zerobase.social.domain.NaverLoginParam;
import com.zerobase.social.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialUserController {
    private final SocialLoginService socialLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<String> signUpKakao(@RequestBody KakaoLoginParam param) {
        return ResponseEntity.ok(socialLoginService.login(param));
    }

    @PostMapping("/naver")
    public ResponseEntity<String> signUpNaver(@RequestBody NaverLoginParam param) {
        return ResponseEntity.ok(socialLoginService.login(param));
    }
}
