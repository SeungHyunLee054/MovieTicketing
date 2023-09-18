package com.zerobase.controller;

import com.zerobase.common.UserVo;
import com.zerobase.domain.ChangeBalanceForm;
import com.zerobase.domain.SignInForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.UserDto;
import com.zerobase.domain.model.BlockInputForm;
import com.zerobase.domain.model.User;
import com.zerobase.service.UserBalanceService;
import com.zerobase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserBalanceService userBalanceService;

    @PostMapping("/signUp")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) {
        userService.userSignUp(form);
        return ResponseEntity.ok("회원 가입 성공");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> userSignIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(userService.userLogin(form));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> userInfo(@AuthenticationPrincipal UserVo userVo) {
        return ResponseEntity.ok(userService.getUserInfo(userVo.getEmail()));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers(@AuthenticationPrincipal UserVo userVo) {
        return ResponseEntity.ok(userService.getAllUsers(userVo));
    }

    @PostMapping("/admin/block")
    public ResponseEntity<String> blockUser(@AuthenticationPrincipal UserVo userVo,
                                            @RequestBody BlockInputForm form) {
        userService.blockUser(userVo, form);
        return ResponseEntity.ok("해당 유저를 차단했습니다.");
    }

    @PostMapping("/balance")
    public ResponseEntity<Long> changeBalance(@AuthenticationPrincipal UserVo userVo,
                                              @RequestBody ChangeBalanceForm form) {
        return ResponseEntity.ok(userBalanceService
                .changeBalance(userVo.getId(), form).getCurrentMoney());
    }
}
