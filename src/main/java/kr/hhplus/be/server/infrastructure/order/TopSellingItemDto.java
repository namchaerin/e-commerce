package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.product.Product;
import lombok.Getter;

@Getter
public class TopSellingItemDto {

    private final Product product;

    private final Long sellingQuantity;

    public TopSellingItemDto(Product product, Long sellingQuantity) {
        this.product = product;
        this.sellingQuantity = sellingQuantity;
    }

}
