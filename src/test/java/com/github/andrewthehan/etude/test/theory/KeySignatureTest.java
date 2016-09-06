
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

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

  @Test
  public void testFromAccidentals(){
    KeySignature ks;

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 0, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("C"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 0, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("A"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 1, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("G"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 1, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("E"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 2, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("D"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 2, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("B"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 3, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("A"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 3, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("F#"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 4, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("E"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 4, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("C#"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 5, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("B"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 5, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("G#"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 6, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("F#"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 6, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("D#"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 7, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("C#"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.SHARP, 7, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("A#"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 0, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("C"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 0, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("A"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 1, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("F"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 1, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("D"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 2, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Bb"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 2, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("G"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 3, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Eb"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 3, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("C"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 4, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Ab"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 4, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("F"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 5, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Db"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 5, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("Bb"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 6, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Gb"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 6, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("Eb"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 7, Mode.MAJOR);
    assertEquals(ks.getKey(), Key.fromString("Cb"));
    assertEquals(ks.getMode(), Mode.MAJOR);

    ks = KeySignature.fromAccidentals(Accidental.FLAT, 7, Mode.NATURAL_MINOR);
    assertEquals(ks.getKey(), Key.fromString("Ab"));
    assertEquals(ks.getMode(), Mode.NATURAL_MINOR);
  }
}
