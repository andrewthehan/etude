
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChordTest{

  @Test
  public void testConstructor(){
    Chord chord;

    Pitch[] pitches;

    pitches = new Pitch[]{Pitch.fromString("C4"), Pitch.fromString("E4"), Pitch.fromString("G4")};
    chord = new Chord(pitches);
    assertEquals(chord.toString(), "{C4(48),E4(52),G4(55)}");

    Pitch pitch = Pitch.fromString("C4");

    chord = new Chord(pitch, Chord.Quality.MAJOR);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55)}");
    chord = new Chord(pitch, Chord.Quality.MINOR);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55)}");
    chord = new Chord(pitch, Chord.Quality.DIMINISHED);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54)}");
    chord = new Chord(pitch, Chord.Quality.AUGMENTED);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),G#4(56)}");

    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bn4(59)}");
    chord = new Chord(pitch, Chord.Quality.MINOR_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55),Bb4(58)}");
    chord = new Chord(pitch, Chord.Quality.DOMINANT_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bb4(58)}");
    chord = new Chord(pitch, Chord.Quality.DIMINISHED_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54),Bbb4(57)}");
    chord = new Chord(pitch, Chord.Quality.HALF_DIMINISHED_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54),Bb4(58)}");
    chord = new Chord(pitch, Chord.Quality.MINOR_MAJOR_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55),Bn4(59)}");
    chord = new Chord(pitch, Chord.Quality.AUGMENTED_MAJOR_SEVENTH);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),G#4(56),Bn4(59)}");

    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.ROOT);
    assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bn4(59)}");
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.FIRST);
    assertEquals(chord.toString(), "{En4(52),Gn4(55),Bn4(59),Cn4(48)}");
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.SECOND);
    assertEquals(chord.toString(), "{Gn4(55),Bn4(59),Cn4(48),En4(52)}");
    chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.THIRD);
    assertEquals(chord.toString(), "{Bn4(59),Cn4(48),En4(52),Gn4(55)}");

    try{
      new Chord(pitch, Chord.Quality.MAJOR, Inversion.THIRD);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid inversion: THIRD (unable to invert chord with less than 4 pitches to its third inversion)");
    }
  }

  @Test
  public void testString(){
    Chord chord;

    chord = Chord.fromString("{C4}");
    assertEquals(chord.toString(), "{C4(48)}");

    chord = Chord.fromString("{C4,E4,G4}");
    assertEquals(chord.toString(), "{C4(48),E4(52),G4(55)}");

    try{
      Chord.fromString("C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: C4 (missing curly braces that enclose pitches)");
    }

    try{
      Chord.fromString("{C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: {C4 (missing curly braces that enclose pitches)");
    }

    try{
      Chord.fromString("C4}");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: C4} (missing curly braces that enclose pitches)");
    }

    try{
      Chord.fromString("{a{C4}");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: {a{C4} (contains extra curly braces)");
    }

    try{
      Chord.fromString("{C4}a}");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: {C4}a} (contains extra curly braces)");
    }

    try{
      Chord.fromString("a{C4}");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: a{C4} (contains extra information)");
    }

    try{
      Chord.fromString("{C4}a");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid chord string: {C4}a (contains extra information)");
    }
  }
}
