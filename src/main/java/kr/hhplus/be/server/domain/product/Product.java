package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Integer stock;


    public void updateStock(Integer stock) {
        if (stock >= 0) this.stock = stock;
    }

}
