package kr.hhplus.be.server.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiErrorResponse {

    private int errorCode;
    private String message;

    public ApiErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
