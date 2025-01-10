package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductTopResponse {

    private final Integer rank;
    private final Long productId;
    private final String productName;
    private final BigDecimal price;
    private final Integer stock;

    public ProductTopResponse(Integer rank, Long productId, String productName, BigDecimal price, Integer stock) {
        this.rank = rank;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

}
