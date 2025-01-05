package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderItemDto {

    private final Long productId;
    private final Integer quantity;

    public OrderItemDto(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
