package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Coupon extends BaseEntity {

    private String code;
    private Integer discountRate;

    private Integer totalQuantity;
    private Integer remainingQuantity;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;


    public Coupon(String code) {
        this.code = code;
    }

    public synchronized void issueCoupon() {
        this.remainingQuantity -= 1;
    }

    public void updateRemainingQuantity(Integer toQuantity) {
        this.remainingQuantity = toQuantity;
    }

}
