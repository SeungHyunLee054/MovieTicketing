package com.zerobase.controller;

import com.zerobase.domain.SignInForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.UserDto;
import com.zerobase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) {
        userService.userSignUp(form);
        return ResponseEntity.ok("회원 가입 성공");
    }

    @PostMapping("/admin/signUp")
    public ResponseEntity<String> userAdminSignUp(@RequestBody SignUpForm form) {
        userService.userAdminSignUp(form);
        return ResponseEntity.ok("관리자 회원 가입 성공");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> userSignIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(userService.userLoginToken(form));
    }

    @PostMapping("/admin/signIn")
    public ResponseEntity<String> userAdminSignIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(userService.userAdminLoginToken(form));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> userInfo(@RequestParam String email) {
        return ResponseEntity.ok(userService.userInfo(email));
    }
}
