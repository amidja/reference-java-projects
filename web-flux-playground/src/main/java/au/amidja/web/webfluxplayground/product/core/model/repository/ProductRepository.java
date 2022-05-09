package au.amidja.web.webfluxplayground.product.core.model.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import au.amidja.web.webfluxplayground.product.core.model.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}