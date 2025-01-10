package kr.hhplus.be.server.presentation.dto;

import kr.hhplus.be.server.domain.order.Order;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderSuccessRequest {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDateTime;

    public OrderSuccessRequest(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.totalAmount = order.getTotalAmount();
        this.orderDateTime = order.getOrderDateTime();
    }

    @Override
    public String toString() {
        return "OrderSuccessRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderDateTime=" + orderDateTime +
                '}';
    }

}
