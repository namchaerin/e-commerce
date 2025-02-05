package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import static kr.hhplus.be.server.common.ErrorCode.*;

@Entity
@Getter
@NoArgsConstructor
public class UserBalance extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balanceAmount;

    @Version
    private Long version;


    public UserBalance(User user, BigDecimal balanceAmount) {
        this.user = user;
        this.balanceAmount = balanceAmount;
    }

    public void addAmount(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(NEGATIVE_BALANCE.getMessage());
        }

        BigDecimal maxLimit = new BigDecimal("100000");
        if (amount.compareTo(maxLimit) > 0) {
            throw new IllegalArgumentException(MAX_BALANCE_EXCEEDED.getMessage());
        }

        this.balanceAmount = this.balanceAmount.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParameterException(NEGATIVE_BALANCE.getMessage());
        }

        if (balanceAmount.compareTo(amount) < 0) {
            throw new InsufficientStockException(INSUFFICIENT_BALANCE);
        }
        balanceAmount = balanceAmount.subtract(amount);
    }

}
