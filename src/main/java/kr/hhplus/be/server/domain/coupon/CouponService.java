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

import static kr.hhplus.be.server.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final UserRepository userRepository;

    private final UserCouponRepository userCouponRepository;

    private final CouponRepository couponRepository;

    public static final int MAX_COUPON_ISSUE_LIMIT = 3;


    public List<CouponsResponse> getCoupons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        return userCouponRepository.getCouponsByUser(user);
    }

    @Transactional
    public void issueCoupon(Long userId, Long couponId) {
        // 유저와 쿠폰 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        Coupon coupon = couponRepository.findByIdForUpdate(couponId)
                .orElseThrow(() -> new ResourceNotFoundException(COUPON_NOT_FOUND));

        // 1. 쿠폰이 발급 가능한지 체크
        if (coupon.getRemainingQuantity() <= 0) {
            throw new InsufficientStockException(COUPON_EXHAUSTED);
        }

        // 2. 유저가 발급할 수 있는 쿠폰 수량 초과 체크
        long issuedCouponCount = userCouponRepository.countByUserAndCoupon(user, coupon);
        if (issuedCouponCount >= MAX_COUPON_ISSUE_LIMIT) {
            throw new CouponLimitExceededException(COUPON_LIMIT_EXCEEDED);
        }

        // 3. 쿠폰 발급
        coupon.updateRemainingQuantity(coupon.getRemainingQuantity() - 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.addCoupon(user, coupon);
        userCouponRepository.save(userCoupon);

    }

    public Coupon useCoupon(User user, Long couponId) {

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new InvalidCouponException(COUPON_NOT_FOUND));

        UserCoupon userCoupon = userCouponRepository.findTopByUserAndCouponAndUsedIsFalse(user, coupon);
        if (userCoupon == null) {
            throw new InvalidCouponException(COUPON_NOT_OWNED);
        }

        userCoupon.use();

        return coupon;
    }

}
