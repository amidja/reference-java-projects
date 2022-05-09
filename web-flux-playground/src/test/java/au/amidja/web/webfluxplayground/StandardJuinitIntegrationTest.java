package au.amidja.web.webfluxplayground;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import au.amidja.web.webfluxplayground.product.core.model.Product;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Slf4j
class StandardJuinitIntegrationTest {

	@Test
	void testWebClinet() {
		WebClient client = WebClient.create();

		WebClient.ResponseSpec responseSpec = client.get()
		    .uri("http://localhost:8080/products")
		    .accept(MediaType.APPLICATION_JSON)
		    .retrieve();
		
		Product firstProduct = responseSpec
				.bodyToFlux(Product.class).blockFirst();
		
		Assertions.assertThat(firstProduct).isNot(null);
		log.info("First product is : {}", firstProduct);
	}

}
