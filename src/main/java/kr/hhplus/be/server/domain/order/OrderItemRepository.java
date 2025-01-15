package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.infrastructure.order.OrderItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemQueryRepository {

}
