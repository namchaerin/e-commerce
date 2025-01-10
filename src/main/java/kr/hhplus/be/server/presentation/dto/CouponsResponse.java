package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponsResponse {

    private final Long couponId;
    private final String couponCode;
    private final Integer discountRate;
    private final LocalDateTime expirationDate;

    public CouponsResponse(Long couponId, String couponCode, Integer discountRate, LocalDateTime expirationDate) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
    }

}
