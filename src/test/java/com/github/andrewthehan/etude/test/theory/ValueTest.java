
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValueTest{

  @Test
  public void testDuration(){
    Value value;

    Value[] values = Value.values();
    double currentDuration = 2.0;
    for(int i = 0; i < values.length; ++i){
      value = Value.fromDuration(currentDuration);
      assertEquals(values[i], value);
      currentDuration /= 2.0;
    }

    try{
      Value.fromDuration(0);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid duration: 0.0 (cannot be zero)", e.getMessage());
    }
    try{
      Value.fromDuration(1.5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid duration: 1.5 (cannot be represented as a value)", e.getMessage());
    }
  }

  @Test
  public void testString(){
    Value value;

    value = Value.fromString("2");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("2.");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("2.0");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("DOUBLE_WHOLE");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("0.5");
    assertEquals(Value.HALF, value);

    value = Value.fromString(".5");
    assertEquals(Value.HALF, value);

    value = Value.fromString("HALF");
    assertEquals(Value.HALF, value);

    value = Value.fromString("2/1");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("4/2");
    assertEquals(Value.DOUBLE_WHOLE, value);

    value = Value.fromString("1/2");
    assertEquals(Value.HALF, value);

    value = Value.fromString("2/4");
    assertEquals(Value.HALF, value);

    try{
      Value.fromString("a2");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: a2", e.getMessage());
    }

    try{
      Value.fromString("2a");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: 2a", e.getMessage());
    }

    try{
      Value.fromString("2.2.2");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: 2.2.2", e.getMessage());
    }

    try{
      Value.fromString("1.0/2");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: 1.0/2", e.getMessage());
    }

    try{
      Value.fromString("1/2.0");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: 1/2.0", e.getMessage());
    }

    try{
      Value.fromString("0");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid duration: 0.0 (cannot be zero)", e.getMessage());
    }

    try{
      Value.fromString("HAL");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value string: HAL", e.getMessage());
    }
  }
}
