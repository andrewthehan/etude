
package tests;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntervalTest{

  @Test
  public void testConstructor(){
    Interval interval;

    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals(interval.toString(), "P1");
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals(interval.toString(), "P5");
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals(interval.toString(), "P8");
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals(interval.toString(), "M3");
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals(interval.toString(), "m3");
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals(interval.toString(), "d3");
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals(interval.toString(), "d5");
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals(interval.toString(), "dd3");
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals(interval.toString(), "dd5");
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals(interval.toString(), "A4");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals(interval.toString(), "AA4");

    try{
      new Interval(Interval.Quality.PERFECT, -5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval: P-5 (number must be a positive integer)");
    }
    try{
      new Interval(Interval.Quality.PERFECT, 2);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval: P2 (number cannot have a perfect quality)");
    }
    try{
      new Interval(Interval.Quality.MAJOR, 5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval: M5 (number cannot have major or minor quality)");
    }
    try{
      new Interval(Interval.Quality.MINOR, 5);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval: m5 (number cannot have major or minor quality)");
    }
  }

  @Test
  public void testBetween(){
    Interval interval;

    Pitch a, b;

    a = Pitch.fromString("G4");
    b = Pitch.fromString("A4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "M2");
    assertEquals(a.step(interval).toString(), "An4(57)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "P1");
    assertEquals(a.step(interval).toString(), "Cn4(48)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cb4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "d1");
    assertEquals(a.step(interval).toString(), "Cb4(47)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cbb4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "dd1");
    assertEquals(a.step(interval).toString(), "Cbb4(46)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C#4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "A1");
    assertEquals(a.step(interval).toString(), "C#4(49)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Cx4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "AA1");
    assertEquals(a.step(interval).toString(), "Cx4(50)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("E4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "M3");
    assertEquals(a.step(interval).toString(), "En4(52)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Eb4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "m3");
    assertEquals(a.step(interval).toString(), "Eb4(51)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ebb4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "d3");
    assertEquals(a.step(interval).toString(), "Ebb4(50)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ebbb4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "dd3");
    assertEquals(a.step(interval).toString(), "Ebbb4(49)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("E#4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "A3");
    assertEquals(a.step(interval).toString(), "E#4(53)");

    a = Pitch.fromString("C4");
    b = Pitch.fromString("Ex4");
    interval = Interval.between(a, b);
    assertEquals(interval.toString(), "AA3");
    assertEquals(a.step(interval).toString(), "Ex4(54)");

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("Cbbb4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Cannot create interval for pitches: C4(48) -> Cbbb4(45)");
    }

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("C#x4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Cannot create interval for pitches: C4(48) -> C#x4(51)");
    }

    try{
      a = Pitch.fromString("C4");
      b = Pitch.fromString("E#x4");
      Interval.between(a, b);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Cannot create interval for pitches: C4(48) -> E#x4(55)");
    }
  }

  @Test
  public void testOffset(){
    Interval interval;

    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals(interval.getOffset(), 0);
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals(interval.getOffset(), 7);
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals(interval.getOffset(), 12);
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals(interval.getOffset(), 4);
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals(interval.getOffset(), 3);
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals(interval.getOffset(), 2);
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals(interval.getOffset(), 6);
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals(interval.getOffset(), 1);
    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals(interval.getOffset(), 5);
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals(interval.getOffset(), 6);
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals(interval.getOffset(), 7);

    interval = new Interval(Interval.Quality.MAJOR, 10);
    assertEquals(interval.getOffset(), 16);
    interval = new Interval(Interval.Quality.PERFECT, 12);
    assertEquals(interval.getOffset(), 19);
  }

  @Test
  public void testPerfect(){
    assert(Interval.isPerfect(1));
    assert(!Interval.isPerfect(2));
    assert(!Interval.isPerfect(3));
    assert(Interval.isPerfect(4));
    assert(Interval.isPerfect(5));
    assert(!Interval.isPerfect(6));
    assert(!Interval.isPerfect(7));
    assert(Interval.isPerfect(8));
    assert(!Interval.isPerfect(9));
  }

  @Test
  public void testString(){
    Interval interval;

    interval = Interval.fromString("P1");
    assertEquals(interval.toString(), "P1");

    interval = Interval.fromString("M3");
    assertEquals(interval.toString(), "M3");

    interval = Interval.fromString("m3");
    assertEquals(interval.toString(), "m3");

    interval = Interval.fromString("dd4");
    assertEquals(interval.toString(), "dd4");

    interval = Interval.fromString("AA6");
    assertEquals(interval.toString(), "AA6");

    interval = Interval.fromString("P8");
    assertEquals(interval.toString(), "P8");

    interval = Interval.fromString("M9");
    assertEquals(interval.toString(), "M9");

    interval = Interval.fromString("M13");
    assertEquals(interval.toString(), "M13");

    try{
      Interval.fromString("B4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid quality string: B");
    }

    try{
      Interval.fromString("p4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid quality string: p");
    }

    try{
      Interval.fromString("MM4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid quality string: MM");
    }

    try{
      Interval.fromString("1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval string: 1 (missing information)");
    }

    try{
      Interval.fromString("M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval string: M (missing information)");
    }

    try{
      Interval.fromString("3M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid quality string: 3");
    }

    try{
      Interval.fromString("M3M");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval string: M3M (contains extra information)");
    }

    try{
      Interval.fromString("3M3");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid interval string: 3M3 (contains extra information)");
    }
  }
}
