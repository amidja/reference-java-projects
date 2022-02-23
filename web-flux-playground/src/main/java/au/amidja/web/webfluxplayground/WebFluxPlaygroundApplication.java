package au.amidja.web.webfluxplayground;

import au.amidja.web.webfluxplayground.product.core.model.model.Product;
import au.amidja.web.webfluxplayground.product.core.model.model.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebFluxPlaygroundApplication {

	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WebFluxPlaygroundApplication.class);
		
		//https://springhow.com/spring-boot-startup-actuator-endpoint/
  		
        BufferingApplicationStartup applicationStartup = new BufferingApplicationStartup(2048);
        applicationStartup.addFilter(startupStep -> startupStep.getName().startsWith("spring.beans.instantiate"));
        applicationStartup.addFilter(startupStep -> startupStep.getName().startsWith("pring.context.base-packages.scan"));        
        
        
        application.setApplicationStartup(applicationStartup);
		
		
		application.run(args);
		//SpringApplication.run(WebFluxPlaygroundApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations, ProductRepository repository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null, "Big Latte", 2.99),
					new Product(null, "Big Decaf", 2.49),
					new Product(null, "Green Tea", 1.95)
			).flatMap(p -> repository.save(p));

			productFlux.thenMany(repository.findAll()).subscribe(System.out::println);

			/*
			//When working with real MongoDB Server
			operations.collectionExists(Product.class)
					.flatMap(exists -> exists ? operations.dropCollection(Product.class) : Mono.just(exists))
					.thenMany(v -> operations.createCollection(Product.class))
					.thenMany(productFlux)
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
			*/
		};


	}

}

