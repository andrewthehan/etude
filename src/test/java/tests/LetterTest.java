
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Iterator;

public class LetterTest{

  @Test
  public void testOffset(){
    Letter letter;

    letter = Letter.A;
    assertEquals(letter.getOffset(), 9);
    letter = Letter.B;
    assertEquals(letter.getOffset(), 11);
    letter = Letter.C;
    assertEquals(letter.getOffset(), 0);
    letter = Letter.D;
    assertEquals(letter.getOffset(), 2);
    letter = Letter.E;
    assertEquals(letter.getOffset(), 4);
    letter = Letter.F;
    assertEquals(letter.getOffset(), 5);
    letter = Letter.G;
    assertEquals(letter.getOffset(), 7);
  }

  @Test
  public void testStream(){
    String letters;

    letters = Letter
      .stream()
      .limit(8)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals(letters, "ABCDEFGA");

    letters = Letter
      .stream()
      .limit(5)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals(letters, "ABCDE");

    letters = Letter
      .stream()
      .limit(16)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals(letters, "ABCDEFGABCDEFGAB");

    letters = Letter
      .stream(Letter.C)
      .limit(10)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals(letters, "CDEFGABCDE");
  }

  @Test
  public void testIterator(){
    Iterator<Letter> it;

    Letter[] letters = Letter.values();

    it = Letter.iterator();
    for(int i = 0; i < letters.length * 2 ; ++i){
      assertEquals(it.next(), letters[i % letters.length]);
    }

    it = Letter.iterator(Letter.A);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(it.next(), letters[i % letters.length]);
    }

    it = Letter.iterator(Letter.C);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(it.next(), letters[(i + 2) % letters.length]);
    }

    it = Letter.iterator(Letter.G);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(it.next(), letters[(i + 6) % letters.length]);
    }
  }

  @Test
  public void testChar(){
    Letter letter;

    letter = Letter.fromChar('A');
    assertEquals(letter, Letter.A);
    assertEquals(letter.toString(), "A");
    letter = Letter.fromChar('a');
    assertEquals(letter, Letter.A);
    assertEquals(letter.toString(), "A");

    letter = Letter.fromChar('B');
    assertEquals(letter, Letter.B);
    assertEquals(letter.toString(), "B");
    letter = Letter.fromChar('b');
    assertEquals(letter, Letter.B);
    assertEquals(letter.toString(), "B");

    letter = Letter.fromChar('C');
    assertEquals(letter, Letter.C);
    assertEquals(letter.toString(), "C");
    letter = Letter.fromChar('c');
    assertEquals(letter, Letter.C);
    assertEquals(letter.toString(), "C");

    letter = Letter.fromChar('D');
    assertEquals(letter, Letter.D);
    assertEquals(letter.toString(), "D");
    letter = Letter.fromChar('d');
    assertEquals(letter, Letter.D);
    assertEquals(letter.toString(), "D");

    letter = Letter.fromChar('E');
    assertEquals(letter, Letter.E);
    assertEquals(letter.toString(), "E");
    letter = Letter.fromChar('e');
    assertEquals(letter, Letter.E);
    assertEquals(letter.toString(), "E");

    letter = Letter.fromChar('F');
    assertEquals(letter, Letter.F);
    assertEquals(letter.toString(), "F");
    letter = Letter.fromChar('f');
    assertEquals(letter, Letter.F);
    assertEquals(letter.toString(), "F");

    letter = Letter.fromChar('G');
    assertEquals(letter, Letter.G);
    assertEquals(letter.toString(), "G");
    letter = Letter.fromChar('g');
    assertEquals(letter, Letter.G);
    assertEquals(letter.toString(), "G");

    try{
      Letter.fromChar('H');
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid letter character: H");
    }

    try{
      Letter.fromChar('1');
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid letter character: 1");
    }
  }
}
