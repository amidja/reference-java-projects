package au.amidja.core.example.reactor.context;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

@DisplayName("Simple Context Test ")
public class SimpleContextTest {

	private static final Logger log = LoggerFactory.getLogger(SimpleContextTest.class);

	
	
	/**
	 *  Context in a reactive stream is essentially a map that holds key-value pairs,
	 *  accessible across different stages of the stream.
	 *  
	 *  References:
	 *   - https://medium.com/@ranjeetk.developer/context-java-reactive-programming-f482febc184c
	 *   - https://projectreactor.io/docs/core/release/reference/advancedFeatures/context.html 
	 *  
	 */
	// 
	// 

	@DisplayName("Context Demo")
	@Nested
	public class SimpleContextDemo {
		
		@Test
		public void append_world_to_hello() {
			String key = "message";
			
			Mono<String> r = Mono.just("Hello")
			    .flatMap(s -> Mono.deferContextual(ctx ->
			         Mono.just(s + " " + ctx.get(key))))
			    .contextWrite(ctx -> ctx.put(key, "World"));

			StepVerifier.create(r)
			            .expectNext("Hello World")
			            .verifyComplete();					
		
		
		}
		
		@Test
		public void say_hello_to_userId() throws Exception{
		
			String key = "userId";
								
			Mono<String> pipeline = Mono.just("Hello, User!")					
					.flatMap(message -> Mono.deferContextual(ctx -> {
						String userId = ctx.get(key);
						return Mono.just(message + " (User ID: " + userId + ")");
						}))
					.contextWrite(Context.of(key, "12345"))
					;

			pipeline.subscribe(System.out::println);
			
			StepVerifier.create(pipeline)
				.expectNext("Hello, User! (User ID: 12345)")
				.verifyComplete();
			
	
			//The relative positions of where you write to the Context and where you read from it matters. 
			//The Context is immutable and its content can only be seen by operators above it, as demonstrated in the following example:
			
			pipeline = Mono.just("Hello, User!")
					.contextWrite(Context.of(key, "12345"))
					.flatMap(message -> Mono.deferContextual(ctx -> {
						String userId = ctx.get(key);
						return Mono.just(message + " (User ID: " + userId + ")");
						}))				
					;
	 		
			StepVerifier.create(pipeline)
				.expectErrorMatches(throwable -> throwable instanceof NoSuchElementException)			
				.verify();
			
			
			//Update Context

			pipeline = Mono.just("Hello, User!")
					.contextWrite(Context.of(key, "12345"))
					.contextWrite(ctx -> ctx.put(key, "67890"))
					.flatMap(message -> Mono.deferContextual(ctx -> {
						String userId = ctx.get(key);
						return Mono.just(message + " (User ID: " + userId + ")");
						}))					
					.contextWrite(ctx -> ctx.put(key, "67890"))
					;
								
			pipeline.subscribe(System.out::println);
					
			StepVerifier.create(pipeline)
				.expectNext("Hello, User! (User ID: 67890)")
				.verifyComplete();
							
		}

	}
}
