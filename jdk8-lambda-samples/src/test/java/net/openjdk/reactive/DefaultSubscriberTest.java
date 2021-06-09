package net.openjdk.reactive;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class DefaultSubscriberTest {

	@Test
	public void test() {
		SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();

		Flux<Integer> ints = Flux.range(1, 4);
		
		ints.subscribe(i -> System.out.println(i),
		    error -> System.err.println("Error " + error),
		    () -> {System.out.println("Done");},
		    s -> s.request(10));
		
		ints.subscribe(ss);
	}

}
