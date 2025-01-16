package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.presentation.dto.OrderSuccessRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public void processPayment(Order order) {

        Payment payment = new Payment();
        payment.create(order);

        // 결제 성공 처리
        payment.complete();
        paymentRepository.save(payment);
    }

    public void sendPaymentResult(OrderSuccessRequest orderSuccessRequest) {
        log.info("결제를 성공했습니다.");
    }

}
