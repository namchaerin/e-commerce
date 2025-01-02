package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderResponse {

    private final boolean success;
    private final Long orderId;
    private final Double newBalance;
    private final String message;

    public OrderResponse(boolean success, Long orderId, Double newBalance, String message) {
        this.success = success;
        this.orderId = orderId;
        this.newBalance = newBalance;
        this.message = message;
    }

}
