package kr.hhplus.be.server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.OrderFacade;
import kr.hhplus.be.server.common.ApiGenericResponse;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import kr.hhplus.be.server.presentation.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 관리", description = "주문과 결제에 대한 API 이다.")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(
            summary = "주문 및 결제",
            description = "상품을 주문 및 결제한다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문과 결제 성공",
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
    @PostMapping
    public ApiGenericResponse<?> order(
            @RequestBody OrderRequest orderRequest) {
        orderFacade.createOrder(orderRequest.getUserId(), orderRequest.getCouponId(), orderRequest.getItems());
        return ApiGenericResponse.createSuccessWithNoContent();
    }

}
