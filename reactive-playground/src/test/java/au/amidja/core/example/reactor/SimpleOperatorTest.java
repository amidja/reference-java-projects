package au.amidja.core.example.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@DisplayName("Simple Mono reactive publisher ")

public class SimpleOperatorTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleOperatorTest.class);

    @Test
    void simpleFlatMap() {
        Flux.range(1, 5)
                .map(i -> i*10)
                .subscribe(s -> log.debug("{}", s));
    }

    @Test
    void simpleFlatMapMany() {
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
