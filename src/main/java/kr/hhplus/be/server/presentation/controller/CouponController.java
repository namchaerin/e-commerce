package kr.hhplus.be.server.presentation.controller;

import kr.hhplus.be.server.presentation.dto.CouponGenerateRequest;
import kr.hhplus.be.server.presentation.dto.CouponGenerateResponse;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    @PostMapping
    public ResponseEntity<?> generateCoupon(
            @RequestBody CouponGenerateRequest request) {
        return ResponseEntity.ok(new CouponGenerateResponse(TRUE, 1L, "쿠폰 발급 성공"));
    }

    @GetMapping
    public List<CouponsResponse> getCoupons(
            @RequestParam Long userId) {
        return Arrays.asList(
                new CouponsResponse(1L, 10, LocalDate.of(2025, 2, 1)),
                new CouponsResponse(2L, 5, LocalDate.of(2025, 2, 17)),
                new CouponsResponse(3L, 7, LocalDate.of(2025, 3, 1)));
    }

}
