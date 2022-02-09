package au.amidja.web.webfluxplayground;

import au.amidja.web.webfluxplayground.core.model.Product;
import au.amidja.web.webfluxplayground.core.model.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebFluxPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WebFluxPlaygroundApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
	}

	@Bean
	CommandLineRunner init(ProductRepository repository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null, "Big Latte", 2.99),
					new Product(null, "Big Decaf", 2.49),
					new Product(null, "Green Tea", 1.95)
			).flatMap(p -> repository.save(p));

			productFlux.thenMany(repository.findAll()).subscribe(System.out::println);
		};
	}

}

