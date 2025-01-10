package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.domain.coupon.Coupon;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Entity
@NoArgsConstructor
public class UserCoupon extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime usedAt;


    public UserCoupon(User user, Coupon coupon, boolean used, LocalDateTime issuedAt, LocalDateTime usedAt) {
        this.user = user;
        this.coupon = coupon;
        this.used = used;
        this.issuedAt = issuedAt;
        this.usedAt = usedAt;
    }

    public void addCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
        this.issuedAt = LocalDateTime.now();
    }

    public void use() {
        this.used = TRUE;
        this.usedAt = LocalDateTime.now();
    }

}
