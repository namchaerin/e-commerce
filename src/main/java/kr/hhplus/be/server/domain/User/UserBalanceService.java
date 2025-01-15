package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.presentation.dto.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    public BalanceResponse getBalanceByUserId(Long userId) {
        UserBalance balance = userBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자의 계정이 존재하지 않습니다."));

        return BalanceResponse.of(balance);
    }

    public BalanceResponse updateBalance(Long userId, BigDecimal rechargingBalance) {

        UserBalance balance = userBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자의 계정이 존재하지 않습니다."));

        balance.addAmount(rechargingBalance);

        BigDecimal balanceAmount = balance.getBalanceAmount();

        userBalanceRepository.save(balance);

        return new BalanceResponse(userId, balanceAmount);
    }
}
