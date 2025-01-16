package kr.hhplus.be.server.common.exceptions;

import kr.hhplus.be.server.common.ErrorCode;

public class CouponLimitExceededException extends RuntimeException {

    private final ErrorCode errorCode;

    public CouponLimitExceededException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
