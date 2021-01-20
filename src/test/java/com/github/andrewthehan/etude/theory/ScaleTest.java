
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class ScaleTest {
  @Test
  public void testStepPatternConstructor() {
    int[] stepPattern;
    Scale scale;

    stepPattern = new int[] { 1, 3, 5 };
    scale = new Scale(Pitch.fromString("C4").get(), stepPattern);
    assertEquals("[C4(48), C#4(49), E4(52), A4(57)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(4).toArray(Pitch[]::new)));
    assertEquals("[C4(48), C#4(49), E4(52), A4(57), A#4(58), C#5(61), F#5(66)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Pitch[]::new)));

    stepPattern = new int[] { 4, 3, 5 };
    scale = new Scale(Pitch.fromString("C4").get(), stepPattern);
    assertEquals("[C4(48), E4(52), G4(55), C5(60), E5(64), G5(67), C6(72)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Pitch[]::new)));
    assertEquals("[C4(48), G3(43), E3(40), C3(36), G2(31), E2(28), C2(24)]",
        Arrays.toString(scale.stream(Direction.DESCENDING).limit(7).toArray(Pitch[]::new)));

    scale = new Scale(Pitch.fromString("Eb4").get(), stepPattern);
    assertEquals("[Eb4(51), G4(55), Bb4(58), Eb5(63), G5(67), Bb5(70), Eb6(75)]",
        Arrays.toString(scale
            .stream(Policy.prioritize(
                Policy.matchKeySignature(KeySignature.fromKey(Key.fromString("Eb").get(), KeySignature.Quality.MAJOR))))
            .limit(Letter.SIZE).toArray(Pitch[]::new)));

    stepPattern = new int[] { 1, 2, 1, 2, 1, 2, 1 };
    scale = new Scale(Pitch.fromString("C4").get(), stepPattern);
    assertEquals("[C4(48), C#4(49), D#4(51), E4(52), F#4(54), G4(55), A4(57), A#4(58)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new)));
    assertEquals("[C4(48), B3(47), A3(45), G#3(44), F#3(42), F3(41), D#3(39), D3(38)]",
        Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new)));

    stepPattern = new int[] { 2, -1 };
    scale = new Scale(Pitch.fromString("C4").get(), stepPattern);
    assertEquals(
        "[C4(48), D4(50), C#4(49), D#4(51), D4(50), E4(52), D#4(51), F4(53), E4(52), F#4(54), F4(53), G4(55), F#4(54), G#4(56), G4(55), A4(57), G#4(56), A#4(58), A4(57), B4(59), A#4(58), C5(60), B4(59), C#5(61), C5(60)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(25).toArray(Pitch[]::new)));
    assertEquals(
        "[C4(48), C#4(49), B3(47), C4(48), A#3(46), B3(47), A3(45), A#3(46), G#3(44), A3(45), G3(43), G#3(44), F#3(42), G3(43), F3(41), F#3(42), E3(40), F3(41), D#3(39), E3(40), D3(38), D#3(39), C#3(37), D3(38), C3(36)]",
        Arrays.toString(scale.stream(Direction.DESCENDING).limit(25).toArray(Pitch[]::new)));

    int[] ascending = new int[] { 2 };
    int[] descending = new int[] { -1 };
    scale = new Scale(Pitch.fromString("C4").get(), ascending, descending);
    assertEquals("[C4(48), D4(50), E4(52), F#4(54), G#4(56), A#4(58), C5(60)]",
        Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Pitch[]::new)));
    assertEquals(
        "[C4(48), B3(47), A#3(46), A3(45), G#3(44), G3(43), F#3(42), F3(41), E3(40), D#3(39), D3(38), C#3(37), C3(36)]",
        Arrays.toString(scale.stream(Direction.DESCENDING).limit(13).toArray(Pitch[]::new)));
  }

  @Test
  public void testStream() {
    Scale scale;
    String pitches;

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), D4(50), E4(52), F4(53), G4(55), A4(57), B4(59), C5(60)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), B3(47), A3(45), G3(43), F3(41), E3(40), D3(38), C3(36)]", pitches);

    scale = new Scale(Pitch.fromString("G4").get(), Scale.Quality.MAJOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[G4(55), A4(57), B4(59), C5(60), D5(62), E5(64), F#5(66), G5(67)]", pitches);

    scale = new Scale(Pitch.fromString("G4").get(), Scale.Quality.MAJOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[G4(55), F#4(54), E4(52), D4(50), C4(48), B3(47), A3(45), G3(43)]", pitches);

    scale = new Scale(Pitch.fromString("A4").get(), Scale.Quality.NATURAL_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[A4(57), B4(59), C5(60), D5(62), E5(64), F5(65), G5(67), A5(69)]", pitches);

    scale = new Scale(Pitch.fromString("A4").get(), Scale.Quality.NATURAL_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[A4(57), G4(55), F4(53), E4(52), D4(50), C4(48), B3(47), A3(45)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.NATURAL_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), Ab4(56), Bb4(58), C5(60)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.NATURAL_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), Bb3(46), Ab3(44), G3(43), F3(41), Eb3(39), D3(38), C3(36)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.HARMONIC_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), Ab4(56), B4(59), C5(60)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.HARMONIC_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), B3(47), Ab3(44), G3(43), F3(41), Eb3(39), D3(38), C3(36)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MELODIC_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), A4(57), B4(59), C5(60)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MELODIC_MINOR);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Pitch[]::new));
    assertEquals("[C4(48), Bb3(46), Ab3(44), G3(43), F3(41), Eb3(39), D3(38), C3(36)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(13).toArray(Pitch[]::new));
    assertEquals(
        "[C4(48), C#4(49), D4(50), D#4(51), E4(52), F4(53), F#4(54), G4(55), G#4(56), A4(57), A#4(58), B4(59), C5(60)]",
        pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(13).toArray(Pitch[]::new));
    assertEquals(
        "[C4(48), B3(47), A#3(46), A3(45), G#3(44), G3(43), F#3(42), F3(41), E3(40), D#3(39), D3(38), C#3(37), C3(36)]",
        pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT))
        .limit(13).toArray(Pitch[]::new));
    assertEquals(
        "[C4(48), Db4(49), D4(50), Eb4(51), E4(52), F4(53), Gb4(54), G4(55), Ab4(56), A4(57), Bb4(58), B4(59), C5(60)]",
        pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT))
        .limit(13).toArray(Pitch[]::new));
    assertEquals(
        "[C4(48), B3(47), Bb3(46), A3(45), Ab3(44), G3(43), Gb3(42), F3(41), E3(40), Eb3(39), D3(38), Db3(37), C3(36)]",
        pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.WHOLE_TONE);
    pitches = Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Pitch[]::new));
    assertEquals("[C4(48), D4(50), E4(52), F#4(54), G#4(56), A#4(58), C5(60)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.WHOLE_TONE);
    pitches = Arrays.toString(scale.stream(Direction.DESCENDING).limit(7).toArray(Pitch[]::new));
    assertEquals("[C4(48), A#3(46), G#3(44), F#3(42), E3(40), D3(38), C3(36)]", pitches);

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    assertFalse(scale.stream().limit(8).toArray() == scale.stream(Direction.DEFAULT).limit(8).toArray());
    assertEquals(Arrays.toString(scale.stream().limit(8).toArray()),
        Arrays.toString(scale.stream(Direction.DEFAULT).limit(8).toArray()));
  }

  @Test
  public void testIterator() {
    Scale scale;
    String[] pitches;
    Iterator<Pitch> it;

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    pitches = new String[] { "C4(48)", "D4(50)", "E4(52)", "F4(53)", "G4(55)", "A4(57)", "B4(59)", "C5(60)", "D5(62)",
        "E5(64)", "F5(65)", "G5(67)", "A5(69)", "B5(71)", };
    it = scale.iterator();
    for (int i = 0; i < pitches.length; ++i) {
      assertEquals(pitches[i], it.next().toString());
    }

    scale = new Scale(Pitch.fromString("A4").get(), Scale.Quality.NATURAL_MINOR);
    pitches = new String[] { "A4(57)", "B4(59)", "C5(60)", "D5(62)", "E5(64)", "F5(65)", "G5(67)", "A5(69)", "B5(71)",
        "C6(72)", "D6(74)", "E6(76)", "F6(77)", "G6(79)", };
    it = scale.iterator();
    for (int i = 0; i < pitches.length; ++i) {
      assertEquals(pitches[i], it.next().toString());
    }
  }

  @Test
  public void testString() {
    Scale scale;

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    assertEquals("[C4(48), D4(50), E4(52), F4(53), G4(55), A4(57), B4(59)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MAJOR);
    assertEquals("[C4(48), B3(47), A3(45), G3(43), F3(41), E3(40), D3(38)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("G4").get(), Scale.Quality.MAJOR);
    assertEquals("[G4(55), A4(57), B4(59), C5(60), D5(62), E5(64), F#5(66)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("G4").get(), Scale.Quality.MAJOR);
    assertEquals("[G4(55), F#4(54), E4(52), D4(50), C4(48), B3(47), A3(45)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("A4").get(), Scale.Quality.NATURAL_MINOR);
    assertEquals("[A4(57), B4(59), C5(60), D5(62), E5(64), F5(65), G5(67)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("A4").get(), Scale.Quality.NATURAL_MINOR);
    assertEquals("[A4(57), G4(55), F4(53), E4(52), D4(50), C4(48), B3(47)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.NATURAL_MINOR);
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), Ab4(56), Bb4(58)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.NATURAL_MINOR);
    assertEquals("[C4(48), Bb3(46), Ab3(44), G3(43), F3(41), Eb3(39), D3(38)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.HARMONIC_MINOR);
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), Ab4(56), B4(59)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.HARMONIC_MINOR);
    assertEquals("[C4(48), B3(47), Ab3(44), G3(43), F3(41), Eb3(39), D3(38)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MELODIC_MINOR);
    assertEquals("[C4(48), D4(50), Eb4(51), F4(53), G4(55), A4(57), B4(59)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.MELODIC_MINOR);
    assertEquals("[C4(48), Bb3(46), Ab3(44), G3(43), F3(41), Eb3(39), D3(38)]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    assertEquals("[C4(48)]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Pitch.fromString("C4").get(), Scale.Quality.CHROMATIC);
    assertEquals("[C4(48)]", scale.toString(Direction.DESCENDING));
  }
}