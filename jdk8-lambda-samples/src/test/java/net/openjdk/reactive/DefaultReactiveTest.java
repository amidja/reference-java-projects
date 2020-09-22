package net.openjdk.reactive;

import static org.junit.Assert.fail;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DefaultReactiveTest {

	@Test
	public void test() {
//		Mono<String> noData = Mono.empty(); 
//
//		Mono<String> data = Mono.just("foo");

//		Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3); 
//		numbersFromFiveToSeven.subscribe(i -> System.out.println(i)); 
		
		
		Flux<Integer> ints = Flux.range(1, 4) 
			      .map(i -> { 
			        if (i <= 3) return i; 
			        throw new RuntimeException("Got to 4"); 
			      });
		
//			ints.subscribe(i -> System.out.println(i), 
//			      error -> System.err.println("Error: " + error));
//	
			
			ints.subscribe(i -> System.out.println(i),
				    error -> System.err.println("Error " + error),
				    () -> System.out.println("Done"),
				    sub -> sub.request(10));
	}

}
