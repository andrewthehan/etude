
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValueTests{

	@Test
	public void testDuration(){
		Value value;

		Value[] values = Value.values();
		double currentDuration = 2.0;
		for(int i = 0; i < values.length; ++i){
			value = Value.fromDuration(currentDuration);
			assertEquals(value, values[i]);
			currentDuration /= 2.0;
		}

		try{
			Value.fromDuration(0);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid duration: 0.0 (cannot be zero)");
		}
		try{
			Value.fromDuration(1.5);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid duration: 1.5 (cannot be represented as a value)");
		}
	}

	@Test
	public void testString(){
		Value value;

		value = Value.fromString("2");
		assertEquals(value, Value.DOUBLE_WHOLE);

		value = Value.fromString("2.");
		assertEquals(value, Value.DOUBLE_WHOLE);

		value = Value.fromString("2.0");
		assertEquals(value, Value.DOUBLE_WHOLE);

		value = Value.fromString("0.5");
		assertEquals(value, Value.HALF);

		value = Value.fromString(".5");
		assertEquals(value, Value.HALF);

		value = Value.fromString("2/1");
		assertEquals(value, Value.DOUBLE_WHOLE);

		value = Value.fromString("4/2");
		assertEquals(value, Value.DOUBLE_WHOLE);

		value = Value.fromString("1/2");
		assertEquals(value, Value.HALF);

		value = Value.fromString("2/4");
		assertEquals(value, Value.HALF);

		try{
			Value.fromString("a2");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid value string: a2 (does not match a valid form)");
		}

		try{
			Value.fromString("2a");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid value string: 2a (does not match a valid form)");
		}

		try{
			Value.fromString("2.2.2");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid value string: 2.2.2 (does not match a valid form)");
		}

		try{
			Value.fromString("1.0/2");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid value string: 1.0/2 (does not match a valid form)");
		}

		try{
			Value.fromString("1/2.0");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid value string: 1/2.0 (does not match a valid form)");
		}

		try{
			Value.fromString("0");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid duration: 0.0 (cannot be zero)");
		}
	}
}
