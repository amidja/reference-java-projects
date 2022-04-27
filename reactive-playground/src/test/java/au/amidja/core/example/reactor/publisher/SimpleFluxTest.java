package au.amidja.core.example.reactor.publisher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@DisplayName("Simple Mono reactive publisher ")

public class SimpleFluxTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleFluxTest.class);

    void firstSimpleFlux() {
        //Nothing happens unless you subscribe to a publisher.
        Flux.just("A", "B", "C").log()
                .subscribe();
    }

    @Test
    void fluxFromIterable() {
        Flux.fromIterable(Arrays.asList("a", "b", "c"))
                .log()
                .subscribe();
    }


    @Test
    void fluxLimitRate() {
        Flux.range(1, 12).log()
                .limitRate(3) //requesting 3 elements at the time until flux completes
                .subscribe();
    }

}
