
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class KeyTest {

  @Test
  public void testConstructor() {
    Key key;

    key = new Key(Letter.C);
    assertEquals("C", key.toString());
    key = new Key(Letter.G, (Accidental) null);
    assertEquals("G", key.toString());
    key = new Key(Letter.D, Accidental.NATURAL);
    assertEquals("Dn", key.toString());
    key = new Key(Letter.A, Accidental.SHARP);
    assertEquals("A#", key.toString());
    key = new Key(Letter.E, Accidental.DOUBLE_SHARP);
    assertEquals("Ex", key.toString());
    key = new Key(Letter.B, Accidental.TRIPLE_SHARP);
    assertEquals("B#x", key.toString());
    key = new Key(Letter.F, Accidental.FLAT);
    assertEquals("Fb", key.toString());
    key = new Key(Letter.C, Accidental.DOUBLE_FLAT);
    assertEquals("Cbb", key.toString());
    key = new Key(Letter.G, Accidental.TRIPLE_FLAT);
    assertEquals("Gbbb", key.toString());
  }

  @Test
  public void testKeySignature() {
    KeySignature ks;
    Iterator<Letter> letters;

    ks = KeySignature.fromKey(Key.fromString("C").get(), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(Letter.C);
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");

    ks = KeySignature.fromKey(Key.fromString("E").get(), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(Letter.E);
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D#");

    ks = KeySignature.fromKey(Key.fromString("Gb").get(), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(Letter.G);
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Gb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Ab");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Bb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Cb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Db");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Eb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");

    ks = KeySignature.fromKey(Key.fromString("A").get(), KeySignature.Quality.MINOR);
    letters = Letter.iterator(Letter.A);
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G");
  }

  @Test
  public void testManipulations() {
    Key key;

    key = new Key(Letter.C);

    key = key.removeAccidental();
    assertEquals("C", key.toString());
    assertTrue(!key.hasAccidental());
    key = key.natural();
    assertEquals("Cn", key.toString());
    assertTrue(key.isNatural());
    key = key.sharp();
    assertEquals("C#", key.toString());
    assertTrue(key.isSharp());
    key = key.doubleSharp();
    assertEquals("Cx", key.toString());
    assertTrue(key.isDoubleSharp());
    key = key.tripleSharp();
    assertEquals("C#x", key.toString());
    assertTrue(key.isTripleSharp());
    key = key.flat();
    assertEquals("Cb", key.toString());
    assertTrue(key.isFlat());
    key = key.doubleFlat();
    assertEquals("Cbb", key.toString());
    assertTrue(key.isDoubleFlat());
    key = key.tripleFlat();
    assertEquals("Cbbb", key.toString());
    assertTrue(key.isTripleFlat());
  }

  @Test
  public void testEnharmonic() {
    Key a, b;

    String[] keysA = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    String[] keysB = { "Dbb", "Db", "Ebb", "Eb", "Fb", "Gbb", "Gb", "Abb", "Ab", "Bbb", "Bb", "Cb" };
    for (int i = 0; i < keysA.length; ++i) {
      a = Key.fromString(keysA[i]).get();
      b = Key.fromString(keysB[i]).get();
      assertTrue(Key.isEnharmonic(a, b));
    }
  }

  @Test
  public void testOffset() {
    Key key;

    String[] keysSharp = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    for (int i = 0; i < keysSharp.length; ++i) {
      key = Key.fromOffset(i, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.SHARP)).get();
      assertEquals(i, key.getOffset());
      assertEquals(keysSharp[i], key.toString());
    }
    String[] keysFlat = { "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B" };
    for (int i = 0; i < keysFlat.length; ++i) {
      key = Key.fromOffset(i, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT)).get();
      assertEquals(i, key.getOffset());
      assertEquals(keysFlat[i], key.toString());
    }

    try {
      Key.fromOffset(0, Policy.prioritize());
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testString() {
    Key key;

    String[] keysUppercase = { "C", "D", "E#", "Fx", "G#x", "Ab", "Bbb", "Cbbb" };
    for (int i = 0; i < keysUppercase.length; ++i) {
      key = Key.fromString(keysUppercase[i]).get();
      assertEquals(keysUppercase[i], key.toString());
    }

    String[] keysLowercase = { "c", "d", "e#", "fx", "g#x", "ab", "bbb", "cbbb" };
    for (int i = 0; i < keysLowercase.length; ++i) {
      key = Key.fromString(keysLowercase[i]).get();
      assertEquals(keysUppercase[i], key.toString());
    }

    try {
      Key.fromString(null).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Key.fromString("").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Key.fromString("  ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testHashCodeAndEquals() {
    Key a, b;

    a = Key.fromString("C").get();
    b = Key.fromString("C").get();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    b = Key.fromString("C#").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("C#").get();
    b = Key.fromString("Db").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    b = Key.fromString("Dbb").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    b = a.removeAccidental();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("Cx").get();
    b = a.doubleSharp();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    b = a.flat();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    b = Key.fromString("B#").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("Cb").get();
    b = Key.fromString("B").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Key.fromString("Cn").get();
    b = Key.fromString("B#").get().getEnharmonicEquivalent(Letter.C).get();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C").get();
    assertTrue(a.equals(a));
  }
}
