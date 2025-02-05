package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.presentation.dto.OrderItemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static kr.hhplus.be.server.common.ErrorCode.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @DisplayName("상품 재고 차감시 동시성 제어 테스트")
    @Test
    public void productConcurrentStockDeduction() throws InterruptedException, ExecutionException {
        // 1. 상품 초기화: 재고 9개, 가격 1000원
        Product product = new Product("상품1", BigDecimal.valueOf(1000), 9);
        productRepository.save(product);

        // 2. 주문 항목 생성: 5개씩 주문
        OrderItemDto itemA = new OrderItemDto(product.getId(), 5); // 첫 번째 트랜잭션: 5개 주문
        OrderItemDto itemB = new OrderItemDto(product.getId(), 5); // 두 번째 트랜잭션: 5개 주문

        // 3. 두 개의 트랜잭션을 동시에 실행하기 위한 CountDownLatch 사용
        CountDownLatch latch = new CountDownLatch(2);

        // 4. 두 개의 스레드로 동시성 테스트
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 트랜잭션 A (첫 번째 주문)
        executor.submit(() -> {
            try {
                productService.getTotalAmount(List.of(itemA));
            } catch (Exception e) {
                fail("트랜잭션 A 실패: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        // 트랜잭션 B (두 번째 주문)
        executor.submit(() -> {
            try {
                productService.getTotalAmount(List.of(itemB));
            } catch (Exception e) {
                fail("트랜잭션 B 실패: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        // 두 트랜잭션이 종료될 때까지 대기
        latch.await();

        // 5. 재고 확인: 첫 번째 트랜잭션은 정상적으로 처리되어야 하며, 두 번째 트랜잭션은 재고 부족 예외를 던져야 한다.
        Product updatedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
        assertEquals(4, updatedProduct.getStock());  // 첫 번째 트랜잭션이 5개를 차감하고, 재고는 0이 되어야 한다.

        // 두 번째 트랜잭션이 예외를 던졌는지 확인
        assertThrows(InsufficientStockException.class, () -> {
            productService.getTotalAmount(List.of(itemB)); // 재고가 부족한 상황에서 예외 발생
        });
    }
}
