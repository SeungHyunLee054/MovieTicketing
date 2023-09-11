package com.zerobase.controller;

import com.zerobase.common.UserVo;
import com.zerobase.config.TokenProvider;
import com.zerobase.domain.ChangeBalanceForm;
import com.zerobase.domain.SignInForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.UserDto;
import com.zerobase.domain.model.User;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import com.zerobase.service.UserBalanceService;
import com.zerobase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider provider;
    private final UserBalanceService userBalanceService;

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
    public ResponseEntity<UserDto> userInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);

        User user = userService.findByEmail(vo.getEmail());
        return ResponseEntity.ok(UserDto.from(user));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);

        User user = userService.findByEmail(vo.getEmail());
        if (!user.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/admin/blcok")
    public ResponseEntity<String> blockUser(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestBody String email) {
        UserVo vo = provider.getUserVo(token);

        User user = userService.findByEmail(vo.getEmail());
        if (!user.isAdminYn()) {
            throw new CustomException(ErrorCode.NO_ADMIN_USER);
        }

        userService.blockUser(email);
        return ResponseEntity.ok("해당 유저를 차단했습니다.");
    }

    @PostMapping("/balance")
    public ResponseEntity<Long> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @RequestBody ChangeBalanceForm form) {
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(userBalanceService
                .changeBalance(vo.getId(), form).getCurrentMoney());
    }
}
