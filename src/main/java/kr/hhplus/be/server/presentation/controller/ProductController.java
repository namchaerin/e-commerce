package kr.hhplus.be.server.presentation.controller;

import kr.hhplus.be.server.presentation.dto.ProductResponse;
import kr.hhplus.be.server.presentation.dto.ProductTopResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public List<ProductResponse> getProducts() {
        return Arrays.asList(
                new ProductResponse(1L, "상품1", 10_000L, 10),
                new ProductResponse(2L, "상품2", 15_000L, 40),
                new ProductResponse(3L, "상품3", 25_000L, 20));
    }

    @GetMapping("/top")
    public List<ProductTopResponse> getTopProducts(
            @RequestParam Integer days) {
        return Arrays.asList(
                new ProductTopResponse(1, 1L, "상품1", 10_000L, 10),
                new ProductTopResponse(2, 2L, "상품2", 15_000L, 40),
                new ProductTopResponse(3, 3L, "상품3", 25_000L, 20));
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(
            @PathVariable Long productId) {
        return new ProductResponse(3L, "상품3", 25_000L, 20);
    }

}
