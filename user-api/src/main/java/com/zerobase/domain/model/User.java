package com.zerobase.domain.model;

import com.zerobase.domain.SignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;

    private boolean adminYn;

    @ManyToMany
    private List<Movie> movies = new ArrayList<>();

    @ColumnDefault("0")
    private Long balance;

    public static User from(SignUpForm form) {
        return User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .build();
    }
}
