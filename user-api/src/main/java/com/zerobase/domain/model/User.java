package com.zerobase.domain.model;

import com.zerobase.domain.MovieDto;
import com.zerobase.domain.MovieInputForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.type.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;

    private boolean adminYn;

    @ElementCollection
    @CollectionTable(name = "movie")
    private List<MovieDto> movies = new ArrayList<>();

    @ColumnDefault("0")
    private Long balance;

    private boolean blocked;

    private OAuthProvider oAuthProvider;

    public static User from(SignUpForm form) {
        return User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .blocked(false)
                .adminYn(form.isAdminYn())
                .build();
    }
}
