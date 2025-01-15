package kr.hhplus.be.server.domain.coupon;

public enum CouponStatus {

    ACTIVE,     // 사용 가능한 상태
    USED,       // 사용된 상태 (전체 수량이 소진된 경우)
    EXPIRED     // 만료된 상태

}
