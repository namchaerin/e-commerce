package kr.hhplus.be.server.domain.coupon;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.common.exceptions.CouponLimitExceededException;
import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.InvalidCouponException;
import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.UserCoupon;
import kr.hhplus.be.server.domain.User.UserCouponRepository;
import kr.hhplus.be.server.domain.User.UserRepository;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final UserRepository userRepository;

    private final UserCouponRepository userCouponRepository;

    private final CouponRepository couponRepository;

    public static final int MAX_COUPON_ISSUE_LIMIT = 3;


    public List<CouponsResponse> getCoupons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("등록되지 않은 사용자입니다."));

        return userCouponRepository.getCouponsByUser(user);
    }

    @Transactional
    public void issueCoupon(Long userId, Long couponId) {
        // 유저와 쿠폰 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("등록되지 않은 사용자입니다."));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("쿠폰을 찾을 수 없습니다."));

        // 1. 쿠폰이 발급 가능한지 체크
        if (coupon.getRemainingQuantity() <= 0) {
            throw new InsufficientStockException("쿠폰 수량이 부족합니다.");
        }

        // 2. 유저가 발급할 수 있는 쿠폰 수량 초과 체크
        long issuedCouponCount = userCouponRepository.countByUserAndCoupon(user, coupon);
        if (issuedCouponCount >= MAX_COUPON_ISSUE_LIMIT) {
            throw new CouponLimitExceededException("유저별 동일 쿠폰 발급 한도를 초과했습니다.");
        }

        // 3. 쿠폰 발급 (비관적 락 사용)
        coupon = couponRepository.findByIdForUpdate(couponId);  // PESSIMISTIC_WRITE 락을 사용하여 동시성 문제 해결

        if (coupon.getRemainingQuantity() > 0) {
            coupon.issueCoupon();
            couponRepository.save(coupon);

            // UserCoupon 저장 (쿠폰 발급)
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.addCoupon(user, coupon);
            userCouponRepository.save(userCoupon);

        } else {
            throw new InsufficientStockException("쿠폰 수량이 부족합니다.");
        }

    }

    public Coupon useCoupon(User user, Long couponId) {

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
                InvalidCouponException::new);

        UserCoupon userCoupon = userCouponRepository.findTopByUserAndCouponAndUsedIsFalse(user, coupon);
        if (userCoupon == null) {
            throw new InvalidCouponException("사용자가 보유하고 있지 않은 쿠폰입니다.");
        }

        userCoupon.use();

        return coupon;
    }

}
