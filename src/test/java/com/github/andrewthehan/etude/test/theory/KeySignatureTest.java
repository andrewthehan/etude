
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Arrays;

public class KeySignatureTest{

  @Test
  public void testIsMajorAndIsMinor(){
    KeySignature ks;

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MAJOR);
    assertTrue(ks.isMajor());
    assertFalse(ks.isMinor());

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MINOR);
    assertFalse(ks.isMajor());
    assertTrue(ks.isMinor());

    ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MAJOR);
    assertTrue(ks.isMajor());
    assertFalse(ks.isMinor());

    ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MINOR);
    assertFalse(ks.isMajor());
    assertTrue(ks.isMinor());
  }

  @Test
  public void testDegreeKey(){
    Degree degree;
    Key key;

    KeySignature ks = new KeySignature(Key.fromString("E"), KeySignature.Quality.MAJOR);

    degree = Degree.TONIC;
    assertEquals("E", ks.keyOf(degree).toString());
    degree = Degree.SUPERTONIC;
    assertEquals("F#", ks.keyOf(degree).toString());
    degree = Degree.MEDIANT;
    assertEquals("G#", ks.keyOf(degree).toString());
    degree = Degree.SUBDOMINANT;
    assertEquals("A", ks.keyOf(degree).toString());
    degree = Degree.DOMINANT;
    assertEquals("B", ks.keyOf(degree).toString());
    degree = Degree.SUBMEDIANT;
    assertEquals("C#", ks.keyOf(degree).toString());
    degree = Degree.LEADING_TONE;
    assertEquals("D#", ks.keyOf(degree).toString());

    key = Key.fromString("E");
    assertEquals(Degree.TONIC, ks.degreeOf(key));
    key = Key.fromString("F");
    assertEquals(Degree.SUPERTONIC, ks.degreeOf(key));
    key = Key.fromString("G#");
    assertEquals(Degree.MEDIANT, ks.degreeOf(key));
    key = Key.fromString("Ax");
    assertEquals(Degree.SUBDOMINANT, ks.degreeOf(key));
    key = Key.fromString("Bb");
    assertEquals(Degree.DOMINANT, ks.degreeOf(key));
    key = Key.fromString("Cbb");
    assertEquals(Degree.SUBMEDIANT, ks.degreeOf(key));
    key = Key.fromString("D");
    assertEquals(Degree.LEADING_TONE, ks.degreeOf(key));
  }

  @Test
  public void testAccidentals(){
    KeySignature ks;
    String keys;

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MAJOR);
    assertEquals(0, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("", keys);

    ks = new KeySignature(Key.fromString("G"), KeySignature.Quality.MAJOR);
    assertEquals(1, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#", keys);

    ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MAJOR);
    assertEquals(3, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#,C#,G#", keys);

    ks = new KeySignature(Key.fromString("C#"), KeySignature.Quality.MAJOR);
    assertEquals(7, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#,C#,G#,D#,A#,E#,B#", keys);

    ks = new KeySignature(Key.fromString("F"), KeySignature.Quality.MAJOR);
    assertEquals(1, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb", keys);

    ks = new KeySignature(Key.fromString("Eb"), KeySignature.Quality.MAJOR);
    assertEquals(3, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb,Eb,Ab", keys);

    ks = new KeySignature(Key.fromString("Cb"), KeySignature.Quality.MAJOR);
    assertEquals(7, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb,Eb,Ab,Db,Gb,Cb,Fb", keys);

    ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MINOR);
    assertEquals(0, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("", keys);

    ks = new KeySignature(Key.fromString("E"), KeySignature.Quality.MINOR);
    assertEquals(1, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#", keys);

    ks = new KeySignature(Key.fromString("F#"), KeySignature.Quality.MINOR);
    assertEquals(3, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#,C#,G#", keys);

    ks = new KeySignature(Key.fromString("A#"), KeySignature.Quality.MINOR);
    assertEquals(7, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("F#,C#,G#,D#,A#,E#,B#", keys);

    ks = new KeySignature(Key.fromString("D"), KeySignature.Quality.MINOR);
    assertEquals(1, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb", keys);

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MINOR);
    assertEquals(3, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb,Eb,Ab", keys);

    ks = new KeySignature(Key.fromString("Ab"), KeySignature.Quality.MINOR);
    assertEquals(7, ks.getAccidentalCount());
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals("Bb,Eb,Ab,Db,Gb,Cb,Fb", keys);
  }

  @Test
  public void testFromAccidentals(){
    KeySignature ks;

    ks = KeySignature.fromAccidentals(Accidental.NONE, 0, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("C"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.NONE, 0, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("A"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.NATURAL, 0, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("C"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.NATURAL, 0, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("A"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    try{
      KeySignature.fromAccidentals(Accidental.NONE, 1, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 1 ", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.NONE, 1, KeySignature.Quality.MINOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 1 ", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.SHARP, 0, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 0 #", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.SHARP, 0, KeySignature.Quality.MINOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 0 #", e.getMessage());
    }

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 1, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("G"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 1, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("E"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 2, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("D"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 2, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("B"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 3, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("A"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 3, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("F#"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 4, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("E"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 4, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("C#"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 5, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("B"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 5, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("G#"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 6, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("F#"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 6, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("D#"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 7, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("C#"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 7, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("A#"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());


    try{
      KeySignature.fromAccidentals(Accidental.FLAT, 0, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 0 b", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.FLAT, 0, KeySignature.Quality.MINOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid count for accidental type: 0 b", e.getMessage());
    }

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 1, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("F"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 1, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("D"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 2, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Bb"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 2, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("G"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 3, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Eb"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 3, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("C"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 4, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Ab"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 4, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("F"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 5, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Db"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 5, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("Bb"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 6, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Gb"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 6, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("Eb"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 7, KeySignature.Quality.MAJOR);
    assertEquals(Key.fromString("Cb"), ks.getKey());
    assertEquals(KeySignature.Quality.MAJOR, ks.getQuality());

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 7, KeySignature.Quality.MINOR);
    assertEquals(Key.fromString("Ab"), ks.getKey());
    assertEquals(KeySignature.Quality.MINOR, ks.getQuality());

    try{
      KeySignature.fromAccidentals(Accidental.NONE, -1, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid accidental count: -1", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.NONE, 8, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid accidental count: 8", e.getMessage());
    }

    try{
      KeySignature.fromAccidentals(Accidental.DOUBLE_SHARP, 1, KeySignature.Quality.MAJOR);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid accidental type to create KeySignature from: x", e.getMessage());
    }
  }

  @Test
  public void testParallel(){
    KeySignature ks;
    KeySignature parallel;

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MAJOR);
    parallel = ks.getParallel();
    assertEquals(Key.fromString("C"), parallel.getKey());
    assertEquals(KeySignature.Quality.MINOR, parallel.getQuality());

    parallel = parallel.getParallel();
    assertEquals(Key.fromString("C"), parallel.getKey());
    assertEquals(KeySignature.Quality.MAJOR, parallel.getQuality());
  }

  @Test
  public void testRelative(){
    KeySignature ks;
    KeySignature relative;

    ks = new KeySignature(Key.fromString("C"), KeySignature.Quality.MAJOR);
    relative = ks.getRelative();
    assertEquals(Key.fromString("A"), relative.getKey());
    assertEquals(KeySignature.Quality.MINOR, relative.getQuality());

    relative = relative.getRelative();
    assertEquals(Key.fromString("C"), relative.getKey());
    assertEquals(KeySignature.Quality.MAJOR, relative.getQuality());
  }

  @Test
  public void testValueOf(){
    assertEquals(KeySignature.Quality.MAJOR, KeySignature.Quality.valueOf("MAJOR"));
    assertEquals(KeySignature.Quality.MINOR, KeySignature.Quality.valueOf("MINOR"));
  }
}
