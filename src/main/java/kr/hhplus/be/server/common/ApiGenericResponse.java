package kr.hhplus.be.server.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiGenericResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public static <T> ApiGenericResponse<T> createSuccess(T data) {
        return new ApiGenericResponse<>(SUCCESS_STATUS, data, null);
    }

    public static ApiGenericResponse<?> createSuccessWithNoContent() {
        return new ApiGenericResponse<>(SUCCESS_STATUS, null, null);
    }

    public static ApiGenericResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiGenericResponse<>(FAIL_STATUS, errors, null);
    }

    public static ApiGenericResponse<?> createError(String message) {
        return new ApiGenericResponse<>(ERROR_STATUS, null, message);
    }

    private ApiGenericResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
