package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.domain.User.User;

import java.time.LocalDateTime;

@Entity
public class CouponTransaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private LocalDateTime usedAt;

}
