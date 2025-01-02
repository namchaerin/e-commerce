package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private final Long userId;
    private final List<OrderItemDto> items;
    private final Long couponId;

    public OrderRequest(Long userId, List<OrderItemDto> items, Long couponId) {
        this.userId = userId;
        this.items = items;
        this.couponId = couponId;
    }

}
