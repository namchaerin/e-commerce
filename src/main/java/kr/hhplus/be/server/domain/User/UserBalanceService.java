package kr.hhplus.be.server.domain.User;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.common.exceptions.InsufficientStockException;
import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import kr.hhplus.be.server.presentation.dto.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import static kr.hhplus.be.server.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    public BalanceResponse getBalanceByUserId(Long userId) {
        UserBalance balance = userBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        return BalanceResponse.of(balance);
    }

    public BalanceResponse updateBalance(Long userId, BigDecimal rechargingBalance) {

        UserBalance balance = userBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        balance.addAmount(rechargingBalance);

        BigDecimal balanceAmount = balance.getBalanceAmount();

        userBalanceRepository.save(balance);

        return new BalanceResponse(userId, balanceAmount);
    }

    @Transactional
    public void deductBalance(Long userId, BigDecimal amount) {
        try {
            UserBalance balance = userBalanceRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

            balance.subtractAmount(amount);
            userBalanceRepository.save(balance);
        } catch (OptimisticLockException e) {
            throw new RuntimeException(BALANCE_UPDATE_FAILED.getMessage());
        }
    }

}
