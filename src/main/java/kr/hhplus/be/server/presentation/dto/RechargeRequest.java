package kr.hhplus.be.server.presentation.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RechargeRequest {

    private Long userId;
    private BigDecimal amount;

}
