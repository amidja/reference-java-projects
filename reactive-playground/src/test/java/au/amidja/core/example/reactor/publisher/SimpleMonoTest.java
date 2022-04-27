package au.amidja.core.example.reactor.publisher;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@DisplayName("Simple Mono reactive publisher ")
public class SimpleMonoTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleMonoTest.class);

    @Test
    @Disabled
    void monoWithConsumer() {
        //Nothing happens unless you subscribe to a publisher.
        Mono.just("A").log()
                .subscribe(s -> log.debug(s), null, () -> log.debug("Done"));
    }

    @Test
    void onErrorTerminateMono() {
        Mono.just("A")
                .map(value ->{return new RuntimeException("My Runtime Exception");})
                .log().subscribe();
    }

    @Test
    void onErrorReturnMono() {
        Mono.error(new RuntimeException("My Runtime Exception"))
                .onErrorResume(e -> {
                    log.error("Caught: ", e);
                    return Mono.just("My Data");
                })
                .log()
                .subscribe();
    }

    @Test
    void onErrorReturnValue() {
        Mono.error(new RuntimeException("My Runtime Exception"))
                .onErrorReturn("My Data")
                .log()
                .subscribe();
    }
}