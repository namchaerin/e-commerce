package kr.hhplus.be.server.common.exceptions;

import kr.hhplus.be.server.common.ErrorCode;
import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {

    private final ErrorCode errorCode;

    public InsufficientStockException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
