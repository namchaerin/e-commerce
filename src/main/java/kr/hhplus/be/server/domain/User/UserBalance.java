package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class UserBalance extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balanceAmount;

    public UserBalance(User user, BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
        this.user = user;
    }

    public void addAmount(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }

        BigDecimal maxLimit = new BigDecimal("100000");
        if (amount.compareTo(maxLimit) > 0) {
            throw new IllegalArgumentException("최대 충전 금액은 100,000입니다.");
        }

        this.balanceAmount = this.balanceAmount.add(amount);
    }

}
