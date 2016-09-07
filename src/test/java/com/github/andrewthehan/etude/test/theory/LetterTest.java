
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Iterator;

public class LetterTest{

  @Test
  public void testOffset(){
    Letter letter;

    letter = Letter.A;
    assertEquals(9, letter.getOffset());
    letter = Letter.B;
    assertEquals(11, letter.getOffset());
    letter = Letter.C;
    assertEquals(0, letter.getOffset());
    letter = Letter.D;
    assertEquals(2, letter.getOffset());
    letter = Letter.E;
    assertEquals(4, letter.getOffset());
    letter = Letter.F;
    assertEquals(5, letter.getOffset());
    letter = Letter.G;
    assertEquals(7, letter.getOffset());
  }

  @Test
  public void testStream(){
    String letters;

    letters = Letter
      .stream()
      .limit(8)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("ABCDEFGA", letters);

    letters = Letter
      .stream(Direction.DESCENDING)
      .limit(8)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("AGFEDCBA", letters);

    letters = Letter
      .stream()
      .limit(5)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("ABCDE", letters);

    letters = Letter
      .stream(Direction.DESCENDING)
      .limit(5)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("AGFED", letters);

    letters = Letter
      .stream()
      .limit(16)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("ABCDEFGABCDEFGAB", letters);

    letters = Letter
      .stream(Direction.DESCENDING)
      .limit(16)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("AGFEDCBAGFEDCBAG", letters);

    letters = Letter
      .stream(Letter.C)
      .limit(10)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("CDEFGABCDE", letters);

    letters = Letter
      .stream(Direction.DESCENDING, Letter.C)
      .limit(10)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("CBAGFEDCBA", letters);

    letters = Letter
      .stream()
      .filter(l -> l == Letter.A)
      .limit(10)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("AAAAAAAAAA", letters);

    letters = Letter
      .stream(Direction.DESCENDING)
      .filter(l -> l == Letter.A)
      .limit(10)
      .map(Letter::toString)
      .collect(Collectors.joining(""));
    assertEquals("AAAAAAAAAA", letters);
  }

  @Test
  public void testIterator(){
    Iterator<Letter> it;

    Letter[] letters = Letter.values();

    it = Letter.iterator();
    for(int i = 0; i < letters.length * 2 ; ++i){
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Letter.A);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Letter.C);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[(i + 2) % letters.length], it.next());
    }

    it = Letter.iterator(Letter.G);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[(i + 6) % letters.length], it.next());
    }

    letters = Letter.getLetters(Direction.DESCENDING);

    it = Letter.iterator(Direction.DESCENDING);
    for(int i = 0; i < letters.length * 2 ; ++i){
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.A);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.C);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[(i + 5) % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.G);
    for(int i = 0; i < letters.length * 2; ++i){
      assertEquals(letters[(i + 1) % letters.length], it.next());
    }
  }

  @Test
  public void testChar(){
    Letter letter;

    letter = Letter.fromChar('A');
    assertEquals(Letter.A, letter);
    assertEquals("A", letter.toString());
    letter = Letter.fromChar('a');
    assertEquals(Letter.A, letter);
    assertEquals("A", letter.toString());

    letter = Letter.fromChar('B');
    assertEquals(Letter.B, letter);
    assertEquals("B", letter.toString());
    letter = Letter.fromChar('b');
    assertEquals(Letter.B, letter);
    assertEquals("B", letter.toString());

    letter = Letter.fromChar('C');
    assertEquals(Letter.C, letter);
    assertEquals("C", letter.toString());
    letter = Letter.fromChar('c');
    assertEquals(Letter.C, letter);
    assertEquals("C", letter.toString());

    letter = Letter.fromChar('D');
    assertEquals(Letter.D, letter);
    assertEquals("D", letter.toString());
    letter = Letter.fromChar('d');
    assertEquals(Letter.D, letter);
    assertEquals("D", letter.toString());

    letter = Letter.fromChar('E');
    assertEquals(Letter.E, letter);
    assertEquals("E", letter.toString());
    letter = Letter.fromChar('e');
    assertEquals(Letter.E, letter);
    assertEquals("E", letter.toString());

    letter = Letter.fromChar('F');
    assertEquals(Letter.F, letter);
    assertEquals("F", letter.toString());
    letter = Letter.fromChar('f');
    assertEquals(Letter.F, letter);
    assertEquals("F", letter.toString());

    letter = Letter.fromChar('G');
    assertEquals(Letter.G, letter);
    assertEquals("G", letter.toString());
    letter = Letter.fromChar('g');
    assertEquals(Letter.G, letter);
    assertEquals("G", letter.toString());

    try{
      Letter.fromChar('H');
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid letter character: H", e.getMessage());
    }

    try{
      Letter.fromChar('h');
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid letter character: h", e.getMessage());
    }

    try{
      Letter.fromChar('1');
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid letter character: 1", e.getMessage());
    }
  }

  @Test
  public void testValueOf(){
    assertEquals(Letter.A, Letter.valueOf("A"));
    assertEquals(Letter.B, Letter.valueOf("B"));
    assertEquals(Letter.C, Letter.valueOf("C"));
    assertEquals(Letter.D, Letter.valueOf("D"));
    assertEquals(Letter.E, Letter.valueOf("E"));
    assertEquals(Letter.F, Letter.valueOf("F"));
    assertEquals(Letter.G, Letter.valueOf("G"));
  }
}
