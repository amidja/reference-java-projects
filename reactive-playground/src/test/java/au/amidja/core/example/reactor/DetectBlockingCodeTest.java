package au.amidja.core.example.reactor;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

class DetectBlockingCodeTest {

	@Test
	void blockingInNonBlockingThreadsShouldNotBeAllowed() {
		StepVerifier.create(this.blockingIsNotAllowed()).expectErrorMatches(e -> {
			e.printStackTrace();

			return e instanceof Error && e.getMessage().contains("Blocking call!");
		}).verify();
	}

	@Test
	void blockingInBlockingThreadsShouldBeAllowed() {
		StepVerifier.create(this.blockingIsAllowed()).expectNext(1).verifyComplete();
	}

	
	public Mono<Integer> blockingIsAllowed() {
		return getBlockingMono().subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<Integer> blockingIsNotAllowed() {
		return getBlockingMono().subscribeOn(Schedulers.parallel());
	}

	
	private Mono<Integer> getBlockingMono() {
		return Mono.just(1).doOnNext(i -> block());
	}

	private void block() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}


}
