package kr.hhplus.be.server.domain.payment;

public enum PaymentStatus {
    PENDING,      // 결제 대기 중
    COMPLETED,    // 결제 완료
    FAILED,       // 결제 실패
    CANCELED,     // 결제 취소
    REFUNDED,     // 환불됨
    PROCESSING,   // 처리 중
    EXPIRED,      // 결제 만료됨
    DECLINED      // 결제 거부됨
}
