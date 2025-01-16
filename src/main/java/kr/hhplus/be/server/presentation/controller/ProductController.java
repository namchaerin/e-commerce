package kr.hhplus.be.server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.ApiErrorResponse;
import kr.hhplus.be.server.common.ApiGenericResponse;
import kr.hhplus.be.server.common.exceptions.PageResponse;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.presentation.dto.ProductResponse;
import kr.hhplus.be.server.presentation.dto.ProductTopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "상품 관리", description = "상품 관리에 대한 API 이다.")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "상품 목록 조회",
            description = "상품 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상품 목록 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping
    public ApiGenericResponse<PageResponse<ProductResponse>> getProducts(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "한 페이지 내의 상품 개수") @RequestParam(defaultValue = "10") int size
    ) {
        return ApiGenericResponse.createSuccess(productService.getProductList(page, size));
    }

    @Operation(
            summary = "인기 판매 상품 조회",
            description = "조회 기간과 순위에 따라 판매율 높은 순으로 상품들을 조회한다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상위 상품 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 파라미터",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/top")
    public List<ProductTopResponse> getTopProducts(
            @Parameter(description = "조회 기간", example = "5") @RequestParam(defaultValue = "3") Integer days,
            @Parameter(description = "상품 개수", example = "10") @RequestParam(defaultValue = "5") Integer topN) {
        return productService.getTopSellingProducts(days, topN);
    }


    @Operation(
            summary = "상품 조회",
            description = "상품의 ID로 상품 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상품 조회 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 파라미터",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{productId}")
    public ProductResponse getProduct(
            @Parameter(description = "상품 아이디", example = "123")
            @PathVariable Long productId) {
        return productService.getProduct(productId);
    }

}
