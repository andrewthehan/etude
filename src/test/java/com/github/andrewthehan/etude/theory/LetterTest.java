
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.stream.Collectors;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class LetterTest {

  @Test
  public void testOffset() {
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
  public void testStream() {
    String letters;

    letters = Letter.stream().limit(8).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("ABCDEFGA", letters);

    letters = Letter.stream(Direction.DESCENDING).limit(8).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("AGFEDCBA", letters);

    letters = Letter.stream().limit(5).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("ABCDE", letters);

    letters = Letter.stream(Direction.DESCENDING).limit(5).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("AGFED", letters);

    letters = Letter.stream().limit(16).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("ABCDEFGABCDEFGAB", letters);

    letters = Letter.stream(Direction.DESCENDING).limit(16).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("AGFEDCBAGFEDCBAG", letters);

    letters = Letter.stream(Letter.C).limit(10).map(Letter::toString).collect(Collectors.joining(""));
    assertEquals("CDEFGABCDE", letters);

    letters = Letter.stream(Direction.DESCENDING, Letter.C).limit(10).map(Letter::toString)
        .collect(Collectors.joining(""));
    assertEquals("CBAGFEDCBA", letters);

    letters = Letter.stream().filter(l -> l == Letter.A).limit(10).map(Letter::toString)
        .collect(Collectors.joining(""));
    assertEquals("AAAAAAAAAA", letters);

    letters = Letter.stream(Direction.DESCENDING).filter(l -> l == Letter.A).limit(10).map(Letter::toString)
        .collect(Collectors.joining(""));
    assertEquals("AAAAAAAAAA", letters);
  }

  @Test
  public void testIterator() {
    Iterator<Letter> it;

    Letter[] letters = Letter.values();

    it = Letter.iterator();
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Letter.A);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Letter.C);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[(i + 2) % letters.length], it.next());
    }

    it = Letter.iterator(Letter.G);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[(i + 6) % letters.length], it.next());
    }

    letters = Letter.getLetters(Direction.DESCENDING);

    it = Letter.iterator(Direction.DESCENDING);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.A);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[i % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.C);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[(i + 5) % letters.length], it.next());
    }

    it = Letter.iterator(Direction.DESCENDING, Letter.G);
    for (int i = 0; i < letters.length * 2; ++i) {
      assertEquals(letters[(i + 1) % letters.length], it.next());
    }
  }

  @Test
  public void testChar() {
    Letter letter;

    letter = Letter.fromChar('A').get();
    assertEquals(Letter.A, letter);
    assertEquals("A", letter.toString());
    letter = Letter.fromChar('a').get();
    assertEquals(Letter.A, letter);
    assertEquals("A", letter.toString());

    letter = Letter.fromChar('B').get();
    assertEquals(Letter.B, letter);
    assertEquals("B", letter.toString());
    letter = Letter.fromChar('b').get();
    assertEquals(Letter.B, letter);
    assertEquals("B", letter.toString());

    letter = Letter.fromChar('C').get();
    assertEquals(Letter.C, letter);
    assertEquals("C", letter.toString());
    letter = Letter.fromChar('c').get();
    assertEquals(Letter.C, letter);
    assertEquals("C", letter.toString());

    letter = Letter.fromChar('D').get();
    assertEquals(Letter.D, letter);
    assertEquals("D", letter.toString());
    letter = Letter.fromChar('d').get();
    assertEquals(Letter.D, letter);
    assertEquals("D", letter.toString());

    letter = Letter.fromChar('E').get();
    assertEquals(Letter.E, letter);
    assertEquals("E", letter.toString());
    letter = Letter.fromChar('e').get();
    assertEquals(Letter.E, letter);
    assertEquals("E", letter.toString());

    letter = Letter.fromChar('F').get();
    assertEquals(Letter.F, letter);
    assertEquals("F", letter.toString());
    letter = Letter.fromChar('f').get();
    assertEquals(Letter.F, letter);
    assertEquals("F", letter.toString());

    letter = Letter.fromChar('G').get();
    assertEquals(Letter.G, letter);
    assertEquals("G", letter.toString());
    letter = Letter.fromChar('g').get();
    assertEquals(Letter.G, letter);
    assertEquals("G", letter.toString());

    try {
      Letter.fromChar('H').get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Letter.fromChar('h').get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Letter.fromChar('1').get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testValueOf() {
    assertEquals(Letter.A, Letter.valueOf("A"));
    assertEquals(Letter.B, Letter.valueOf("B"));
    assertEquals(Letter.C, Letter.valueOf("C"));
    assertEquals(Letter.D, Letter.valueOf("D"));
    assertEquals(Letter.E, Letter.valueOf("E"));
    assertEquals(Letter.F, Letter.valueOf("F"));
    assertEquals(Letter.G, Letter.valueOf("G"));
  }
}
