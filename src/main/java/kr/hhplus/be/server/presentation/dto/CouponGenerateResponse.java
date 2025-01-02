package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

@Getter
public class CouponGenerateResponse {

    private final boolean success;
    private final Long couponId;
    private final String message;

    public CouponGenerateResponse(boolean success, Long couponId, String message) {
        this.success = success;
        this.couponId = couponId;
        this.message = message;
    }

}
