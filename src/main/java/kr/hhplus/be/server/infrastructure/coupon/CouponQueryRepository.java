package kr.hhplus.be.server.infrastructure.coupon;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CouponQueryRepository {

    // PESSIMISTIC_WRITE 락을 사용하여 쿠폰을 조회
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    Optional<Coupon> findByIdForUpdate(Long couponId);

}
