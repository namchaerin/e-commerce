package kr.hhplus.be.server.common;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int errorCode;
    private String message;

    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
