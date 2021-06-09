package net.openjdk.reactive;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;

import reactor.core.publisher.Flux;

//https://spring.io/blog/2019/03/06/flight-of-the-flux-1-assembly-vs-subscription
public class HotvsColdFluxTest {

	@Ignore @Test
	public void testColdFlux() throws InterruptedException{
		Flux<Long> clockTicks = Flux.interval(Duration.ofSeconds(1));

		clockTicks.subscribe(tick -> System.out.println("clock1 " + tick + "s"));

		Thread.sleep(1000);

		clockTicks.subscribe(tick -> System.out.println("\tclock2 " + tick + "s"));
		
		Thread.sleep(3000);
	}

	
	@Test
	public void testHotFlux() throws InterruptedException{
		Flux<Long> coldTicks = Flux.interval(Duration.ofSeconds(1));
		Flux<Long >clockTicks = coldTicks.share();
		
		clockTicks.subscribe(tick -> System.out.println("clock1 " + tick + "s"));

		Thread.sleep(2000);

		clockTicks.subscribe(tick -> System.out.println("\tclock2 " + tick + "s"));
		
		Thread.sleep(3000);
	}
	
	@Ignore @Test //(expected= ExecutionException.class)//(expected = ArrayIndexOutOfBoundsException.class)
	public void testJavaStreams() throws ExecutionException, InterruptedException  {
		
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		int seconds = LocalTime.now().getSecond();
		
		List<Integer> source;
		
		if (seconds % 2 == 0) {
			source = IntStream.range(1, 11).boxed().collect(Collectors.toList());
		}else if (seconds % 3 == 0) {
			source = IntStream.range(0, 4).boxed().collect(Collectors.toList());
		}else {
			source = Arrays.asList(1, 2, 3, 4);
		}

		executor.submit(() -> source.get(5)).get();
		
	}

}