
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class DegreeTest{

  @Test
  public void testValue(){
    assertEquals(Degree.TONIC, Degree.fromValue(1));
    assertEquals(Degree.SUPERTONIC, Degree.fromValue(2));
    assertEquals(Degree.MEDIANT, Degree.fromValue(3));
    assertEquals(Degree.SUBDOMINANT, Degree.fromValue(4));
    assertEquals(Degree.DOMINANT, Degree.fromValue(5));
    assertEquals(Degree.SUBMEDIANT, Degree.fromValue(6));
    assertEquals(Degree.LEADING_TONE, Degree.fromValue(7));

    try{
      Degree.fromValue(0);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value: 0", e.getMessage());
    }
    try{
      Degree.fromValue(8);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid value: 8", e.getMessage());
    }

    assertEquals(1, Degree.TONIC.getValue());
    assertEquals(2, Degree.SUPERTONIC.getValue());
    assertEquals(3, Degree.MEDIANT.getValue());
    assertEquals(4, Degree.SUBDOMINANT.getValue());
    assertEquals(5, Degree.DOMINANT.getValue());
    assertEquals(6, Degree.SUBMEDIANT.getValue());
    assertEquals(7, Degree.LEADING_TONE.getValue());
  }
}
