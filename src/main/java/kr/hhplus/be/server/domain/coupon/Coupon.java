package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Coupon extends BaseEntity {

    private String code;
    private Integer discountRate;

    private Integer totalQuantity;
    private Integer remainingQuantity;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;


    public Coupon(String code, Integer discountRate, Integer totalQuantity, Integer remainingQuantity, LocalDateTime validFrom, LocalDateTime validUntil, CouponStatus status) {
        this.code = code;
        this.discountRate = discountRate;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.status = status;
    }

    public Coupon(String code) {
        this.code = code;
    }


    public void updateRemainingQuantity(Integer toQuantity) {
        this.remainingQuantity = toQuantity;
    }

}
