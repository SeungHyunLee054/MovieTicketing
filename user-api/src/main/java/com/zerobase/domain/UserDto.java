package com.zerobase.domain;

import com.zerobase.domain.model.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private Long balance;
    private LocalDate birth;
    private String phone;
    private List<MovieDto> movies;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .balance(user.getBalance())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .movies(user.getMovies())
                .build();
    }
}
