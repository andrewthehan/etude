
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class KeySignatureTest {

  @Test
  public void testGetOrderOf() {
    Letter[] letters;

    letters = KeySignature.getOrderOfFlats();
    assertEquals("[B, E, A, D, G, C, F]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(0);
    assertEquals("[]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(1);
    assertEquals("[B]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(2);
    assertEquals("[B, E]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(3);
    assertEquals("[B, E, A]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(4);
    assertEquals("[B, E, A, D]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(5);
    assertEquals("[B, E, A, D, G]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(6);
    assertEquals("[B, E, A, D, G, C]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfFlats(7);
    assertEquals("[B, E, A, D, G, C, F]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps();
    assertEquals("[F, C, G, D, A, E, B]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(0);
    assertEquals("[]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(1);
    assertEquals("[F]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(2);
    assertEquals("[F, C]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(3);
    assertEquals("[F, C, G]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(4);
    assertEquals("[F, C, G, D]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(5);
    assertEquals("[F, C, G, D, A]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(6);
    assertEquals("[F, C, G, D, A, E]", Arrays.toString(letters));

    letters = KeySignature.getOrderOfSharps(7);
    assertEquals("[F, C, G, D, A, E, B]", Arrays.toString(letters));
  }

  @Test
  public void testGetKeyFor() {
    Key key;

    key = KeySignature.getKeyFor(null, 0, KeySignature.Quality.MAJOR).get();
    assertEquals("C", key.toString());

    key = KeySignature.getKeyFor(null, 0, KeySignature.Quality.MINOR).get();
    assertEquals("A", key.toString());

    key = KeySignature.getKeyFor(Accidental.NATURAL, 0, KeySignature.Quality.MAJOR).get();
    assertEquals("C", key.toString());

    key = KeySignature.getKeyFor(Accidental.NATURAL, 0, KeySignature.Quality.MINOR).get();
    assertEquals("A", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 1, KeySignature.Quality.MAJOR).get();
    assertEquals("G", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 1, KeySignature.Quality.MINOR).get();
    assertEquals("E", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 2, KeySignature.Quality.MAJOR).get();
    assertEquals("D", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 2, KeySignature.Quality.MINOR).get();
    assertEquals("B", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 3, KeySignature.Quality.MAJOR).get();
    assertEquals("A", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 3, KeySignature.Quality.MINOR).get();
    assertEquals("F#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 4, KeySignature.Quality.MAJOR).get();
    assertEquals("E", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 4, KeySignature.Quality.MINOR).get();
    assertEquals("C#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 5, KeySignature.Quality.MAJOR).get();
    assertEquals("B", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 5, KeySignature.Quality.MINOR).get();
    assertEquals("G#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 6, KeySignature.Quality.MAJOR).get();
    assertEquals("F#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 6, KeySignature.Quality.MINOR).get();
    assertEquals("D#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 7, KeySignature.Quality.MAJOR).get();
    assertEquals("C#", key.toString());

    key = KeySignature.getKeyFor(Accidental.SHARP, 7, KeySignature.Quality.MINOR).get();
    assertEquals("A#", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 1, KeySignature.Quality.MAJOR).get();
    assertEquals("F", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 1, KeySignature.Quality.MINOR).get();
    assertEquals("D", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 2, KeySignature.Quality.MAJOR).get();
    assertEquals("Bb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 2, KeySignature.Quality.MINOR).get();
    assertEquals("G", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 3, KeySignature.Quality.MAJOR).get();
    assertEquals("Eb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 3, KeySignature.Quality.MINOR).get();
    assertEquals("C", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 4, KeySignature.Quality.MAJOR).get();
    assertEquals("Ab", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 4, KeySignature.Quality.MINOR).get();
    assertEquals("F", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 5, KeySignature.Quality.MAJOR).get();
    assertEquals("Db", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 5, KeySignature.Quality.MINOR).get();
    assertEquals("Bb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 6, KeySignature.Quality.MAJOR).get();
    assertEquals("Gb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 6, KeySignature.Quality.MINOR).get();
    assertEquals("Eb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 7, KeySignature.Quality.MAJOR).get();
    assertEquals("Cb", key.toString());

    key = KeySignature.getKeyFor(Accidental.FLAT, 7, KeySignature.Quality.MINOR).get();
    assertEquals("Ab", key.toString());
  }

  @Test
  public void testFromAccidentals() {
    KeySignature ks;

    ks = KeySignature.fromAccidentals(null, 0).get();
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(null, 0).get();
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.NATURAL, 0).get();
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.NATURAL, 0).get();
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    try {
      KeySignature.fromAccidentals(null, 1).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(null, 1).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(Accidental.SHARP, 0).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(Accidental.SHARP, 0).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 1).get();
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[F#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 2).get();
    assertEquals(2, ks.getAccidentalCount());
    assertEquals("[F#, C#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 3).get();
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 4).get();
    assertEquals(4, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 5).get();
    assertEquals(5, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#, A#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 6).get();
    assertEquals(6, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#, A#, E#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 7).get();
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#, A#, E#, B#]", Arrays.toString(ks.getAccidentals()));

    try {
      KeySignature.fromAccidentals(Accidental.FLAT, 0).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(Accidental.FLAT, 0).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 1).get();
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[Bb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 2).get();
    assertEquals(2, ks.getAccidentalCount());
    assertEquals("[Bb, Eb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 3).get();
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 4).get();
    assertEquals(4, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 5).get();
    assertEquals(5, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db, Gb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 6).get();
    assertEquals(6, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db, Gb, Cb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 7).get();
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db, Gb, Cb, Fb]", Arrays.toString(ks.getAccidentals()));

    try {
      KeySignature.fromAccidentals(null, -1).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(null, 8).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      KeySignature.fromAccidentals(Accidental.DOUBLE_SHARP, 1).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testFromKey() {
    KeySignature ks;

    ks = KeySignature.fromKey(Key.fromString("C").get(), KeySignature.Quality.MAJOR);
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("G").get(), KeySignature.Quality.MAJOR);
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[F#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("A").get(), KeySignature.Quality.MAJOR);
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("C#").get(), KeySignature.Quality.MAJOR);
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#, A#, E#, B#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("F").get(), KeySignature.Quality.MAJOR);
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[Bb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("Eb").get(), KeySignature.Quality.MAJOR);
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("Cb").get(), KeySignature.Quality.MAJOR);
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db, Gb, Cb, Fb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("A").get(), KeySignature.Quality.MINOR);
    assertEquals(0, ks.getAccidentalCount());
    assertEquals("[]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("E").get(), KeySignature.Quality.MINOR);
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[F#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("F#").get(), KeySignature.Quality.MINOR);
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("A#").get(), KeySignature.Quality.MINOR);
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[F#, C#, G#, D#, A#, E#, B#]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("D").get(), KeySignature.Quality.MINOR);
    assertEquals(1, ks.getAccidentalCount());
    assertEquals("[Bb]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("C").get(), KeySignature.Quality.MINOR);
    assertEquals(3, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab]", Arrays.toString(ks.getAccidentals()));

    ks = KeySignature.fromKey(Key.fromString("Ab").get(), KeySignature.Quality.MINOR);
    assertEquals(7, ks.getAccidentalCount());
    assertEquals("[Bb, Eb, Ab, Db, Gb, Cb, Fb]", Arrays.toString(ks.getAccidentals()));
  }

  @Test
  public void testValueOf() {
    assertEquals(KeySignature.Quality.MAJOR, KeySignature.Quality.valueOf("MAJOR"));
    assertEquals(KeySignature.Quality.MINOR, KeySignature.Quality.valueOf("MINOR"));
  }
}
