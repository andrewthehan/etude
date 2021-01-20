
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class ChordTest {

  @Test
  public void testConstructor() {
    Chord chord;

    Pitch[] pitches;

    pitches = new Pitch[] { Pitch.fromString("C4").get(), Pitch.fromString("E4").get(), Pitch.fromString("G4").get() };
    chord = new Chord(pitches);
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());
    assertEquals(3, chord.getPitches().length);

    Pitch pitch = Pitch.fromString("C4").get();

    chord = new Chord(pitch, Chord.Quality.MAJOR);
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MINOR);
    assertEquals("[C4(48), Eb4(51), G4(55)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.DIMINISHED);
    assertEquals("[C4(48), Eb4(51), Gb4(54)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.AUGMENTED);
    assertEquals("[C4(48), E4(52), G#4(56)]", chord.toString());

    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH);
    assertEquals("[C4(48), E4(52), G4(55), B4(59)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MINOR_SEVENTH);
    assertEquals("[C4(48), Eb4(51), G4(55), Bb4(58)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.DOMINANT_SEVENTH);
    assertEquals("[C4(48), E4(52), G4(55), Bb4(58)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.DIMINISHED_SEVENTH);
    assertEquals("[C4(48), Eb4(51), Gb4(54), Bbb4(57)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.HALF_DIMINISHED_SEVENTH);
    assertEquals("[C4(48), Eb4(51), Gb4(54), Bb4(58)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MINOR_MAJOR_SEVENTH);
    assertEquals("[C4(48), Eb4(51), G4(55), B4(59)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.AUGMENTED_MAJOR_SEVENTH);
    assertEquals("[C4(48), E4(52), G#4(56), B4(59)]", chord.toString());

    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Chord.Inversion.ROOT);
    assertEquals("[C4(48), E4(52), G4(55), B4(59)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Chord.Inversion.FIRST);
    assertEquals("[E4(52), G4(55), B4(59), C5(60)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Chord.Inversion.SECOND);
    assertEquals("[G4(55), B4(59), C5(60), E5(64)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Chord.Inversion.THIRD);
    assertEquals("[B4(59), C5(60), E5(64), G5(67)]", chord.toString());

    try {
      new Chord(pitch, Chord.Quality.MAJOR, Chord.Inversion.THIRD);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testStream() {
    Chord chord;
    Pitch[] pitches;

    chord = new Chord(Pitch.fromString("C4").get(), Chord.Quality.MAJOR);

    pitches = chord.stream().limit(6).toArray(Pitch[]::new);
    assertEquals("[C4(48), E4(52), G4(55), C5(60), E5(64), G5(67)]", Arrays.toString(pitches));

    pitches = chord.stream(Direction.DESCENDING).limit(6).toArray(Pitch[]::new);
    assertEquals("[C4(48), G3(43), E3(40), C3(36), G2(31), E2(28)]", Arrays.toString(pitches));

    chord = new Chord(Pitch.fromString("F#4").get(), Chord.Quality.DIMINISHED_SEVENTH);

    pitches = chord.stream().limit(6).toArray(Pitch[]::new);
    assertEquals("[F#4(54), A4(57), C5(60), Eb5(63), F#5(66), A5(69)]", Arrays.toString(pitches));

    pitches = chord.stream(Direction.DESCENDING).limit(6).toArray(Pitch[]::new);
    assertEquals("[F#4(54), Eb4(51), C4(48), A3(45), F#3(42), Eb3(39)]", Arrays.toString(pitches));
  }

  @Test
  public void testString() {
    Chord chord;

    chord = Chord.fromString("[C4]").get();
    assertEquals("[C4(48)]", chord.toString());

    chord = Chord.fromString("[C4,E4,G4]").get();
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    try {
      Chord.fromString(null).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString(" ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("C4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("[C4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("C4]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("[a[C4]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("[C4]a]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("a[C4]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.fromString("[C4]a").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testBuilder() {
    Chord chord;

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR).build();
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("Ab4").get()).add(Chord.Quality.MINOR)
        .add(Interval.fromString("M6").get()).build();
    assertEquals("[Ab4(56), Cb5(59), Eb5(63), F5(65)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR).build();
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR)
        .setInversion(Chord.Inversion.SECOND).build();
    assertEquals("[G4(55), C5(60), E5(64)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.DOMINANT_SEVENTH)
        .setBottomDegree(Degree.MEDIANT).build();
    assertEquals("[E4(52), G4(55), Bb4(58), C5(60)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.DIMINISHED_SEVENTH)
        .setBottomDegree(Degree.MEDIANT).build();
    assertEquals("[Eb4(51), Gb4(54), Bbb4(57), C5(60)]", chord.toString());

    chord = Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR)
        .add(Interval.fromString("P4").get()).setBottomDegree(Degree.SUBDOMINANT).build();
    assertEquals("[F4(53), G4(55), C5(60), E5(64)]", chord.toString());

    try {
      Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Interval.fromString("P1").get())
          .add(Interval.fromString("P4").get()).add(Interval.fromString("P5").get()).setInversion(Chord.Inversion.FIRST)
          .build();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR).setInversion(Chord.Inversion.THIRD)
          .build();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Chord.builder().setRoot(Pitch.fromString("C4").get()).add(Chord.Quality.MAJOR).setBottomDegree(Degree.SUBDOMINANT)
          .build();
      fail("Expected an expection.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }
}
