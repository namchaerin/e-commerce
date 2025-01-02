package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long productId;
    private final String productName;
    private final Long price;
    private final Integer stock;

    public ProductResponse(Long productId, String productName, Long price, Integer stock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

}
