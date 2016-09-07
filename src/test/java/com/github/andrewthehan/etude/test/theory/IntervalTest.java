
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntervalTest{

  @Test
  public void testConstructor(){
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

    try{
      new Interval(Interval.Quality.PERFECT, -5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval: P-5 (number must be a positive integer)", e.getMessage());
    }
    try{
      new Interval(Interval.Quality.PERFECT, 2);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval: P2 (number cannot have a perfect quality)", e.getMessage());
    }
    try{
      new Interval(Interval.Quality.MAJOR, 5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval: M5 (number cannot have major or minor quality)", e.getMessage());
    }
    try{
      new Interval(Interval.Quality.MINOR, 5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval: m5 (number cannot have major or minor quality)", e.getMessage());
    }
  }

  @Test
  public void testBetween(){
    Interval interval;

    Pitch a, b;

    a = Pitch.fromString("G4");
    b = Pitch.fromString("A4");
    interval = Interval.between(a, b);
    assertEquals("M2", interval.toString());
    assertEquals("A4(57)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C4");
    interval = Interval.between(a, b);
    assertEquals("P1", interval.toString());
    assertEquals("C4(48)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cb4");
    interval = Interval.between(a, b);
    assertEquals("d1", interval.toString());
    assertEquals("Cb4(47)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cbb4");
    interval = Interval.between(a, b);
    assertEquals("dd1", interval.toString());
    assertEquals("Cbb4(46)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C#4");
    interval = Interval.between(a, b);
    assertEquals("A1", interval.toString());
    assertEquals("C#4(49)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cx4");
    interval = Interval.between(a, b);
    assertEquals("AA1", interval.toString());
    assertEquals("Cx4(50)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("E4");
    interval = Interval.between(a, b);
    assertEquals("M3", interval.toString());
    assertEquals("E4(52)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Eb4");
    interval = Interval.between(a, b);
    assertEquals("m3", interval.toString());
    assertEquals("Eb4(51)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ebb4");
    interval = Interval.between(a, b);
    assertEquals("d3", interval.toString());
    assertEquals("Ebb4(50)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ebbb4");
    interval = Interval.between(a, b);
    assertEquals("dd3", interval.toString());
    assertEquals("Ebbb4(49)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("E#4");
    interval = Interval.between(a, b);
    assertEquals("A3", interval.toString());
    assertEquals("E#4(53)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ex4");
    interval = Interval.between(a, b);
    assertEquals("AA3", interval.toString());
    assertEquals("Ex4(54)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C5");
    interval = Interval.between(a, b);
    assertEquals("P8", interval.toString());
    assertEquals("C5(60)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("G5");
    interval = Interval.between(a, b);
    assertEquals("P12", interval.toString());
    assertEquals("G5(67)", a.step(interval).toString());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("G6");
    interval = Interval.between(a, b);
    assertEquals("P19", interval.toString());
    assertEquals("G6(79)", a.step(interval).toString());

    a = Pitch.fromString("A4");
    b = Pitch.fromString("A5");
    interval = Interval.between(a, b);
    assertEquals("P8", interval.toString());
    assertEquals("A5(69)", a.step(interval).toString());

    a = Pitch.fromString("A4");
    b = Pitch.fromString("C5");
    interval = Interval.between(a, b);
    assertEquals("m3", interval.toString());
    assertEquals("C5(60)", a.step(interval).toString());

    a = Pitch.fromString("A4");
    b = Pitch.fromString("C6");
    interval = Interval.between(a, b);
    assertEquals("m10", interval.toString());
    assertEquals("C6(72)", a.step(interval).toString());

    a = Pitch.fromString("G4");
    b = Pitch.fromString("G5");
    interval = Interval.between(a, b);
    assertEquals("P8", interval.toString());
    assertEquals("G5(67)", a.step(interval).toString());

    a = Pitch.fromString("G4");
    b = Pitch.fromString("C5");
    interval = Interval.between(a, b);
    assertEquals("P4", interval.toString());
    assertEquals("C5(60)", a.step(interval).toString());

    a = Pitch.fromString("G4");
    b = Pitch.fromString("C6");
    interval = Interval.between(a, b);
    assertEquals("P11", interval.toString());
    assertEquals("C6(72)", a.step(interval).toString());

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("Cbbb4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Cannot create interval for pitches: C4(48) -> Cbbb4(45)", e.getMessage());
    }

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("C#x4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Cannot create interval for pitches: C4(48) -> C#x4(51)", e.getMessage());
    }

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("E#x4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Cannot create interval for pitches: C4(48) -> E#x4(55)", e.getMessage());
    }

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("B3");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Cannot create interval with nonpositive number", e.getMessage());
    }
  }

  @Test
  public void testOffset(){
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
  public void testPerfect(){
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
  public void testString(){
    Interval interval;

    interval = Interval.fromString("P1");
    assertEquals("P1", interval.toString());

    interval = Interval.fromString("M3");
    assertEquals("M3", interval.toString());

    interval = Interval.fromString("m3");
    assertEquals("m3", interval.toString());

    interval = Interval.fromString("d3");
    assertEquals("d3", interval.toString());

    interval = Interval.fromString("dd4");
    assertEquals("dd4", interval.toString());

    interval = Interval.fromString("A5");
    assertEquals("A5", interval.toString());

    interval = Interval.fromString("AA6");
    assertEquals("AA6", interval.toString());

    interval = Interval.fromString("P8");
    assertEquals("P8", interval.toString());

    interval = Interval.fromString("M9");
    assertEquals("M9", interval.toString());

    interval = Interval.fromString("M13");
    assertEquals("M13", interval.toString());

    try{
      Interval.fromString(null);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: null", e.getMessage());
    }

    try{
      Interval.fromString(" ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string:   (blank)", e.getMessage());
    }

    try{
      Interval.fromString("B4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid quality string: B", e.getMessage());
    }

    try{
      Interval.fromString("p4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid quality string: p", e.getMessage());
    }

    try{
      Interval.fromString("MM4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid quality string: MM", e.getMessage());
    }

    try{
      Interval.fromString("1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: 1 (missing information)", e.getMessage());
    }

    try{
      Interval.fromString("M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: M (missing information)", e.getMessage());
    }

    try{
      Interval.fromString("3M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid quality string: 3", e.getMessage());
    }

    try{
      Interval.fromString("M3M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: M3M (contains extra information)", e.getMessage());
    }

    try{
      Interval.fromString("3M3");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: 3M3 (contains extra information)", e.getMessage());
    }

    try{
      Interval.fromString("3 M 3");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid interval string: 3 M 3 (contains extra information)", e.getMessage());
    }
  }

  @Test
  public void testValueOf(){
    assertEquals(Interval.Quality.PERFECT, Interval.Quality.valueOf("PERFECT"));
    assertEquals(Interval.Quality.MAJOR, Interval.Quality.valueOf("MAJOR"));
    assertEquals(Interval.Quality.MINOR, Interval.Quality.valueOf("MINOR"));
    assertEquals(Interval.Quality.DIMINISHED, Interval.Quality.valueOf("DIMINISHED"));
    assertEquals(Interval.Quality.DOUBLY_DIMINISHED, Interval.Quality.valueOf("DOUBLY_DIMINISHED"));
    assertEquals(Interval.Quality.AUGMENTED, Interval.Quality.valueOf("AUGMENTED"));
    assertEquals(Interval.Quality.DOUBLY_AUGMENTED, Interval.Quality.valueOf("DOUBLY_AUGMENTED"));
  }
}
