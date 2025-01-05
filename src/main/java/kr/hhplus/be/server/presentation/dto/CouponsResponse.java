package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponsResponse {

    private final Long couponId;
    private final Integer discountRate;
    private final LocalDate expirationDate;

    public CouponsResponse(Long couponId, Integer discountRate, LocalDate expirationDate) {
        this.couponId = couponId;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
    }

}
