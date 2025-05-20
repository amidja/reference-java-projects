package au.amidja.core.example.reactor.publisher;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//https://www.baeldung.com/java-mono-list-to-flux
class MonoListToFlux {

	@Test
	void test() {
		Mono<List<String>> monoList = monoOfList();
		Flux<String> fluxFromMonoList = monoTofluxUsingFlatMapMany(monoList);
		fluxFromMonoList.subscribe(System.out::println);
	}

	
	Mono<List<String>> monoOfList() {
	    List<String> list = new ArrayList<>();
	    list.add("one");
	    list.add("two");
	    list.add("three");
	    list.add("four");

	    return Mono.just(list);
	}
	
	<T> Flux<T> monoTofluxUsingFlatMapMany(Mono<List<T>> monoList) {
	    return monoList
	      .flatMapMany(Flux::fromIterable)
	      //.log()
	      ;
	}
}
