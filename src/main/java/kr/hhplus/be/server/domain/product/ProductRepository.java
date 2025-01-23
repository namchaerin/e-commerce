package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.infrastructure.product.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {

}
