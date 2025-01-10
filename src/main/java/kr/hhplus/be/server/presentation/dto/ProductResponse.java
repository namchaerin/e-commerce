package kr.hhplus.be.server.presentation.dto;

import kr.hhplus.be.server.domain.product.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponse {

    private final Long productId;
    private final String productName;
    private final BigDecimal price;
    private final Integer stock;

    public ProductResponse(Long productId, String productName, BigDecimal price, Integer stock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock());
    }

}
