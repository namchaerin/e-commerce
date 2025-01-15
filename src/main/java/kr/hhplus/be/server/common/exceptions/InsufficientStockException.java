package kr.hhplus.be.server.common.exceptions;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super("재고가 부족합니다.");
    }

    public InsufficientStockException(String message) {
        super(message);
    }

}
