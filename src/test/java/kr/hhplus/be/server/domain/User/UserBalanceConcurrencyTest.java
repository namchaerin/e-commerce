package kr.hhplus.be.server.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserBalanceConcurrencyTest {

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;


    @Test
    @DisplayName("잔액 차감 동시성 제어 테스트 - 낙관적 락 사용")
    public void balanceDeductOptimisticLockingTest() throws InterruptedException {

        User user = new User("user1");
        userRepository.save(user);
        UserBalance balance = new UserBalance(user, new BigDecimal("10000"));
        userBalanceRepository.save(balance);

        CountDownLatch latch = new CountDownLatch(1);

        Thread threadA = new Thread(() -> {
            try {
                userBalanceService.deductBalance(user.getId(), new BigDecimal("5000"));
            } catch (Exception e) {
                System.out.println("트랜잭션 A 오류: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                latch.await();
                assertThrows(OptimisticLockingFailureException.class, () -> {
                    userBalanceService.deductBalance(user.getId(), new BigDecimal("3000"));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        threadA.start();
        threadB.start();

        latch.await();

        Optional<UserBalance> updatedBalance = userBalanceRepository.findByUserId(user.getId());
        assertTrue(updatedBalance.isPresent());
        assertTrue(updatedBalance.get().getBalanceAmount().compareTo(new BigDecimal("5000")) == 0);

    }

}
