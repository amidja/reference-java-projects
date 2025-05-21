package au.amidja.core.example.reactor.publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DisplayName("Mono and List reactive publisher ")
class MonoListToFlux {

	
	@Test
	void testMonoListFilterWithStream() {
		//Filtering a list contained in mono using list stream
		//Not the best way to do it!
		
		final String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
		final List<String> carsList = Arrays.asList(cars);
		
		Mono<List<String>> carsMonoList = Mono.just(carsList);
		
		carsMonoList
			.map(l -> {
				List<String> filterdList = l.stream()
						.filter(c -> c.equalsIgnoreCase("BMW")).collect(Collectors.toList());
				return filterdList;
			})
			.filter(s -> !s.isEmpty()).log()
			.subscribe(System.out::println);
	}

	
	@Test
	void testFilterListWithMonoReturningFlux() {
		String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
		List<String> carsList = Arrays.asList(cars);
		Mono<String> carMono = Mono.just("BMW");
		
		Flux<String> otherCars = carMono
				.flatMapMany(c ->{
					List<String> filteredList = new ArrayList<>();
					
					Iterator<String> itr = carsList.iterator();
					
					while (itr.hasNext()) { 
						String x = itr.next(); 
						if (!x.equalsIgnoreCase(c)) filteredList.add(x); 
					} 
					
					return Flux.fromIterable(filteredList);
					
				});
		
		otherCars.subscribe(System.out::println);
	}
	
	
	@Test	
	void testConvertMonoListToFlux() {
		//https://www.baeldung.com/java-mono-list-to-flux
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
