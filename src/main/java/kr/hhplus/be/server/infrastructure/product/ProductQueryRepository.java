package kr.hhplus.be.server.infrastructure.product;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.product.Product;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductQueryRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithLock(Long productId);

}
