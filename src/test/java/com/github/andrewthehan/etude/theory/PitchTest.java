
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.Exceptional;

import org.junit.Test;

public class PitchTest {

  @Test
  public void testConstructor() {
    Pitch pitch;

    pitch = new Pitch(Key.fromString("C").get(), 4);
    assertEquals("C4(48)", pitch.toString());
    pitch = new Pitch(Key.fromString("C").get(), 0);
    assertEquals("C0(0)", pitch.toString());
    pitch = new Pitch(Key.fromString("G").get(), 10);
    assertEquals("G10(127)", pitch.toString());

    try {
      pitch = new Pitch(Key.fromString("G#").get(), 10);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      pitch = new Pitch(Key.fromString("Cb").get(), 0);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testKeySignature() {
    KeySignature ks;
    Iterator<Letter> letters;
    Pitch pitch;

    ks = KeySignature.fromKey(Key.fromString("C").get(), KeySignature.Quality.MAJOR);
    letters = Letter.iterator(Letter.C);
    pitch = new Pitch(Key.fromString("C").get(), 4);
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "C5(60)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "D4(50)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "E4(52)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "F4(53)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "G4(55)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "A4(57)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "B4(59)");

    ks = KeySignature.fromKey(Key.fromString("A").get(), KeySignature.Quality.MINOR);
    letters = Letter.iterator(Letter.A);
    pitch = new Pitch(Key.fromString("A").get(), 4);
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "A5(69)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "B4(59)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "C5(60)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "D5(62)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "E5(64)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "F5(65)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).get().apply(ks).toString(), "G5(67)");
  }

  @Test
  public void testSteps() {
    Pitch pitch;

    pitch = Pitch.fromProgramNumber(0).get();
    for (int i = 0; i < 127; i++) {
      pitch = pitch.halfStepUp().get();
    }
    assertEquals(127, pitch.getProgramNumber());
    assertEquals("G10(127)", pitch.toString());
    for (int i = 0; i < 127; i++) {
      pitch = pitch.halfStepDown().get();
    }
    assertEquals(0, pitch.getProgramNumber());
    assertEquals("C0(0)", pitch.toString());
    pitch = pitch.step(127).get();
    assertEquals(127, pitch.getProgramNumber());
    assertEquals("G10(127)", pitch.toString());
    pitch = pitch.step(-127).get();
    assertEquals(0, pitch.getProgramNumber());
    assertEquals("C0(0)", pitch.toString());

    pitch = Pitch.fromString("Cbbb4").get();
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cbb4(46)", pitch.toString());
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cb4(47)", pitch.toString());
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("C4(48)", pitch.toString());
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("C#4(49)", pitch.toString());
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cx4(50)", pitch.toString());
    pitch = pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("C#x4(51)", pitch.toString());

    try {
      pitch.halfStepUp(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cx4(50)", pitch.toString());
    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("C#4(49)", pitch.toString());
    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("C4(48)", pitch.toString());
    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cb4(47)", pitch.toString());
    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cbb4(46)", pitch.toString());
    pitch = pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
    assertEquals("Cbbb4(45)", pitch.toString());

    try {
      pitch.halfStepDown(Policy.prioritize(Policy.matchLetter(pitch.getKey().getLetter()))).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    String[] pitchesSharp = { "C4(48)", "C#4(49)", "D4(50)", "D#4(51)", "E4(52)", "F4(53)", "F#4(54)", "G4(55)",
        "G#4(56)", "A4(57)", "A#4(58)", "B4(59)", "C5(60)" };
    String[] pitchesFlat = { "C4(48)", "Db4(49)", "D4(50)", "Eb4(51)", "E4(52)", "F4(53)", "Gb4(54)", "G4(55)",
        "Ab4(56)", "A4(57)", "Bb4(58)", "B4(59)", "C5(60)" };

    pitch = Pitch.fromProgramNumber(47).get();
    for (int i = 0; i < pitchesSharp.length; ++i) {
      pitch = pitch.halfStepUp(Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.SHARP)).get();
      assertEquals(pitchesSharp[i], pitch.toString());
    }
    pitch = pitch.halfStepUp().get();
    for (int i = pitchesSharp.length - 1; i >= 0; --i) {
      pitch = pitch.halfStepDown(Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.SHARP)).get();
      assertEquals(pitchesSharp[i], pitch.toString());
    }

    pitch = Pitch.fromProgramNumber(47).get();
    for (int i = 0; i < pitchesFlat.length; ++i) {
      pitch = pitch.halfStepUp(Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT)).get();
      assertEquals(pitchesFlat[i], pitch.toString());
    }
    pitch = pitch.halfStepUp().get();
    for (int i = pitchesFlat.length - 1; i >= 0; --i) {
      pitch = pitch.halfStepDown(Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT)).get();
      assertEquals(pitchesFlat[i], pitch.toString());
    }

    pitch = Pitch.fromString("C4").get();
    assertEquals(pitch, pitch.step(0).get());

    assertEquals(Pitch.fromString("C4(48)").get(), pitch.step(0, Policy.DEFAULT_PRIORITY).get());
    assertEquals(Pitch.fromString("B#3(48)").get(), pitch.step(0, Policy.prioritize(Policy.SHARP)).get());

    try {
      pitch.step(0, Policy.prioritize(Policy.DOUBLE_SHARP)).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    assertEquals(Pitch.fromString("A#x3(48)").get(), pitch.step(0, Policy.prioritize(Policy.TRIPLE_SHARP)).get());

    try {
      pitch.step(0, Policy.prioritize(Policy.FLAT)).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    assertEquals(Pitch.fromString("Dbb4(48)").get(), pitch.step(0, Policy.prioritize(Policy.DOUBLE_FLAT)).get());

    try {
      pitch.step(0, Policy.prioritize(Policy.TRIPLE_FLAT)).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testInterval() {
    Pitch pitch;
    Interval interval;

    pitch = Pitch.fromString("C4").get();

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 1);
    assertEquals("Cbb4(46)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 1);
    assertEquals("Cb4(47)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals("C4(48)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 1);
    assertEquals("C#4(49)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 1);
    assertEquals("Cx4(50)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 2);
    assertEquals("Dbbb4(47)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 2);
    assertEquals("Dbb4(48)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MINOR, 2);
    assertEquals("Db4(49)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MAJOR, 2);
    assertEquals("D4(50)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 2);
    assertEquals("D#4(51)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 2);
    assertEquals("Dx4(52)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals("Ebbb4(49)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals("Ebb4(50)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals("Eb4(51)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals("E4(52)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 3);
    assertEquals("E#4(53)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 3);
    assertEquals("Ex4(54)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 4);
    assertEquals("Fbb4(51)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 4);
    assertEquals("Fb4(52)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.PERFECT, 4);
    assertEquals("F4(53)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals("F#4(54)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals("Fx4(55)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals("Gbb4(53)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals("Gb4(54)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals("G4(55)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 5);
    assertEquals("G#4(56)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 5);
    assertEquals("Gx4(57)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 6);
    assertEquals("Abbb4(54)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 6);
    assertEquals("Abb4(55)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MINOR, 6);
    assertEquals("Ab4(56)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MAJOR, 6);
    assertEquals("A4(57)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 6);
    assertEquals("A#4(58)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 6);
    assertEquals("Ax4(59)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 7);
    assertEquals("Bbbb4(56)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 7);
    assertEquals("Bbb4(57)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MINOR, 7);
    assertEquals("Bb4(58)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MAJOR, 7);
    assertEquals("B4(59)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 7);
    assertEquals("B#4(60)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 7);
    assertEquals("Bx4(61)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 8);
    assertEquals("Cbb5(58)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 8);
    assertEquals("Cb5(59)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals("C5(60)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 8);
    assertEquals("C#5(61)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 8);
    assertEquals("Cx5(62)", pitch.step(interval).get().toString());

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 9);
    assertEquals("Dbbb5(59)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DIMINISHED, 9);
    assertEquals("Dbb5(60)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MINOR, 9);
    assertEquals("Db5(61)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.MAJOR, 9);
    assertEquals("D5(62)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.AUGMENTED, 9);
    assertEquals("D#5(63)", pitch.step(interval).get().toString());
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 9);
    assertEquals("Dx5(64)", pitch.step(interval).get().toString());
  }

  @Test
  public void testHigherLower() {
    Pitch pitch;

    pitch = Pitch.fromString("C4").get();

    assertTrue(pitch.isHigherThan(Pitch.fromString("C3").get()));
    assertTrue(pitch.isHigherThan(Pitch.fromString("Cb4").get()));
    assertTrue(pitch.isHigherThan(Pitch.fromString("B3").get()));
    assertTrue(pitch.isHigherThan(Pitch.fromString("Dbbb4").get()));
    assertTrue(pitch.isLowerThan(Pitch.fromString("C5").get()));
    assertTrue(pitch.isLowerThan(Pitch.fromString("C#4").get()));
    assertTrue(pitch.isLowerThan(Pitch.fromString("Db4").get()));
    assertTrue(pitch.isLowerThan(Pitch.fromString("B#x3").get()));

    assertEquals("D4(50)", pitch.getHigherPitch(Key.fromString("D").get()).get().toString());
    assertEquals("D3(38)", pitch.getLowerPitch(Key.fromString("D").get()).get().toString());
    assertEquals("C5(60)", pitch.getHigherPitch(Key.fromString("C").get()).get().toString());
    assertEquals("C3(36)", pitch.getLowerPitch(Key.fromString("C").get()).get().toString());

    assertEquals("Dbb5(60)", pitch.getHigherPitch(Key.fromString("Dbb").get()).get().toString());
    assertEquals("Dbb3(36)", pitch.getLowerPitch(Key.fromString("Dbb").get()).get().toString());
    assertEquals("Cbb5(58)", pitch.getHigherPitch(Key.fromString("Cbb").get()).get().toString());
    assertEquals("Cbb4(46)", pitch.getLowerPitch(Key.fromString("Cbb").get()).get().toString());
    assertEquals("Cx4(50)", pitch.getHigherPitch(Key.fromString("Cx").get()).get().toString());
    assertEquals("Cx3(38)", pitch.getLowerPitch(Key.fromString("Cx").get()).get().toString());
    assertEquals("B#4(60)", pitch.getHigherPitch(Key.fromString("B#").get()).get().toString());
    assertEquals("B#2(36)", pitch.getLowerPitch(Key.fromString("B#").get()).get().toString());

    String sorted = Stream.of("C4", "G4", "D4", "A4", "E4", "B4", "F4").map(Pitch::fromString).map(Exceptional::get)
        .sorted().map(Pitch::toString).collect(Collectors.joining(" "));
    assertEquals("C4(48) D4(50) E4(52) F4(53) G4(55) A4(57) B4(59)", sorted);
  }

  @Test
  public void testEnharmonic() {
    Pitch a, b;

    String[] pitchesA = { "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4" };
    String[] pitchesB = { "Dbb4", "Db4", "Ebb4", "Eb4", "Fb4", "Gbb4", "Gb4", "Abb4", "Ab4", "Bbb4", "Bb4", "Cb5" };
    for (int i = 0; i < pitchesA.length; ++i) {
      a = Pitch.fromString(pitchesA[i]).get();
      b = Pitch.fromString(pitchesB[i]).get();
      assertTrue(Pitch.isEnharmonic(a, b));
    }

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C4").get();
    assertTrue(Pitch.isEnharmonic(a, b));

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C5").get();
    assertFalse(Pitch.isEnharmonic(a, b));

    a = Pitch.fromString("C#4").get();
    b = Pitch.fromString("Db5").get();
    assertFalse(Pitch.isEnharmonic(a, b));
  }

  @Test
  public void testProgramNumber() {
    Pitch pitch;

    pitch = Pitch.fromProgramNumber(48).get();
    assertEquals(48, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C4(48)", pitch.toString());
    pitch = Pitch.fromProgramNumber(0).get();
    assertEquals(0, pitch.getProgramNumber());
    assertEquals(0, pitch.getOctave());
    assertEquals("C0(0)", pitch.toString());
    pitch = Pitch.fromProgramNumber(127).get();
    assertEquals(127, pitch.getProgramNumber());
    assertEquals(10, pitch.getOctave());
    assertEquals("G10(127)", pitch.toString());

    try {
      Pitch.fromProgramNumber(-1).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromProgramNumber(128).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testString() {
    Pitch pitch;

    pitch = Pitch.fromString("C4").get();
    assertEquals(48, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C4(48)", pitch.toString());
    pitch = Pitch.fromString("C4").get();
    assertEquals(48, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C4(48)", pitch.toString());
    pitch = Pitch.fromString("C#4").get();
    assertEquals(49, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C#4(49)", pitch.toString());
    pitch = Pitch.fromString("Cx4").get();
    assertEquals(50, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("Cx4(50)", pitch.toString());
    pitch = Pitch.fromString("C#x4").get();
    assertEquals(51, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C#x4(51)", pitch.toString());
    pitch = Pitch.fromString("Cb4").get();
    assertEquals(47, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("Cb4(47)", pitch.toString());
    pitch = Pitch.fromString("Cbb4").get();
    assertEquals(46, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("Cbb4(46)", pitch.toString());
    pitch = Pitch.fromString("Cbbb4").get();
    assertEquals(45, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("Cbbb4(45)", pitch.toString());
    pitch = Pitch.fromString("C4(48)").get();
    assertEquals(48, pitch.getProgramNumber());
    assertEquals(4, pitch.getOctave());
    assertEquals("C4(48)", pitch.toString());
    try {
      Pitch.fromString("A").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("C(48)").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("Abbbb4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("C4(49)").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("A4B4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("C4(48").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("C4(48))").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString(null).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
    try {
      Pitch.fromString("  ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testHashCodeAndEquals() {
    Pitch a, b;

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C4").get();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("D4").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Pitch.fromString("C#4").get();
    b = Pitch.fromString("Db4").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Pitch.fromString("C4").get();
    b = Pitch.fromString("C5").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Pitch.fromString("C4").get();
    b = a.step(1).get().step(-1).get();
    assertTrue(a.equals(b));
    assertTrue(b.hashCode() == b.hashCode());
  }
}
