package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class BalanceResponse {

    private final Long userId;
    private final Double balance;

    public BalanceResponse(Long userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
