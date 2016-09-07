
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChordTest{

  @Test
  public void testConstructor(){
    Chord chord;

    Pitch[] pitches;

    pitches = new Pitch[]{Pitch.fromString("C4"), Pitch.fromString("E4"), Pitch.fromString("G4")};
    chord = new Chord(pitches);
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());
    assertEquals(3, chord.getPitches().length);

    Pitch pitch = Pitch.fromString("C4");

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

    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.ROOT);
    assertEquals("[C4(48), E4(52), G4(55), B4(59)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.FIRST);
    assertEquals("[E4(52), G4(55), B4(59), C5(60)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.SECOND);
    assertEquals("[G4(55), B4(59), C5(60), E5(64)]", chord.toString());
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.THIRD);
    assertEquals("[B4(59), C5(60), E5(64), G5(67)]", chord.toString());

    try{
      new Chord(pitch, Chord.Quality.MAJOR, Inversion.THIRD);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Unable to invert chord: missing LEADING_TONE pitch", e.getMessage());
    }
  }

  @Test
  public void testString(){
    Chord chord;

    chord = Chord.fromString("[C4]");
    assertEquals("[C4(48)]", chord.toString());

    chord = Chord.fromString("[C4,E4,G4]");
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    try{
      Chord.fromString(null);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: null", e.getMessage());
    }

    try{
      Chord.fromString(" ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string:   (blank)", e.getMessage());
    }

    try{
      Chord.fromString("C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: C4 (missing brackets that enclose pitches)", e.getMessage());
    }

    try{
      Chord.fromString("[C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: [C4 (missing brackets that enclose pitches)", e.getMessage());
    }

    try{
      Chord.fromString("C4]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: C4] (missing brackets that enclose pitches)", e.getMessage());
    }

    try{
      Chord.fromString("[a[C4]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: [a[C4] (contains extra brackets)", e.getMessage());
    }

    try{
      Chord.fromString("[C4]a]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: [C4]a] (contains extra brackets)", e.getMessage());
    }

    try{
      Chord.fromString("a[C4]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: a[C4] (contains extra information)", e.getMessage());
    }

    try{
      Chord.fromString("[C4]a");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid chord string: [C4]a (contains extra information)", e.getMessage());
    }
  }

  @Test
  public void testBuilder(){
    Chord chord;

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.MAJOR)
      .build();
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("Ab4"))
      .add(Chord.Quality.MINOR)
      .add(Interval.fromString("M6"))
      .build();
    assertEquals("[Ab4(56), Cb5(59), Eb5(63), F5(65)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.MAJOR)
      .build();
    assertEquals("[C4(48), E4(52), G4(55)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.MAJOR)
      .setInversion(Inversion.SECOND)
      .build();
    assertEquals("[G4(55), C5(60), E5(64)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.DOMINANT_SEVENTH)
      .setBottomDegree(Degree.MEDIANT)
      .build();
    assertEquals("[E4(52), G4(55), Bb4(58), C5(60)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.DIMINISHED_SEVENTH)
      .setBottomDegree(Degree.MEDIANT)
      .build();
    assertEquals("[Eb4(51), Gb4(54), Bbb4(57), C5(60)]", chord.toString());

    chord = Chord
      .builder()
      .setRoot(Pitch.fromString("C4"))
      .add(Chord.Quality.MAJOR)
      .add(Interval.fromString("P4"))
      .setBottomDegree(Degree.SUBDOMINANT)
      .build();
    assertEquals("[F4(53), G4(55), C5(60), E5(64)]", chord.toString());

    try{
      Chord
        .builder()
        .setRoot(Pitch.fromString("C4"))
        .add(Interval.fromString("P1"))
        .add(Interval.fromString("P4"))
        .add(Interval.fromString("P5"))
        .setInversion(Inversion.FIRST)
        .build();
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Unable to invert chord: missing MEDIANT pitch", e.getMessage());
    }

    try{
      Chord
        .builder()
        .setRoot(Pitch.fromString("C4"))
        .add(Chord.Quality.MAJOR)
        .setInversion(Inversion.THIRD)
        .build();
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Unable to invert chord: missing LEADING_TONE pitch", e.getMessage());
    }

    try{
      Chord
        .builder()
        .setRoot(Pitch.fromString("C4"))
        .add(Chord.Quality.MAJOR)
        .setBottomDegree(Degree.SUBDOMINANT)
        .build();
      fail("Expected an expection.");
    }
    catch(Exception e){
      assertEquals("Unable to invert chord: missing SUBDOMINANT pitch", e.getMessage());
    }
  }
}
