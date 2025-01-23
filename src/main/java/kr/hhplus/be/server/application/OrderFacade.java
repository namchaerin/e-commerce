package kr.hhplus.be.server.application;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.UserBalanceService;
import kr.hhplus.be.server.domain.User.UserService;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.order.OrderStatus;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.presentation.dto.OrderItemDto;
import kr.hhplus.be.server.presentation.dto.OrderSuccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final CouponService couponService;
    private final UserService userService;
    private final ProductService productService;
    private final UserBalanceService userBalanceService;


    @Transactional
    public void createOrder(Long userId, Long couponId, List<OrderItemDto> items) {
        // 유저 체크
        User user = userService.validateUser(userId);

        // 상품 재고 확인 및 잠금
        BigDecimal totalAmount = productService.getTotalAmount(items);

        // 쿠폰 사용 처리
        Coupon coupon = couponService.useCoupon(user, couponId);

        // 주문 생성
        Order order = orderService.createOrder(user, coupon, totalAmount);

        // 잔액 차감
        userBalanceService.deductBalance(userId, totalAmount);

        // 결제
        paymentService.processPayment(order);

        // 주문 상태 업데이트 및 저장
        orderService.updateOrderStatus(order.getId(), OrderStatus.COMPLETED );

        // 외부 데이터 플랫폼으로 결과 전송
        paymentService.sendPaymentResult(new OrderSuccessRequest(order));

    }
}
