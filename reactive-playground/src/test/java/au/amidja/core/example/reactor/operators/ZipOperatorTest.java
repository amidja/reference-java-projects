package au.amidja.core.example.reactor.operators;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@DisplayName("Zip Operator testing ")
class ZipOperatorTest {

	
	//https://stackoverflow.com/questions/59742934/can-you-flux-zip-a-mono-and-a-flux-and-and-repeat-the-mono-value-for-every-flux
	@Test
	void testZipFluxWithMono() {
		// a flux that contains 6 elements.
		 final Flux<Integer> userIds = Flux.fromIterable(List.of(1,2,3,4,5,6));
		 final Mono<String> groupLabel = Mono.just("someLabel");
		 
		// this is how to zip up the flux and mono how you'd want, 
		 //     such that every time the flux emits, the mono emits. 
		 final Flux<Tuple2<Integer, String>> zippingFluxToMono = userIds
		         .flatMap(userId -> Mono.just(userId)
		                 .zipWith(groupLabel));
		 
		 zippingFluxToMono.log().subscribe();
	}
	
	
	@Test
	void testFilterWitnMonoInFlux() {
		Mono<Integer> mono = Mono.just(3);
		String[] indexes = {"1", "2", "3", "4", "5", "6"};
        Flux<String> flux = Flux.fromArray(indexes);
        //Way 1
     
        Flux<String> res = mono.flatMapMany(i -> flux.filter(j -> Integer.valueOf(j) % i == 0));        		
        res.subscribe(System.out::println);

	}

	@Test
	void testMonoLookupInFluxforMono() {
		Mono<Integer> mono = Mono.just(3);
		String[] indexes = {"1", "2", "3", "4", "5", "6"};
        Flux<String> flux = Flux.fromArray(indexes);
        Mono<String> res = flux.collectList().map(f ->{
        	String index = "1";
        	return index;       	        	
        });               		
        res.subscribe(System.out::println);
	}
		
	@Test 
	void testFluxFilterWithMapManyUsingMono() {
		Mono<Integer> mono = Mono.just(3);
		Flux<String> flux = Flux.just("1", "2", "3", "4", "5", "6");
		 
		Flux<String> res = flux.collectList().zipWith(mono).log()
				.flatMapMany(tuple -> {
			        List<String> list = tuple.getT1();
			        
			        List<String> result = new ArrayList<>();
			        int x = tuple.getT2();
			        for(String y : list){
			            if(Integer.valueOf(y) % x==0){
			            	result.add(y);
			            }
			        }
			        return (result.isEmpty()) ? Flux.empty(): Flux.fromIterable(result); 
			        
	    });
		
		res.subscribe(System.out::println);
	}
		
	
	@Disabled @Test 
	void testFluxFilterWithManyUsingMono() {
		
		String[] indexes = {"1", "2", "3", "4", "5", "6"};		
		Flux<String> flux = Flux.fromArray(indexes);
		
		Mono<Integer> mono = Mono.just(3);
		 
		Flux<String> res = flux.zipWith(mono).log()
				.flatMap(tuple -> {
			        String index = tuple.getT1();
			        
		//	        List<String> result = new ArrayList<>();
			        int x = tuple.getT2();
		//	        for(String y : list){
			            if(Integer.valueOf(index) % x==0){
			            	return Mono.just(index);
			            }
		//	        }
			        return  Mono.empty();	        
	    });
		
		res.subscribe(System.out::println);
		
		//StepVerifier.create(res).expectNext("3").expectComplete();
	}


}
