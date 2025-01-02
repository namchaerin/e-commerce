package kr.hhplus.be.server.presentation.controller;

import kr.hhplus.be.server.presentation.dto.BalanceRequest;
import kr.hhplus.be.server.presentation.dto.BalanceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    @GetMapping
    public BalanceResponse getBalance(
            @RequestParam Long userId) {
        return new BalanceResponse(1L, 50_000d);
    }

    @PostMapping
    public BalanceResponse rechargeBalance(
            @RequestBody BalanceRequest balanceRequest) {
        return new BalanceResponse(1L, 60_000d);
    }

}
