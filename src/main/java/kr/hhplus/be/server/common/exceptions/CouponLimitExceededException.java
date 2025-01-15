package kr.hhplus.be.server.common.exceptions;

public class CouponLimitExceededException extends RuntimeException {

    public CouponLimitExceededException(String message) {
        super(message);
    }

}
