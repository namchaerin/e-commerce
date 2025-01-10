package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.presentation.dto.BalanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserBalanceServiceTest {

    @Mock
    private UserBalanceRepository userBalanceRepository;

    @InjectMocks
    private UserBalanceService userBalanceService;


    @DisplayName("유저 아이디로 잔액 조회 테스트 - 성공")
    @Test
    void testGetBalanceByUserId_success() {

        //given
        User user1 = new User("사용자1");
        UserBalance balance = new UserBalance(user1, new BigDecimal("10000.0"));

        when(userBalanceRepository.findByUserId(user1.getId())).thenReturn(Optional.of(balance));

        //when
        BalanceResponse result = userBalanceService.getBalanceByUserId(user1.getId());

        //then
        assertThat(result.getBalance()).isEqualTo(new BigDecimal("10000.0"));

    }

    @DisplayName("유저 아이디로 잔액 조회 테스트 - 실패")
    @Test
    void testGetBalanceByUserId_notFound() {

        //given
        Long userId = 1L;
        User user1 = new User("사용자1");
        UserBalance balance = new UserBalance(user1, new BigDecimal("10000.0"));

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));

        //when

        //then
        assertThrows(ResourceNotFoundException.class,
                () -> userBalanceService.getBalanceByUserId(2L));

    }

    @DisplayName("잔액 업데이트 테스트 - 성공")
    @Test
    void testUpdateBalance_success() {

        //given
        Long userId = 1L;
        User user = new User("사용자1");
        UserBalance balance = new UserBalance(user, new BigDecimal("10000.0"));

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));

        //when
        BalanceResponse result = userBalanceService.updateBalance(userId, new BigDecimal("5000.0"));

        //then
        assertThat(result.getBalance()).isEqualTo(new BigDecimal("15000.0"));

    }

    @DisplayName("잔액 업데이트 테스트 - 실패 case1) 0원 이하의 금액을 충전하려고 했을 때")
    @Test
    void testUpdateBalance_whenRechargingBalanceIsUnderZero() {

        //given
        Long userId = 1L;
        User user = new User("사용자1");
        UserBalance balance = new UserBalance(user, new BigDecimal("10000.0"));

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> userBalanceService.updateBalance(userId, new BigDecimal("-5000")));

    }

    @DisplayName("잔액 업데이트 테스트 - 실패 case2) 최대 충전 가능 금액 이상의 금액을 충전하려고 했을 때")
    @Test
    void testUpdateBalance_whenRechargingBalanceOverMax() {

        //given
        Long userId = 1L;
        User user = new User("사용자1");
        UserBalance balance = new UserBalance(user, new BigDecimal("10000.0"));

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> userBalanceService.updateBalance(userId, new BigDecimal("2000000")));

    }

}