
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class DegreeTest {

  @Test
  public void testValue() {
    assertEquals(Degree.TONIC, Degree.fromValue(1).get());
    assertEquals(Degree.SUPERTONIC, Degree.fromValue(2).get());
    assertEquals(Degree.MEDIANT, Degree.fromValue(3).get());
    assertEquals(Degree.SUBDOMINANT, Degree.fromValue(4).get());
    assertEquals(Degree.DOMINANT, Degree.fromValue(5).get());
    assertEquals(Degree.SUBMEDIANT, Degree.fromValue(6).get());
    assertEquals(Degree.LEADING_TONE, Degree.fromValue(7).get());

    try {
      Degree.fromValue(0).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Degree.fromValue(8).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
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
