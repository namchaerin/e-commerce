package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.common.exceptions.PageResponse;
import kr.hhplus.be.server.domain.order.OrderItemRepository;
import kr.hhplus.be.server.infrastructure.order.TopSellingItemDto;
import kr.hhplus.be.server.presentation.dto.ProductResponse;
import kr.hhplus.be.server.presentation.dto.ProductTopResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @InjectMocks
    ProductService productService;

    @DisplayName("상품 목록 조회 테스트")
    @Test
    public void testGetProducts() {

        //given
        int page = 0;
        int size = 5;
        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product(), new Product(), new Product(), new Product());
        Pageable pageable = PageRequest.of(page, size);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productList.size());

        Page<Product> productPage = new PageImpl<>(productList.subList(start, end), pageable, productList.size());

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        //when
        PageResponse<ProductResponse> result = productService.getProductList(page, size);

        //then
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(6);

    }

  }