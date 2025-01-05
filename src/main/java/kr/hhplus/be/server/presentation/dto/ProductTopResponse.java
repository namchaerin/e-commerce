package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class ProductTopResponse {

    private final Integer rank;
    private final Long productId;
    private final String productName;
    private final Long price;
    private final Integer stock;

    public ProductTopResponse(Integer rank, Long productId, String productName, Long price, Integer stock) {
        this.rank = rank;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

}
