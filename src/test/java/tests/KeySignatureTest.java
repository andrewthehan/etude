
package tests;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Arrays;

public class KeySignatureTest{

  @Test
  public void testDegreeKey(){
    Degree degree;
    Key key;

    KeySignature ks = new KeySignature(Key.fromString("E"), Mode.MAJOR);

    degree = Degree.TONIC;
    assertEquals(ks.keyOf(degree).toString(), "En");
    degree = Degree.SUPERTONIC;
    assertEquals(ks.keyOf(degree).toString(), "F#");
    degree = Degree.MEDIANT;
    assertEquals(ks.keyOf(degree).toString(), "G#");
    degree = Degree.SUBDOMINANT;
    assertEquals(ks.keyOf(degree).toString(), "An");
    degree = Degree.DOMINANT;
    assertEquals(ks.keyOf(degree).toString(), "Bn");
    degree = Degree.SUBMEDIANT;
    assertEquals(ks.keyOf(degree).toString(), "C#");
    degree = Degree.LEADING_TONE;
    assertEquals(ks.keyOf(degree).toString(), "D#");

    key = Key.fromString("E");
    assertEquals(ks.degreeOf(key), Degree.TONIC);
    key = Key.fromString("Fn");
    assertEquals(ks.degreeOf(key), Degree.SUPERTONIC);
    key = Key.fromString("G#");
    assertEquals(ks.degreeOf(key), Degree.MEDIANT);
    key = Key.fromString("Ax");
    assertEquals(ks.degreeOf(key), Degree.SUBDOMINANT);
    key = Key.fromString("Bb");
    assertEquals(ks.degreeOf(key), Degree.DOMINANT);
    key = Key.fromString("Cbb");
    assertEquals(ks.degreeOf(key), Degree.SUBMEDIANT);
    key = Key.fromString("D");
    assertEquals(ks.degreeOf(key), Degree.LEADING_TONE);
  }

  @Test
  public void testAccidentals(){
    KeySignature ks;
    String keys;

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 0);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "");

    ks = new KeySignature(Key.fromString("G"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 1);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#");

    ks = new KeySignature(Key.fromString("A"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 3);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#,C#,G#");

    ks = new KeySignature(Key.fromString("C#"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 7);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#,C#,G#,D#,A#,E#,B#");

    ks = new KeySignature(Key.fromString("F"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 1);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb");

    ks = new KeySignature(Key.fromString("Eb"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 3);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb,Eb,Ab");

    ks = new KeySignature(Key.fromString("Cb"), Mode.MAJOR);
    assertEquals(ks.getAccidentalCount(), 7);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb,Eb,Ab,Db,Gb,Cb,Fb");

    ks = new KeySignature(Key.fromString("A"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 0);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "");

    ks = new KeySignature(Key.fromString("E"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 1);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#");

    ks = new KeySignature(Key.fromString("F#"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 3);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#,C#,G#");

    ks = new KeySignature(Key.fromString("A#"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 7);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "F#,C#,G#,D#,A#,E#,B#");

    ks = new KeySignature(Key.fromString("D"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 1);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb");

    ks = new KeySignature(Key.fromString("C"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 3);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb,Eb,Ab");

    ks = new KeySignature(Key.fromString("Ab"), Mode.NATURAL_MINOR);
    assertEquals(ks.getAccidentalCount(), 7);
    keys = Arrays
      .stream(ks.getKeysWithAccidentals())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bb,Eb,Ab,Db,Gb,Cb,Fb");
  }
}
