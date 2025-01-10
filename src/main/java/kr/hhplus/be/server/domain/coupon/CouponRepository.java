package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.infrastructure.coupon.CouponQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponQueryRepository {

}
