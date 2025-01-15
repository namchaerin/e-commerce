package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.presentation.dto.CouponsResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCouponQueryRepository {

    @Query(
            "SELECT new kr.hhplus.be.server.presentation.dto.CouponsResponse(C.id, C.code, C.discountRate, C.validUntil) " +
            "FROM UserCoupon UC " +
            "JOIN UC.coupon C " +
            "WHERE UC.user = :user")
    List<CouponsResponse> getCouponsByUser(User user);

}
