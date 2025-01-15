package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.presentation.dto.OrderSuccessRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final WebClient webClient;

    @Value("${mock.api.payment-url}")
    private String paymentApiUrl;

    public PaymentService(PaymentRepository paymentRepository, WebClient.Builder webClientBuilder) {
        this.paymentRepository = paymentRepository;
        this.webClient = webClientBuilder.baseUrl(paymentApiUrl).build();
    }


    public void processPayment(Order order) {

        Payment payment = new Payment();
        payment.create(order);

        // 결제 성공 처리
        payment.complete();
        paymentRepository.save(payment);

    }

    public void sendPaymentResult(OrderSuccessRequest orderSuccessRequest) {
        webClient.post()
                .uri("/mock-api/payment-result")
                .bodyValue(orderSuccessRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.out.println("Error sending payment result: " + error.getMessage()))
                .subscribe(response -> System.out.println("Response from Mock API: " + response));
    }


}
