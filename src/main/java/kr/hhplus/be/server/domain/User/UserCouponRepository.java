package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.infrastructure.coupon.UserCouponQueryRepository;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, UserCouponQueryRepository {

    UserCoupon findTopByUserAndCouponAndUsedIsFalse(User user, Coupon coupon);

    List<CouponsResponse> getCouponsByUser(User user);

    long countByUserAndCoupon(User user, Coupon coupon);

}
