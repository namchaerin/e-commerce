package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class CouponGenerateRequest {

    private final Long userId;
    private final Long couponId;

    public CouponGenerateRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

}
