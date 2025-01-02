package kr.hhplus.be.server.presentation.controller;

import kr.hhplus.be.server.presentation.dto.OrderRequest;
import kr.hhplus.be.server.presentation.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<?> order(
            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(new OrderResponse(TRUE, 1L, 30_000d, "주문 및 결제 성공"));
    }

}
