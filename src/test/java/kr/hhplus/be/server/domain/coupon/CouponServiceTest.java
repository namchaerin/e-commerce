package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.common.exceptions.CouponLimitExceededException;
import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.InvalidCouponException;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.UserCoupon;
import kr.hhplus.be.server.domain.User.UserCouponRepository;
import kr.hhplus.be.server.domain.User.UserRepository;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;
    
    @Mock
    private UserCouponRepository userCouponRepository;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CouponService couponService;


    @DisplayName("사용자가 가진 쿠폰 목록 조회 테스트 - 성공")
    @Test
    void testGetCouponsByUser_success() {

        //given
        Long userId = 1L;
        Long couponId_1 = 1L;
        Long couponId_2 = 2L;
        User user = new User("사용자");
        Coupon coupon = new Coupon("code");

        List<CouponsResponse> list = Arrays.asList(
                new CouponsResponse(couponId_1, "code1", 20, LocalDateTime.of(2025, 1, 5, 12, 0, 0)),
                new CouponsResponse(couponId_2, "code2", 10, LocalDateTime.of(2025, 1, 5, 12, 0, 0)),
                new CouponsResponse(couponId_2, "code2", 10, LocalDateTime.of(2025, 1, 5, 12, 0, 0)));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userCouponRepository.getCouponsByUser(user)).thenReturn(list);


        //when
        List<CouponsResponse> result = couponService.getCoupons(userId);


        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getCouponId()).isEqualTo(1L);

    }

    @DisplayName("쿠폰 발급 테스트 - case1. 쿠폰 수량이 부족한 경우")
    @Test
    void testIssueCoupon_whenCouponStockIsInsufficient() {

        // given
        Long userId = 1L;
        Long couponId = 1L;

        User user = new User("사용자");
        Coupon coupon = new Coupon("code");
        coupon.updateRemainingQuantity(0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(couponRepository.findByIdForUpdate(couponId)).thenReturn(Optional.of(coupon));

        // when, then
        assertThrows(InsufficientStockException.class, () -> couponService.issueCoupon(userId, couponId));
    }

    @DisplayName("쿠폰 발급 테스트 - case2. 유저가 한 쿠폰에 대하여 이미 발급 가능 수만큼 가지고 있는 경우")
    @Test
    void testIssueCoupon_whenUserHasMaxCoupons() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        User user = new User("사용자");
        Coupon coupon = new Coupon("code");
        coupon.updateRemainingQuantity(10);

        // 유저가 이미 3개 발급한 상태
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(couponRepository.findByIdForUpdate(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.countByUserAndCoupon(user, coupon)).thenReturn(3L); // 3개 이미 발급됨

        // when, then
        assertThrows(CouponLimitExceededException.class, () -> couponService.issueCoupon(userId, couponId));
    }

    @DisplayName("쿠폰 발급 테스트 - case3. 쿠폰 재고도 있고, 유저가 해당 쿠폰을 더 발급 가능한 상태인 경우")
    @Test
    void testIssueCoupon_whenCouponIsIssuedSuccessfully() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        User user = new User("사용자");
        Coupon coupon = new Coupon("code");
        coupon.updateRemainingQuantity(10);

        // 유저가 이미 2개 발급한 상태
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(couponRepository.findByIdForUpdate(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.countByUserAndCoupon(user, coupon)).thenReturn(2L);

        // when
        couponService.issueCoupon(userId, couponId);

        // then
        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(userCouponRepository, times(1)).save(any(UserCoupon.class));

    }

    @DisplayName("쿠폰 사용 테스트 - case1. 쿠폰이 존재하지 않는 경우")
    @Test
    void testUseCoupon_whenCouponNotFound() {

        // given
        Long userId = 1L;
        Long couponId = 1L;
        User user = new User("사용자");

        // 쿠폰이 존재하지 않을 때
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(InvalidCouponException.class, () -> couponService.useCoupon(user, couponId));

    }

    @DisplayName("쿠폰 사용 테스트 - case2. 유효하지 않은 쿠폰인 경우")
    @Test
    void testUseCoupon_whenUserHasNoValidCoupon() {

        // given
        Long userId = 1L;
        Long couponId = 1L;
        User user = new User("사용자");
        Coupon coupon = new Coupon("code");

        // 유저가 쿠폰을 가지고 있지 않거나, 이미 사용된 쿠폰인 경우
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findTopByUserAndCouponAndUsedIsFalse(user, coupon)).thenReturn(null);

        // when, then
        assertThrows(InvalidCouponException.class, () -> couponService.useCoupon(user, couponId));

    }

    @DisplayName("쿠폰 사용 테스트 - case3. 쿠폰 사용 성공")
    @Test
    void testUseCoupon_whenCouponIsUsedSuccessfully() {

        // given
        Long userId = 1L;
        Long couponId = 1L;
        User user = new User("사용자");
        Coupon coupon = new Coupon("code");
        UserCoupon userCoupon = mock(UserCoupon.class);

        // 쿠폰이 존재하고, 유저가 해당 쿠폰을 가지고 있으며 사용되지 않은 경우
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findTopByUserAndCouponAndUsedIsFalse(user, coupon)).thenReturn(userCoupon);

        // when
        Coupon returnedCoupon = couponService.useCoupon(user, couponId);

        // then
        assertEquals(coupon, returnedCoupon);
        verify(userCoupon, times(1)).use(); // UserCoupon의 use() 메소드가 호출되었는지 확인

    }


}