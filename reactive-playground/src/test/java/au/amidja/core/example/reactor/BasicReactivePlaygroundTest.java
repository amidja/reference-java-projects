package au.amidja.core.example.reactor;

import java.util.Optional;
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
    class ImperativeVsReactiveExample {

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

    @DisplayName("create Mono")
    @Nested
    class CreateMonoExample {

        @DisplayName("with empty()")
        @Test
        void testCreateMonoEmpty() {
            StepVerifier.create(Mono.empty()).expectNextCount(0).verifyComplete();
        }

        @DisplayName("with just()")
        @Test
        void testCreateMonoJust() {
            StepVerifier.create(Mono.just("Hello")).expectNext("Hello").verifyComplete();
        }

        @DisplayName("with justOrEmpty()")
        @Test
        void testCreateMonoJustOrEmpty() {
            StepVerifier.create(Mono.justOrEmpty(Optional.of("Hello")))
                    .expectNext("Hello")
                    .verifyComplete();
        }

        @DisplayName("with defer()")
        @Test
        void testCreateMonoDefer() {
            StepVerifier.create(Mono.defer(() -> Mono.just("Hello")))
                    .expectNext("Hello")
                    .verifyComplete();
        }

        @DisplayName("with create()")
        @Test
        void testCreateMonoCreate() {
            StepVerifier.create(Mono.create(sink -> sink.success("Hello")))
                    .expectNext("Hello")
                    .verifyComplete();
        }

        @DisplayName("with error()")
        @Test
        void testCreateMonoError() {
            StepVerifier.create(Mono.error(new IllegalArgumentException("error")))
                    .expectError(IllegalArgumentException.class)
                    .verify();
        }
    }
}