package au.amidja.core.example.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Simple Mono reactive publisher ")

public class SimpleOperatorTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleOperatorTest.class);

    @Test
    void simpleMapOperator() {
        System.out.println("Map Operator:");

        Flux.range(1, 5)
                .map(i -> i*10)
                .subscribe(System.out::println);
    }

    @Test
    void simpleFlatMapOperator() {
        Function<Integer, Publisher<Integer>> mapper = i -> Flux.range(i*10, 2);

        Flux<Integer> testFlux = Flux.range(1, 5).flatMap(mapper);

        System.out.println("Flat Map Operator:");
        testFlux.subscribe(System.out::println);

    }

    @Test
    public void givenInputStream_whenCallingTheMapOperator_thenItemsAreTransformed() {
        Function<String, String> mapper = String::toUpperCase;
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.map(mapper);

        StepVerifier.create(outFlux)
                .expectNext("BAELDUNG", ".", "COM")
                .expectComplete()
                .verify();
    }


    @Test
    public void givenInputStream_whenCallingTheFlatMapOperator_thenItemsAreFlatten() {
        Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.flatMap(mapper);

        List<String> output = new ArrayList<>();
        outFlux.subscribe(output::add);
        assertThat(output).containsExactlyInAnyOrder("B", "A", "E", "L", "D", "U", "N", "G", ".", "C", "O", "M");
    }

}
