package au.amidja.core.example.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DisplayName("Mono reactive publisher ")
public class HotMonoTest {

    private static final Logger log = LoggerFactory.getLogger(HotMonoTest.class);

    @DisplayName("Hot Mono Publisher")
    @Nested
    public class HotMonoPublisher{

        @Test
        public void whenUsingMonoJust_thenEagerEvaluation() throws InterruptedException {
            //sampleMsg, created as soon as defined with just().
            //with defer() StepVerifier executes the method sampleMsg on each subscription
            Mono<String> msg = sampleMsg("Eager Publisher");

            log.debug("Intermediate Test Message....");

            StepVerifier.create(msg)
                    .expectNext("Eager Publisher")
                    .verifyComplete();

            Thread.sleep(5000);

            StepVerifier.create(msg)
                    .expectNext("Eager Publisher")
                    .verifyComplete();
        }

        @Test
        public void whenUsingMonoDefer_thenLazyEvaluation() throws InterruptedException {

            Mono<String> deferMsg = Mono.defer(() -> sampleMsg("Lazy Publisher"));

            log.debug("Intermediate Test Message....");

            StepVerifier.create(deferMsg).expectNext("Lazy Publisher").verifyComplete();

            Thread.sleep(5000);

            StepVerifier.create(deferMsg).expectNext("Lazy Publisher").verifyComplete();

        }

        private Mono<String> sampleMsg(String str){
            log.debug("Call to retrieve Sample Message! -->{}, at {}", str, System.currentTimeMillis());
            return Mono.just(str);
        }
    }
}
