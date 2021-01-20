
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class IntervalTest {

  @Test
  public void testConstructor() {
    Interval interval;

    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals("P1", interval.toString());
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals("P5", interval.toString());
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals("P8", interval.toString());
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals("M3", interval.toString());
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals("m3", interval.toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals("d3", interval.toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals("d5", interval.toString());
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals("dd3", interval.toString());
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals("dd5", interval.toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals("A4", interval.toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals("AA4", interval.toString());

    try {
      new Interval(Interval.Quality.PERFECT, -5);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      new Interval(Interval.Quality.PERFECT, 2);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      new Interval(Interval.Quality.MAJOR, 5);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      new Interval(Interval.Quality.MINOR, 5);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testEnharmonic() {
    Interval a, b;

    a = new Interval(Interval.Quality.PERFECT, 5);
    b = new Interval(Interval.Quality.DIMINISHED, 6);
    assertTrue(Interval.isEnharmonic(a, b));
  }

  @Test
  public void testBetween() {
    Interval interval;

    Pitch a, b;

    a = Pitch.fromString("G4").get();
    b = Pitch.fromString("A4").get();
    interval = Interval.between(a, b).get();
    assertEquals("M2", interval.toString());
    assertEquals("A4(57)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C4").get();
    interval = Interval.between(a, b).get();
    assertEquals("P1", interval.toString());
    assertEquals("C4(48)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Cb4").get();
    interval = Interval.between(a, b).get();
    assertEquals("d1", interval.toString());
    assertEquals("Cb4(47)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Cbb4").get();
    interval = Interval.between(a, b).get();
    assertEquals("dd1", interval.toString());
    assertEquals("Cbb4(46)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C#4").get();
    interval = Interval.between(a, b).get();
    assertEquals("A1", interval.toString());
    assertEquals("C#4(49)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Cx4").get();
    interval = Interval.between(a, b).get();
    assertEquals("AA1", interval.toString());
    assertEquals("Cx4(50)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("E4").get();
    interval = Interval.between(a, b).get();
    assertEquals("M3", interval.toString());
    assertEquals("E4(52)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Eb4").get();
    interval = Interval.between(a, b).get();
    assertEquals("m3", interval.toString());
    assertEquals("Eb4(51)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Ebb4").get();
    interval = Interval.between(a, b).get();
    assertEquals("d3", interval.toString());
    assertEquals("Ebb4(50)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Ebbb4").get();
    interval = Interval.between(a, b).get();
    assertEquals("dd3", interval.toString());
    assertEquals("Ebbb4(49)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("E#4").get();
    interval = Interval.between(a, b).get();
    assertEquals("A3", interval.toString());
    assertEquals("E#4(53)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("Ex4").get();
    interval = Interval.between(a, b).get();
    assertEquals("AA3", interval.toString());
    assertEquals("Ex4(54)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C5").get();
    interval = Interval.between(a, b).get();
    assertEquals("P8", interval.toString());
    assertEquals("C5(60)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("G5").get();
    interval = Interval.between(a, b).get();
    assertEquals("P12", interval.toString());
    assertEquals("G5(67)", a.step(interval).get().toString());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("G6").get();
    interval = Interval.between(a, b).get();
    assertEquals("P19", interval.toString());
    assertEquals("G6(79)", a.step(interval).get().toString());

    a = Pitch.fromString("A4").get();
    b = Pitch.fromString("A5").get();
    interval = Interval.between(a, b).get();
    assertEquals("P8", interval.toString());
    assertEquals("A5(69)", a.step(interval).get().toString());

    a = Pitch.fromString("A4").get();
    b = Pitch.fromString("C5").get();
    interval = Interval.between(a, b).get();
    assertEquals("m3", interval.toString());
    assertEquals("C5(60)", a.step(interval).get().toString());

    a = Pitch.fromString("A4").get();
    b = Pitch.fromString("C6").get();
    interval = Interval.between(a, b).get();
    assertEquals("m10", interval.toString());
    assertEquals("C6(72)", a.step(interval).get().toString());

    a = Pitch.fromString("G4").get();
    b = Pitch.fromString("G5").get();
    interval = Interval.between(a, b).get();
    assertEquals("P8", interval.toString());
    assertEquals("G5(67)", a.step(interval).get().toString());

    a = Pitch.fromString("G4").get();
    b = Pitch.fromString("C5").get();
    interval = Interval.between(a, b).get();
    assertEquals("P4", interval.toString());
    assertEquals("C5(60)", a.step(interval).get().toString());

    a = Pitch.fromString("G4").get();
    b = Pitch.fromString("C6").get();
    interval = Interval.between(a, b).get();
    assertEquals("P11", interval.toString());
    assertEquals("C6(72)", a.step(interval).get().toString());

    try {
      a = Pitch.fromString("C4").get();
      b = Pitch.fromString("Cbbb4").get();
      Interval.between(a, b).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      a = Pitch.fromString("C4").get();
      b = Pitch.fromString("C#x4").get();
      Interval.between(a, b).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      a = Pitch.fromString("C4").get();
      b = Pitch.fromString("E#x4").get();
      Interval.between(a, b).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      a = Pitch.fromString("C4").get();
      b = Pitch.fromString("B3").get();
      Interval.between(a, b).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testInverse() {
    Interval interval;
    Interval inverse;

    interval = new Interval(Interval.Quality.PERFECT, 1);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.PERFECT, inverse.getQuality());
    assertEquals(8, inverse.getNumber());

    interval = new Interval(Interval.Quality.PERFECT, 8);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.PERFECT, inverse.getQuality());
    assertEquals(1, inverse.getNumber());

    interval = new Interval(Interval.Quality.MAJOR, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.MINOR, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.MINOR, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.MAJOR, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DIMINISHED, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.AUGMENTED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.AUGMENTED, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DIMINISHED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 2);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.PERFECT, 5);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.PERFECT, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.AUGMENTED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.AUGMENTED, 5);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DIMINISHED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 5);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    // intervals past the range [1, 8]

    interval = new Interval(Interval.Quality.PERFECT, 15);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.PERFECT, inverse.getQuality());
    assertEquals(1, inverse.getNumber());

    interval = new Interval(Interval.Quality.MAJOR, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.MINOR, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.MINOR, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.MAJOR, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DIMINISHED, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.AUGMENTED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.AUGMENTED, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DIMINISHED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 9);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, inverse.getQuality());
    assertEquals(7, inverse.getNumber());

    interval = new Interval(Interval.Quality.PERFECT, 12);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.PERFECT, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DIMINISHED, 12);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.AUGMENTED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.AUGMENTED, 12);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DIMINISHED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 12);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());

    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 12);
    inverse = interval.inverse();
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, inverse.getQuality());
    assertEquals(4, inverse.getNumber());
  }

  @Test
  public void testOffset() {
    Interval interval;

    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals(0, interval.getOffset());
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals(7, interval.getOffset());
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals(12, interval.getOffset());
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals(4, interval.getOffset());
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals(3, interval.getOffset());
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals(2, interval.getOffset());
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals(6, interval.getOffset());
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals(1, interval.getOffset());
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals(5, interval.getOffset());
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals(6, interval.getOffset());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals(7, interval.getOffset());

    interval = new Interval(Interval.Quality.MAJOR, 10);
    assertEquals(16, interval.getOffset());
    interval = new Interval(Interval.Quality.PERFECT, 12);
    assertEquals(19, interval.getOffset());
  }

  @Test
  public void testPerfect() {
    assertTrue(Interval.isPerfect(1));
    assertFalse(Interval.isPerfect(2));
    assertFalse(Interval.isPerfect(3));
    assertTrue(Interval.isPerfect(4));
    assertTrue(Interval.isPerfect(5));
    assertFalse(Interval.isPerfect(6));
    assertFalse(Interval.isPerfect(7));
    assertTrue(Interval.isPerfect(8));
    assertFalse(Interval.isPerfect(9));
  }

  @Test
  public void testString() {
    Interval interval;

    interval = Interval.fromString("P1").get();
    assertEquals("P1", interval.toString());

    interval = Interval.fromString("M3").get();
    assertEquals("M3", interval.toString());

    interval = Interval.fromString("m3").get();
    assertEquals("m3", interval.toString());

    interval = Interval.fromString("d3").get();
    assertEquals("d3", interval.toString());

    interval = Interval.fromString("dd4").get();
    assertEquals("dd4", interval.toString());

    interval = Interval.fromString("A5").get();
    assertEquals("A5", interval.toString());

    interval = Interval.fromString("AA6").get();
    assertEquals("AA6", interval.toString());

    interval = Interval.fromString("P8").get();
    assertEquals("P8", interval.toString());

    interval = Interval.fromString("M9").get();
    assertEquals("M9", interval.toString());

    interval = Interval.fromString("M13").get();
    assertEquals("M13", interval.toString());

    try {
      Interval.fromString(null).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString(" ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("B4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("p4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("MM4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("1").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("M").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("3M").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("M3M").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("3M3").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Interval.fromString("3 M 3").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testValueOf() {
    assertEquals(Interval.Quality.PERFECT, Interval.Quality.valueOf("PERFECT"));
    assertEquals(Interval.Quality.MAJOR, Interval.Quality.valueOf("MAJOR"));
    assertEquals(Interval.Quality.MINOR, Interval.Quality.valueOf("MINOR"));
    assertEquals(Interval.Quality.DIMINISHED, Interval.Quality.valueOf("DIMINISHED"));
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, Interval.Quality.valueOf("DOUBLY_DIMINISHED"));
    assertEquals(Interval.Quality.AUGMENTED, Interval.Quality.valueOf("AUGMENTED"));
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, Interval.Quality.valueOf("DOUBLY_AUGMENTED"));
  }
}
