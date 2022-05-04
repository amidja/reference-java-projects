package au.amidja.web.webfluxplayground.product.core.model.model.repository;

import au.amidja.web.webfluxplayground.product.core.model.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
