package kr.hhplus.be.server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.ApiGenericResponse;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.presentation.dto.CouponGenerateRequest;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
@Tag(name = "쿠폰 관리", description = "쿠폰을 발급하고 조회하기 위한 API 이다.")
public class CouponController {

    private final CouponService couponService;


    @Operation(
            summary = "쿠폰 발급",
            description = "특정 쿠폰을 유저에게 발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "쿠폰 발급 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "쿠폰 수량 부족 또는 유저별 발급 한도 초과",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "유저 또는 쿠폰이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class)))
            })
    @PostMapping
    public ApiGenericResponse<?> generateCoupon(
            @RequestBody CouponGenerateRequest request) {
        couponService.issueCoupon(request.getUserId(), request.getCouponId());
        return ApiGenericResponse.createSuccessWithNoContent();
    }

    @Operation(
            summary = "쿠폰 조회",
            description = "쿠폰을 조회한다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "쿠폰 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponsResponse.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "유저 또는 쿠폰이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiGenericResponse.class)))
            })
    @GetMapping
    public ApiGenericResponse<List<CouponsResponse>> getCoupons(
            @RequestParam Long userId) {
        return ApiGenericResponse.createSuccess(couponService.getCoupons(userId));
    }

}
