package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.coupon.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_order")
@NoArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDateTime;

    public void create(User user, Coupon coupon, BigDecimal totalAmount) {
        this.user = user;
        this.coupon = coupon;
        this.totalAmount = totalAmount.multiply(new BigDecimal(1 - coupon.getDiscountRate()/100));
        this.status = OrderStatus.PENDING;
        this.orderDateTime = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

}
