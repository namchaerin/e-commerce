package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemQueryRepository {

    @Query(
            "SELECT new kr.hhplus.be.server.infrastructure.order.TopSellingItemDto(OI.product, SUM(OI.quantity) AS totalSales) " +
            "FROM OrderItem OI " +
            "JOIN OI.order O " +
            "WHERE O.orderDateTime >= :fromDate " +
            "AND O.status = 'COMPLETED' " +
            "GROUP BY OI.product " +
            "ORDER BY SUM(OI.quantity) DESC"
    )
    List<TopSellingItemDto> findTopSellingProducts(LocalDateTime fromDate, Pageable limit);

}
