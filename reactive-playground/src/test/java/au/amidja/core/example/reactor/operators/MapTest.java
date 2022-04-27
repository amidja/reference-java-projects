package au.amidja.core.example.reactor.operators;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MapTest {

	@Test
    public void mapTest() {
        Flux.range(1, 5)
                .map(v -> transform(v))
                .subscribe(y -> log.info(y));

    }

    private String transform(Integer i){
        return  String.format("%03d", i);
    }
}
