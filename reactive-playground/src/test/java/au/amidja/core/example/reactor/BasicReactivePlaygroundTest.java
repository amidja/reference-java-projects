package au.amidja.core.example.reactor;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Basic reactive playground")
public class BasicReactivePlaygroundTest {

    private static final Logger log = LoggerFactory.getLogger(BasicReactivePlaygroundTest.class);

    @DisplayName("imperative vs reactive")
    @Nested
    class ImperativeVsReactive {

        @DisplayName("imperative code")
        @Test
        void testImperative() {
            String msg = "World";
            String upperCaseMsg = msg.toUpperCase();
            String greeting = "Hello " + upperCaseMsg + "!";
            assertThat(greeting).isEqualTo("Hello WORLD!");
        }

        @DisplayName("functional code")
        @Test
        void testFunctional() {
            String greeting =
                    Stream.of("World")
                            .map(String::toUpperCase)
                            .map(um -> "Hello " + um + "!")
                            .collect(Collectors.joining());
            assertThat(greeting).isEqualTo("Hello WORLD!");
        }

        @DisplayName("reactive code")
        @Test
        void testReactive() {
            Mono<String> greeting =
                    Mono.just("World").map(String::toUpperCase).map(um -> "Hello " + um + "!");
            StepVerifier.create(greeting).expectNext("Hello WORLD!").verifyComplete();
        }
    }

}