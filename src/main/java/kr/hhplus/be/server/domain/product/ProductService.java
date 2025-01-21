package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.PageResponse;
import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.domain.order.OrderItemRepository;
import kr.hhplus.be.server.infrastructure.order.TopSellingItemDto;
import kr.hhplus.be.server.presentation.dto.OrderItemDto;
import kr.hhplus.be.server.presentation.dto.ProductResponse;
import kr.hhplus.be.server.presentation.dto.ProductTopResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kr.hhplus.be.server.common.ErrorCode.PRODUCT_NOT_FOUND;
import static kr.hhplus.be.server.common.ErrorCode.PRODUCT_OUT_OF_STOCK;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public PageResponse<ProductResponse> getProductList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productPage = productRepository.findAll(pageable).map(ProductResponse::of);

        return new PageResponse<>(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.hasNext()
        );
    }

    public ProductResponse getProduct(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            return ProductResponse.of(productRepository.findById(productId).get());
        } else {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    public List<ProductTopResponse> getTopSellingProducts(Integer daysAgo, Integer topN) {
        LocalDateTime fromDate = calculateFromDate(daysAgo);
        Pageable pageable = getTopSellingItemsPageable(topN);

        List<TopSellingItemDto> topSellingProducts = orderItemRepository.findTopSellingProducts(fromDate, pageable);

        return getProductTopResponses(topSellingProducts);
    }

    // 상품 재고 잠금 및 수량 확인
    @Transactional
    public BigDecimal lockProductStock(List<OrderItemDto> items) {
        BigDecimal totalAmount = new BigDecimal("0.0");
        for (OrderItemDto item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

            // 재고 수량 확인
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(PRODUCT_OUT_OF_STOCK);
            }

            // 재고 잠금
            product.updateStock(product.getStock() - item.getQuantity());

            totalAmount.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return totalAmount;
    }

    private static List<ProductTopResponse> getProductTopResponses(List<TopSellingItemDto> topSellingProducts) {

        List<ProductTopResponse> responses = new ArrayList<>();

        int rank = 1;
        for (TopSellingItemDto item : topSellingProducts) {
            Product product = item.getProduct();
            ProductTopResponse productResponse = new ProductTopResponse(
                    rank,
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            );
            responses.add(productResponse);
            rank++;
        }
        return responses;
    }

    private LocalDateTime calculateFromDate(int daysAgo) {
        return LocalDateTime.now().minusDays(daysAgo);
    }

    private Pageable getTopSellingItemsPageable(int topN) {
        return PageRequest.of(0, topN);
    }

}
