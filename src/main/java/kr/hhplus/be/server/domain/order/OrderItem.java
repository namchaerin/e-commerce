package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.domain.product.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private BigDecimal unitPrice;
    private Double totalPrice;

}
