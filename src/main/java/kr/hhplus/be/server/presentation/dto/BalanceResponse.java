package kr.hhplus.be.server.presentation.dto;

import kr.hhplus.be.server.domain.User.UserBalance;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BalanceResponse {

    private final Long userId;
    private final BigDecimal balance;

    public BalanceResponse(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static BalanceResponse of(UserBalance balance) {
        return new BalanceResponse(balance.getUser().getId(), balance.getBalanceAmount());
    }

}
