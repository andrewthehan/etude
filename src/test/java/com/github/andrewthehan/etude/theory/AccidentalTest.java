
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class AccidentalTest {

  @Test
  public void testOffset() {
    Accidental accidental;

    accidental = Accidental.fromOffset(0).get();
    assertEquals(0, accidental.getOffset());
    assertEquals(Accidental.NATURAL, accidental);
    accidental = Accidental.fromOffset(1).get();
    assertEquals(1, accidental.getOffset());
    assertEquals(Accidental.SHARP, accidental);
    accidental = Accidental.fromOffset(2).get();
    assertEquals(2, accidental.getOffset());
    assertEquals(Accidental.DOUBLE_SHARP, accidental);
    accidental = Accidental.fromOffset(3).get();
    assertEquals(3, accidental.getOffset());
    assertEquals(Accidental.TRIPLE_SHARP, accidental);
    accidental = Accidental.fromOffset(-1).get();
    assertEquals(-1, accidental.getOffset());
    assertEquals(Accidental.FLAT, accidental);
    accidental = Accidental.fromOffset(-2).get();
    assertEquals(-2, accidental.getOffset());
    assertEquals(Accidental.DOUBLE_FLAT, accidental);
    accidental = Accidental.fromOffset(-3).get();
    assertEquals(-3, accidental.getOffset());
    assertEquals(Accidental.TRIPLE_FLAT, accidental);

    try {
      Accidental.fromOffset(4).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testString() {
    Accidental accidental;

    accidental = Accidental.fromString("n").get();
    assertEquals(Accidental.NATURAL, accidental);
    assertEquals("n", accidental.toString());

    accidental = Accidental.fromString("#").get();
    assertEquals(Accidental.SHARP, accidental);
    assertEquals("#", accidental.toString());

    accidental = Accidental.fromString("x").get();
    assertEquals(Accidental.DOUBLE_SHARP, accidental);
    assertEquals("x", accidental.toString());

    accidental = Accidental.fromString("#x").get();
    assertEquals(Accidental.TRIPLE_SHARP, accidental);
    assertEquals("#x", accidental.toString());

    accidental = Accidental.fromString("b").get();
    assertEquals(Accidental.FLAT, accidental);
    assertEquals("b", accidental.toString());

    accidental = Accidental.fromString("bb").get();
    assertEquals(Accidental.DOUBLE_FLAT, accidental);
    assertEquals("bb", accidental.toString());

    accidental = Accidental.fromString("bbb").get();
    assertEquals(Accidental.TRIPLE_FLAT, accidental);
    assertEquals("bbb", accidental.toString());

    try {
      Accidental.fromString("A").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testValueOf() {
    assertEquals(Accidental.NATURAL, Accidental.valueOf("NATURAL"));
    assertEquals(Accidental.SHARP, Accidental.valueOf("SHARP"));
    assertEquals(Accidental.DOUBLE_SHARP, Accidental.valueOf("DOUBLE_SHARP"));
    assertEquals(Accidental.TRIPLE_SHARP, Accidental.valueOf("TRIPLE_SHARP"));
    assertEquals(Accidental.FLAT, Accidental.valueOf("FLAT"));
    assertEquals(Accidental.DOUBLE_FLAT, Accidental.valueOf("DOUBLE_FLAT"));
    assertEquals(Accidental.TRIPLE_FLAT, Accidental.valueOf("TRIPLE_FLAT"));
  }
}
