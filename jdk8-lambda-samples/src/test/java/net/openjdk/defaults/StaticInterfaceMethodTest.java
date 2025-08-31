package net.openjdk.defaults;

import org.junit.Test;

import static org.junit.Assert.*;

public class StaticInterfaceMethodTest {

	//References: https://www.baeldung.com/java-static-default-methods
	/**
	 * In addition to declaring default methods in interfaces, Java 8 also allows us to define and implement static methods 
	 * in interfaces. 
	 * 
	 * Since static methods don’t belong to a particular object, 
	 * they’re not part of the API of the classes implementing the interface; therefore, they have to be called by using the interface name preceding the method name.
	 */
	
    @Test
    public void test() {
        assertEquals("toDublin", TicketOffice.defaultOffice().qDublin());
        assertEquals("toLimerick", TicketOffice.defaultOffice().qLimerick());
    }

    public interface TicketOffice {
        String qDublin();
        String qLimerick();
        

        static TicketOffice defaultOffice() {        	
            //return () -> "toDublin";
        	        	
            return new TicketOffice() {
                @Override
                public String qDublin() {     
                    return "toDublin";        
                }
                
                @Override
                public String qLimerick() {     
                    return "toLimerick";        
                }
            };
            
        }
    }

}
