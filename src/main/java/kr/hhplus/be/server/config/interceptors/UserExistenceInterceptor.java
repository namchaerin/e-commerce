package kr.hhplus.be.server.config.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.domain.User.UserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

@Component
public class UserExistenceInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public UserExistenceInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청 헤더에서 유저 ID 또는 인증 정보 가져오기
        String userId = request.getHeader("User-Id"); // 예시로 User-Id 헤더에서 유저 ID를 가져온다고 가정

        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("User ID is missing");
            return false;
        }

        boolean userExists = userService.checkUserExistence(Long.valueOf(userId));

        if (!userExists) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("User not found");
            return false;
        }

        return true;
    }
}

