package com.zerobase.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceHistory extends BaseEntity {
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;
    private Long changeMoney;
    private Long currentMoney;
    private String fromMessage;
    private String description;
}
