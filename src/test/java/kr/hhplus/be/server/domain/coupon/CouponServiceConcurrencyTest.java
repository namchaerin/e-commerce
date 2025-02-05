package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.UserCouponRepository;
import kr.hhplus.be.server.domain.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CouponServiceConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    private Long userId1;
    private Long userId2;
    private Long couponId;


    @BeforeEach
    public void setUp() {

        User user1 = new User("user1");
        userRepository.save(user1);
        userId1 = user1.getId();

        User user2 = new User("user2");
        userRepository.save(user2);
        userId2 = user2.getId();

        Coupon coupon = new Coupon("coupon1", 10, 30, 1, LocalDateTime.of(2025, 1, 10, 0, 0, 0), LocalDateTime.of(2025, 1, 20, 0, 0, 0), CouponStatus.ACTIVE);
        couponRepository.save(coupon);
        couponId = coupon.getId();
    }

    @DisplayName("쿠폰 발급 동시성 제어 테스트")
    @Test
    public void couponIssueWithMultipleUsersTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(2);

        executorService.submit(() -> {
            try {
                couponService.issueCoupon(userId1, couponId);
                System.out.println("user1 요청: 성공");
            } catch (Exception e) {
                System.out.println("user1 요청 실패: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                couponService.issueCoupon(userId2, couponId);
                System.out.println("user2 요청: 성공");
            } catch (Exception e) {
                System.out.println("user2 요청 실패: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        Coupon updatedCoupon = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("쿠폰 조회 실패"));
        assertEquals(0, updatedCoupon.getRemainingQuantity());

        executorService.shutdown();
    }

}