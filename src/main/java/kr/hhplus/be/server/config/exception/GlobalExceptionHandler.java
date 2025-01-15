package kr.hhplus.be.server.config.exception;

import kr.hhplus.be.server.common.ApiGenericResponse;
import kr.hhplus.be.server.common.exceptions.CouponLimitExceededException;
import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.InvalidCouponException;
import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiGenericResponse<?>> handleInsufficientCouponStock(InsufficientStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiGenericResponse.createError(ex.getMessage()));
    }

    @ExceptionHandler(CouponLimitExceededException.class)
    public ResponseEntity<ApiGenericResponse<?>> handleCouponLimitExceeded(CouponLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiGenericResponse.createError(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCouponException.class)
    public ResponseEntity<ApiGenericResponse<?>> handleInvalidCoupon(InvalidCouponException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiGenericResponse.createError(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiGenericResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiGenericResponse.createError(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiGenericResponse<?>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiGenericResponse.createError(ex.getMessage()));
    }

}
