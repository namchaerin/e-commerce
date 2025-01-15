package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.UserCoupon;
import kr.hhplus.be.server.domain.User.UserCouponRepository;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;


    public Order createOrder(User user, Coupon coupon, BigDecimal totalAmount) {
        Order order = new Order();
        order.create(user, coupon, totalAmount);
        return orderRepository.save(order);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found"));
        order.updateStatus(status);
    }

}
