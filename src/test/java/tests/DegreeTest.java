
package tests;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class DegreeTest{

  @Test
  public void testValue(){
    assertEquals(Degree.fromValue(1), Degree.TONIC);
    assertEquals(Degree.fromValue(2), Degree.SUPERTONIC);
    assertEquals(Degree.fromValue(3), Degree.MEDIANT);
    assertEquals(Degree.fromValue(4), Degree.SUBDOMINANT);
    assertEquals(Degree.fromValue(5), Degree.DOMINANT);
    assertEquals(Degree.fromValue(6), Degree.SUBMEDIANT);
    assertEquals(Degree.fromValue(7), Degree.LEADING_TONE);

    try{
      Degree.fromValue(0);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid value: 0");
    }
    try{
      Degree.fromValue(8);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid value: 8");
    }

    assertEquals(Degree.TONIC.getValue(), 1);
    assertEquals(Degree.SUPERTONIC.getValue(), 2);
    assertEquals(Degree.MEDIANT.getValue(), 3);
    assertEquals(Degree.SUBDOMINANT.getValue(), 4);
    assertEquals(Degree.DOMINANT.getValue(), 5);
    assertEquals(Degree.SUBMEDIANT.getValue(), 6);
    assertEquals(Degree.LEADING_TONE.getValue(), 7);
  }
}
