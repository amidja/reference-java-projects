package net.openjdk.lambda.basics;

import java.util.function.Consumer;

import org.junit.Test;

//http://www.java2s.com/Tutorials/Java/java.util.function/Consumer/index.htm

public class ConsumerTest {

	@Test
	public void testSimpleConsumer() {
		Consumer<String> c = (x) -> System.out.println(x.toLowerCase());
	    c.accept("Java2s.com");
	}

}
