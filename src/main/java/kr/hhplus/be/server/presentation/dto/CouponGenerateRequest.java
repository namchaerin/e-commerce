package kr.hhplus.be.server.presentation.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

@Getter
public class CouponGenerateRequest {

    @Parameter(description = "사용자 ID")
    private final Long userId;

    @Parameter(description = "발급받고자 하는 쿠폰 ID")
    private final Long couponId;

    public CouponGenerateRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

}
