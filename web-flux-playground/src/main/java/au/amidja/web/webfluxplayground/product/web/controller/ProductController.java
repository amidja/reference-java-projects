package au.amidja.web.webfluxplayground.product.web.controller;

import au.amidja.web.webfluxplayground.product.core.model.model.Product;
import au.amidja.web.webfluxplayground.product.core.model.model.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductRepository repostory;

    public ProductController (ProductRepository repository){
        this.repostory = repository;
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getAllProducts(){
        return repostory.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id){
        return repostory.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product){
        return repostory.save(product);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product product){
        return repostory.findById(id)
                .flatMap(existingProduct ->{
                   existingProduct.setName(product.getName());
                   existingProduct.setPrice(product.getPrice());
                   return repostory.save(existingProduct);
                })
                .map(updatedProduct -> ResponseEntity.ok(updatedProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                ;
    }
}