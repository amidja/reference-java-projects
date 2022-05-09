package au.amidja.web.webfluxplayground;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import au.amidja.web.webfluxplayground.product.core.model.Product;
import au.amidja.web.webfluxplayground.product.core.model.repository.ProductRepository;
import au.amidja.web.webfluxplayground.product.functional.handler.ProductHandler;
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
	
	@Bean
	RouterFunction<ServerResponse> routes(ProductHandler handler) {
//		return route()
//				.GET("functional/products/events", accept(TEXT_EVENT_STREAM), handler::getProductEvents)
//				.GET("functional/products/{id}", accept(APPLICATION_JSON), handler::getProduct)
//				.GET("functional/products", accept(APPLICATION_JSON), handler::getAllProducts)
//				.PUT("functional/products/{id}", accept(APPLICATION_JSON), handler::updateProduct)
//				.POST("functional/products", contentType(APPLICATION_JSON), handler::saveProduct)
//				.DELETE("functional/products/{id}", accept(APPLICATION_JSON), handler::deleteProduct)
//				.DELETE("functional/products", accept(APPLICATION_JSON), handler::deleteAllProducts)
//				.build();
		return route()
				.path("functional/products",
						builder -> builder
							.nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
								nestedBuilder -> nestedBuilder
									.GET("/events", handler::getProductEvents)
									.GET("/{id}", handler::getProduct)
									.GET(handler::getAllProducts)
									.PUT("/{id}", handler::updateProduct)
									.POST(handler::saveProduct)
							)
							.DELETE("/{id}", handler::deleteProduct)
							.DELETE(handler::deleteAllProducts)
				).build();
	}
}