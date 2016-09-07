
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;

public class KeyTest{

  @Test
  public void testConstructor(){
    Key key;

    key = new Key(Letter.C);
    assertEquals("C", key.toString());
    key = new Key(Letter.G, Accidental.NONE);
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
  public void testKeySignature(){
    KeySignature ks;
    Iterator<Letter> letters;

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");

    ks = new KeySignature(Key.fromString("E"), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C#");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D#");

    ks = new KeySignature(Key.fromString("Gb"), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Gb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Ab");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Bb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Cb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Db");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "Eb");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");

    ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MINOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    assertEquals(new Key(letters.next()).apply(ks).toString(), "A");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "B");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "C");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "D");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "E");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "F");
    assertEquals(new Key(letters.next()).apply(ks).toString(), "G");
  }

  @Test
  public void testManipulations(){
    Key key;

    key = new Key(Letter.C);

    key = key.none();
    assertEquals("C", key.toString());
    assertTrue(key.isNone());
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
  public void testEnharmonic(){
    Key a, b;

    String[] keysA = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    String[] keysB = {"Dbb", "Db", "Ebb", "Eb", "Fb", "Gbb", "Gb", "Abb", "Ab", "Bbb", "Bb", "Cb"};
    for(int i = 0; i < keysA.length; ++i){
      a = Key.fromString(keysA[i]);
      b = Key.fromString(keysB[i]);
      assertTrue(Key.isEnharmonic(a, b));
    }
  }

  @Test
  public void testOffset(){
    Key key;

    String[] keysSharp = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    for(int i = 0; i < keysSharp.length; ++i){
      key = Key.fromOffset(i, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.SHARP));
      assertEquals(i, key.getOffset());
      assertEquals(keysSharp[i], key.toString());
    }
    String[] keysFlat = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
    for(int i = 0; i < keysFlat.length; ++i){
      key = Key.fromOffset(i, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT));
      assertEquals(i, key.getOffset());
      assertEquals(keysFlat[i], key.toString());
    }

    try{
      Key.fromOffset(0, Policy.prioritize());
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Policies should not be empty", e.getMessage());
    }
  }

  @Test
  public void testString(){
    Key key;

    String[] keysUppercase = {"C", "D", "E#", "Fx", "G#x", "Ab", "Bbb", "Cbbb"};
    for(int i = 0; i < keysUppercase.length; ++i){
      key = Key.fromString(keysUppercase[i]);
      assertEquals(keysUppercase[i], key.toString());
    }

    String[] keysLowercase = {"c", "d", "e#", "fx", "g#x", "ab", "bbb", "cbbb"};
    for(int i = 0; i < keysLowercase.length; ++i){
      key = Key.fromString(keysLowercase[i]);
      assertEquals(keysUppercase[i], key.toString());
    }

    try{
      Key.fromString(null);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid key string: null", e.getMessage());
    }

    try{
      Key.fromString("");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid key string:  (blank)", e.getMessage());
    }
    
    try{
      Key.fromString("  ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid key string:    (blank)", e.getMessage());
    }
  }

  @Test
  public void testHashCodeAndEquals(){
    Key a, b;
    
    a = Key.fromString("C");
    b = Key.fromString("C");
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C");
    b = Key.fromString("C#");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("C#");
    b = Key.fromString("Db");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("C");
    b = Key.fromString("Dbb");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("C");
    b = a.none();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("Cx");
    b = a.doubleSharp();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C");
    b = a.flat();
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("C");
    b = Key.fromString("B#");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("Cb");
    b = Key.fromString("B");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Key.fromString("C");
    b = Key.fromString("B#").getEnharmonicEquivalent(Letter.C);
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Key.fromString("C");
    assertFalse(a.equals("C"));

    a = Key.fromString("C");
    assertTrue(a.equals(a));
  }
}
