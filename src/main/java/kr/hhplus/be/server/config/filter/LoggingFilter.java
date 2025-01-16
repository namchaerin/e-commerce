package kr.hhplus.be.server.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@WebFilter(urlPatterns = "/api/v1/*")
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. HttpServletRequest와 HttpServletResponse로 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 2. ContentCachingRequestWrapper로 감싸서 본문을 캐싱
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);

        // 3. 요청 로깅 (본문 포함)
        logRequest(wrappedRequest);

        // 4. 필터 체인 실행 (요청/응답 처리)
        chain.doFilter(wrappedRequest, wrappedResponse);

        // 5. 요청 본문 로깅 (doFilter 후)
        logRequestBody(wrappedRequest);

        // 6. 응답 본문 로깅 (doFilter 실행 후)
        logResponseBody(wrappedResponse);

        // 7. 응답을 다시 쓰기
        wrappedResponse.copyBodyToResponse();

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    private void logRequest(HttpServletRequest request) throws IOException {

        // 1. 요청 URL
        log.info("Request URL: " + request.getRequestURL().toString());

        // 2. 요청 메서드 (GET, POST 등)
        log.info("Request Method: " + request.getMethod());

        // 3. 요청 헤더
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header - " + headerName + ": " + request.getHeader(headerName));
        }

        String contentType = request.getHeader("Content-Type");
        log.info("Content-Type: " + contentType);

        // 4. 쿼리 파라미터 (GET 요청에 사용)
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            log.info("Parameter - " + paramName + ": " + request.getParameter(paramName));
        }
    }

    // 요청 본문 로깅
    private void logRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            try {
                String body = new String(content, 0, content.length, "UTF-8");
                log.info("Request Body: " + body);
            } catch (IOException e) {
                log.warn("Failed to read request body");
            }
        }
    }

    // 응답 본문 로깅
    private void logResponseBody(ContentCachingResponseWrapper response) {
        String responseBody = new String(response.getContentAsByteArray());
        log.info("Response Body: " + responseBody);
    }

}
