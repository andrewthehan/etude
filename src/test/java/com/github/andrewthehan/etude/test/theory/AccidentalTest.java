
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class AccidentalTest{

  @Test
  public void testOffset(){
    Accidental accidental;

    accidental = Accidental.NONE;
    assertEquals(0, accidental.getOffset());

    accidental = Accidental.fromOffset(0);
    assertEquals(0, accidental.getOffset());
    assertEquals(Accidental.NONE, accidental);
    accidental = Accidental.fromOffset(1);
    assertEquals(1, accidental.getOffset());
    assertEquals(Accidental.SHARP, accidental);
    accidental = Accidental.fromOffset(2);
    assertEquals(2, accidental.getOffset());
    assertEquals(Accidental.DOUBLE_SHARP, accidental);
    accidental = Accidental.fromOffset(3);
    assertEquals(3, accidental.getOffset());
    assertEquals(Accidental.TRIPLE_SHARP, accidental);
    accidental = Accidental.fromOffset(-1);
    assertEquals(-1, accidental.getOffset());
    assertEquals(Accidental.FLAT, accidental);
    accidental = Accidental.fromOffset(-2);
    assertEquals(-2, accidental.getOffset());
    assertEquals(Accidental.DOUBLE_FLAT, accidental);
    accidental = Accidental.fromOffset(-3);
    assertEquals(-3, accidental.getOffset());
    assertEquals(Accidental.TRIPLE_FLAT, accidental);

    try{
      Accidental.fromOffset(4);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid accidental offset: 4", e.getMessage());
    }
  }

  @Test
  public void testString(){
    Accidental accidental;

    accidental = Accidental.fromString("");
    assertEquals(Accidental.NONE, accidental);
    assertEquals("", accidental.toString());

    accidental = Accidental.fromString("n");
    assertEquals(Accidental.NATURAL, accidental);
    assertEquals("n", accidental.toString());

    accidental = Accidental.fromString("#");
    assertEquals(Accidental.SHARP, accidental);
    assertEquals("#", accidental.toString());

    accidental = Accidental.fromString("x");
    assertEquals(Accidental.DOUBLE_SHARP, accidental);
    assertEquals("x", accidental.toString());

    accidental = Accidental.fromString("#x");
    assertEquals(Accidental.TRIPLE_SHARP, accidental);
    assertEquals("#x", accidental.toString());

    accidental = Accidental.fromString("b");
    assertEquals(Accidental.FLAT, accidental);
    assertEquals("b", accidental.toString());

    accidental = Accidental.fromString("bb");
    assertEquals(Accidental.DOUBLE_FLAT, accidental);
    assertEquals("bb", accidental.toString());

    accidental = Accidental.fromString("bbb");
    assertEquals(Accidental.TRIPLE_FLAT, accidental);
    assertEquals("bbb", accidental.toString());

    try{
      Accidental.fromString("A");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid accidental string: A", e.getMessage());
    }
  }

  @Test
  public void testValueOf(){
    assertEquals(Accidental.NONE, Accidental.valueOf("NONE"));
    assertEquals(Accidental.NATURAL, Accidental.valueOf("NATURAL"));
    assertEquals(Accidental.SHARP, Accidental.valueOf("SHARP"));
    assertEquals(Accidental.DOUBLE_SHARP, Accidental.valueOf("DOUBLE_SHARP"));
    assertEquals(Accidental.TRIPLE_SHARP, Accidental.valueOf("TRIPLE_SHARP"));
    assertEquals(Accidental.FLAT, Accidental.valueOf("FLAT"));
    assertEquals(Accidental.DOUBLE_FLAT, Accidental.valueOf("DOUBLE_FLAT"));
    assertEquals(Accidental.TRIPLE_FLAT, Accidental.valueOf("TRIPLE_FLAT"));
  }
}
