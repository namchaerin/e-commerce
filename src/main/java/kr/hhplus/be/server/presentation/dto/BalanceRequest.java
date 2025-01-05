package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class BalanceRequest {

    private final Long userId;
    private final Double rechargingBalance;

    public BalanceRequest(Long userId, Double rechargingBalance) {
        this.userId = userId;
        this.rechargingBalance = rechargingBalance;
    }

}
