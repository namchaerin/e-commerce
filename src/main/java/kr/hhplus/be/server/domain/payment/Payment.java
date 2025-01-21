package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import kr.hhplus.be.server.domain.order.Order;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paymentDateTime;



    public void complete() {
        this.status = PaymentStatus.COMPLETED;
        this.paymentDateTime = LocalDateTime.now();
    }

    public void cancel() {
        this.status = PaymentStatus.CANCELED;
    }

    public void create(Order order) {
        this.order = order;
        this.amount = order.getTotalAmount();
        this.status = PaymentStatus.PENDING;
        this.paymentDateTime = LocalDateTime.now();
    }

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

}
