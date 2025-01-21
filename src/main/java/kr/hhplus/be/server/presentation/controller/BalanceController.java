package kr.hhplus.be.server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.ApiGenericResponse;
import kr.hhplus.be.server.common.ApiErrorResponse;
import kr.hhplus.be.server.domain.User.UserBalanceService;
import kr.hhplus.be.server.presentation.dto.BalanceResponse;
import kr.hhplus.be.server.presentation.dto.RechargeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balance")
@Tag(name = "잔액 관리", description = "사용자가 소유한 잔액을 관리하는 API 이다.")
public class BalanceController {

    private final UserBalanceService userBalanceService;

    @Operation(
            summary = "잔액 조회",
            description = "잔액을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "잔액 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 파라미터",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{userId}")
    public ApiGenericResponse<BalanceResponse> getBalance(
            @Parameter(description = "사용자 ID") @PathVariable Long userId) {
        return ApiGenericResponse.createSuccess(userBalanceService.getBalanceByUserId(userId));
    }

    @Operation(
            summary = "잔액 충전",
            description = "잔액을 충전합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "잔액 충전 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 파라미터",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping
    public ApiGenericResponse<BalanceResponse> rechargeBalance(@RequestBody RechargeRequest request) {
        return ApiGenericResponse.createSuccess(userBalanceService.updateBalance(request.getUserId(), request.getAmount()));
    }

}
