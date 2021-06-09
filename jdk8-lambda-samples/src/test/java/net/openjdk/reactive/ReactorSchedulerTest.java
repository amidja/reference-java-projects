package net.openjdk.reactive;

import java.time.Duration;

import org.junit.Ignore;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

//https://spring.io/blog/2019/12/13/flight-of-the-flux-3-hopping-threads-and-schedulers
//https://www.baeldung.com/spring-webflux-concurrency
public class ReactorSchedulerTest {

	@Test @Ignore
	public void testAsyncProducer() throws InterruptedException{
		Flux<Integer>sourceSync = Flux.just(1,2);
		
		sourceSync
		   .map(i -> i + ". element: " + i)
		   .subscribe(System.out::println);

		Flux<Integer>sourceAsync = Flux.<Integer>create(emitter -> {
		    System.out.println(Thread.currentThread().getName());
		    emitter.next(1);
		    emitter.complete();
		}).publishOn(Schedulers.single());
					
		sourceAsync
		   .map(i -> i + ". element: " + i)
		   .subscribeOn(Schedulers.single())
		   .subscribe(System.out::println);
		
		System.out.println("Before or after?");		
		Thread.sleep(2000);
	}
	
	@Test
	public void testScheduler() throws InterruptedException{
		Flux.just("hello")
			//just emits its value on the elastic scheduler.
	    	.doOnNext(v -> System.out.println("just: " + Thread.currentThread().getName()))
	    	//the first doOnNext receives that value on the same thread and prints it out: just elastic-1
	    	.publishOn(Schedulers.boundedElastic())
	    	.doOnNext(v -> System.out.println("publishOn: " + Thread.currentThread().getName()))
	    	//delayElements is a time operator, so by default it publishes data on the Schedulers.parallel() scheduler.
	    	.delayElements(Duration.ofMillis(500))
	    	//on the data path, subscribeOn does nothing but propagating signal on the same thread.
	    	.subscribeOn(Schedulers.boundedElastic())
	    	//on the data path, the lambda(s) passed to subscribe(...) are executed on the thread 
	    	//in which data signals are received, so the lambda prints hello delayed parallel-1
	    	.subscribe(v -> System.out.println("`"+v+"`"+ " delayed " + Thread.currentThread().getName()));
			Thread.sleep(1000);
	}

}