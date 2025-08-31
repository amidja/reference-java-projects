package au.amidja.core.example.streams;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCollectorTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleCollectorTest.class);

    /**
     * Reference: https://medium.com/@AlexanderObregon/javas-collectors-reducing-method-explained-820cd19a2412
     */
    @Test
    void simpleReducingExample () {
    	
        Stream<Integer> integerStream = Stream.of(5, 10, 20, 50);
 
        Integer intValueFromStream = integerStream
                .collect(Collectors.reducing((integer, integer2) -> integer2 - integer))
                .orElse(-1);
 
 
        log.debug("intValueFromStream: {}", intValueFromStream);
        assertTrue(intValueFromStream == 35);

        Stream<Boolean> booleanStream = Stream.of(true, true, true, true);

        BinaryOperator<Boolean> op = (val1, val2) -> (val1 && val2) == true;

        Boolean booleanValueFromStream = booleanStream.collect(Collectors.reducing(op)).orElse(false);
        
        log.debug("booleanValueFromStream: {}", booleanValueFromStream);
        assertTrue(booleanValueFromStream == true);
    }
}