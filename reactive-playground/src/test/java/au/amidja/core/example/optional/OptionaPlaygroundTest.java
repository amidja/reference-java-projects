package au.amidja.core.example.optional;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Optional reactive playground")
public class OptionaPlaygroundTest {

	@DisplayName("Present Or Else")
    @Nested
    class PresentOrElseExample {
		
		@DisplayName("test present")
        @Test
        void testPresent() {
			Optional<Integer> op = Optional.of(9455); 	  	       			
	        System.out.println("Optional: "+ op); 
	  
	        // apply ifPresentOrElse 
	        op.ifPresentOrElse( 
	            (value) -> { System.out.println( "Value is present, it's: "+ value); }, 
	            () -> { System.out.println( "Value is empty"); }); 
        }
	}
}