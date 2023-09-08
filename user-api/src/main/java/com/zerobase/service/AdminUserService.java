package com.zerobase.service;

import com.zerobase.domain.model.User;
import com.zerobase.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {
    private final UserRepository userRepository;

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        userRepository.delete(user);
    }
}
