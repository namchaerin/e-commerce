package kr.hhplus.be.server.presentation.controller;

import kr.hhplus.be.server.presentation.dto.OrderSuccessRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockApiController {

    @PostMapping("/payment-result")
    public ResponseEntity<String> sendPaymentResult(@RequestBody OrderSuccessRequest orderSuccessRequest) {
        // 여기에서 외부 데이터 플랫폼에 실제 결제 결과를 전송하는 로직을 구현한다고 가정
        // Mocking (가짜 응답)
        System.out.println("Payment Result received: " + orderSuccessRequest);

        return new ResponseEntity<>("Payment result received successfully", HttpStatus.OK);
    }
}
