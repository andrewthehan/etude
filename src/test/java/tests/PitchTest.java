
package tests;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PitchTest{

  @Test
  public void testConstructor(){
    Pitch pitch;

    pitch = new Pitch(Key.fromString("C"), 4);
    assertEquals(pitch.toString(), "C4(48)");
    pitch = new Pitch(Key.fromString("C"), 0);
    assertEquals(pitch.toString(), "C0(0)");
    pitch = new Pitch(Key.fromString("G"), 10);
    assertEquals(pitch.toString(), "G10(127)");

    try{
      pitch = new Pitch(Key.fromString("G#"), 10);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid program number: 128");
    }

    try{
      pitch = new Pitch(Key.fromString("Cb"), 0);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid program number: -1");
    }
  }

  @Test
  public void testKeySignature(){
    KeySignature ks;
    Iterator<Letter> letters;
    Pitch pitch;

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    pitch = new Pitch(ks.getKey(), 4);
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Cn5(60)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Dn4(50)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "En4(52)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Fn4(53)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Gn4(55)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "An4(57)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Bn4(59)");

    ks = new KeySignature(Key.fromString("A"), Mode.NATURAL_MINOR);
    letters = Letter.iterator(ks.getKey().getLetter());
    pitch = new Pitch(ks.getKey(), 4);
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "An5(69)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Bn4(59)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Cn5(60)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Dn5(62)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "En5(64)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Fn5(65)");
    assertEquals(pitch.getHigherPitch(new Key(letters.next())).apply(ks).toString(), "Gn5(67)");
  }

  @Test
  public void testSteps(){
    Pitch pitch;

    pitch = Pitch.fromProgramNumber(0);
    for(int i = 0; i < 127; i++){
      pitch = pitch.halfStepUp();
    }
    assertEquals(pitch.getProgramNumber(), 127);
    assertEquals(pitch.toString(), "G10(127)");
    for(int i = 0; i < 127; i++){
      pitch = pitch.halfStepDown();
    }
    assertEquals(pitch.getProgramNumber(), 0);
    assertEquals(pitch.toString(), "C0(0)");
    pitch = pitch.step(127);
    assertEquals(pitch.getProgramNumber(), 127);
    assertEquals(pitch.toString(), "G10(127)");
    pitch = pitch.step(-127);
    assertEquals(pitch.getProgramNumber(), 0);
    assertEquals(pitch.toString(), "C0(0)");

    pitch = Pitch.fromString("Cbbb4");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cbb4(46)");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cb4(47)");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cn4(48)");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "C#4(49)");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cx4(50)");
    pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "C#x4(51)");
    try{
      pitch = pitch.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Can't move pitch half step up while maintaining letter: C#x4(51)");
    }
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cx4(50)");
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "C#4(49)");
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cn4(48)");
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cb4(47)");
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cbb4(46)");
    pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
    assertEquals(pitch.toString(), "Cbbb4(45)");
    try{
      pitch = pitch.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Can't move pitch half step down while maintaining letter: Cbbb4(45)");
    }

    String[] pitchesSharp = {"C4(48)", "C#4(49)", "D4(50)", "D#4(51)", "E4(52)", "F4(53)", "F#4(54)", "G4(55)", "G#4(56)", "A4(57)", "A#4(58)", "B4(59)", "C5(60)"};
    String[] pitchesFlat = {"C4(48)", "Db4(49)", "D4(50)", "Eb4(51)", "E4(52)", "F4(53)", "Gb4(54)", "G4(55)", "Ab4(56)", "A4(57)", "Bb4(58)", "B4(59)", "C5(60)"};

    pitch = Pitch.fromProgramNumber(47);
    for(int i = 0; i < pitchesSharp.length; ++i){
      pitch = pitch.halfStepUp(Accidental.Policy.PRIORITIZE_SHARP);
      assertEquals(pitch.toString(), pitchesSharp[i]);
    }
    pitch = pitch.halfStepUp();
    for(int i = pitchesSharp.length - 1; i >= 0; --i){
      pitch = pitch.halfStepDown(Accidental.Policy.PRIORITIZE_SHARP);
      assertEquals(pitch.toString(), pitchesSharp[i]);
    }

    pitch = Pitch.fromProgramNumber(47);
    for(int i = 0; i < pitchesFlat.length; ++i){
      pitch = pitch.halfStepUp(Accidental.Policy.PRIORITIZE_FLAT);
      assertEquals(pitch.toString(), pitchesFlat[i]);
    }
    pitch = pitch.halfStepUp();
    for(int i = pitchesFlat.length - 1; i >= 0; --i){
      pitch = pitch.halfStepDown(Accidental.Policy.PRIORITIZE_FLAT);
      assertEquals(pitch.toString(), pitchesFlat[i]);
    }
  }

  @Test
  public void testInterval(){
    Pitch pitch;
    Interval interval;

    pitch = Pitch.fromString("C4");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 1);
    assertEquals(pitch.step(interval).toString(), "Cbb4(46)");
    interval = new Interval(Interval.Quality.DIMINISHED, 1);
    assertEquals(pitch.step(interval).toString(), "Cb4(47)");
    interval = new Interval(Interval.Quality.PERFECT, 1);
    assertEquals(pitch.step(interval).toString(), "Cn4(48)");
    interval = new Interval(Interval.Quality.AUGMENTED, 1);
    assertEquals(pitch.step(interval).toString(), "C#4(49)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 1);
    assertEquals(pitch.step(interval).toString(), "Cx4(50)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 2);
    assertEquals(pitch.step(interval).toString(), "Dbbb4(47)");
    interval = new Interval(Interval.Quality.DIMINISHED, 2);
    assertEquals(pitch.step(interval).toString(), "Dbb4(48)");
    interval = new Interval(Interval.Quality.MINOR, 2);
    assertEquals(pitch.step(interval).toString(), "Db4(49)");
    interval = new Interval(Interval.Quality.MAJOR, 2);
    assertEquals(pitch.step(interval).toString(), "Dn4(50)");
    interval = new Interval(Interval.Quality.AUGMENTED, 2);
    assertEquals(pitch.step(interval).toString(), "D#4(51)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 2);
    assertEquals(pitch.step(interval).toString(), "Dx4(52)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 3);
    assertEquals(pitch.step(interval).toString(), "Ebbb4(49)");
    interval = new Interval(Interval.Quality.DIMINISHED, 3);
    assertEquals(pitch.step(interval).toString(), "Ebb4(50)");
    interval = new Interval(Interval.Quality.MINOR, 3);
    assertEquals(pitch.step(interval).toString(), "Eb4(51)");
    interval = new Interval(Interval.Quality.MAJOR, 3);
    assertEquals(pitch.step(interval).toString(), "En4(52)");
    interval = new Interval(Interval.Quality.AUGMENTED, 3);
    assertEquals(pitch.step(interval).toString(), "E#4(53)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 3);
    assertEquals(pitch.step(interval).toString(), "Ex4(54)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 4);
    assertEquals(pitch.step(interval).toString(), "Fbb4(51)");
    interval = new Interval(Interval.Quality.DIMINISHED, 4);
    assertEquals(pitch.step(interval).toString(), "Fb4(52)");
    interval = new Interval(Interval.Quality.PERFECT, 4);
    assertEquals(pitch.step(interval).toString(), "Fn4(53)");
    interval = new Interval(Interval.Quality.AUGMENTED, 4);
    assertEquals(pitch.step(interval).toString(), "F#4(54)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 4);
    assertEquals(pitch.step(interval).toString(), "Fx4(55)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 5);
    assertEquals(pitch.step(interval).toString(), "Gbb4(53)");
    interval = new Interval(Interval.Quality.DIMINISHED, 5);
    assertEquals(pitch.step(interval).toString(), "Gb4(54)");
    interval = new Interval(Interval.Quality.PERFECT, 5);
    assertEquals(pitch.step(interval).toString(), "Gn4(55)");
    interval = new Interval(Interval.Quality.AUGMENTED, 5);
    assertEquals(pitch.step(interval).toString(), "G#4(56)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 5);
    assertEquals(pitch.step(interval).toString(), "Gx4(57)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 6);
    assertEquals(pitch.step(interval).toString(), "Abbb4(54)");
    interval = new Interval(Interval.Quality.DIMINISHED, 6);
    assertEquals(pitch.step(interval).toString(), "Abb4(55)");
    interval = new Interval(Interval.Quality.MINOR, 6);
    assertEquals(pitch.step(interval).toString(), "Ab4(56)");
    interval = new Interval(Interval.Quality.MAJOR, 6);
    assertEquals(pitch.step(interval).toString(), "An4(57)");
    interval = new Interval(Interval.Quality.AUGMENTED, 6);
    assertEquals(pitch.step(interval).toString(), "A#4(58)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 6);
    assertEquals(pitch.step(interval).toString(), "Ax4(59)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 7);
    assertEquals(pitch.step(interval).toString(), "Bbbb4(56)");
    interval = new Interval(Interval.Quality.DIMINISHED, 7);
    assertEquals(pitch.step(interval).toString(), "Bbb4(57)");
    interval = new Interval(Interval.Quality.MINOR, 7);
    assertEquals(pitch.step(interval).toString(), "Bb4(58)");
    interval = new Interval(Interval.Quality.MAJOR, 7);
    assertEquals(pitch.step(interval).toString(), "Bn4(59)");
    interval = new Interval(Interval.Quality.AUGMENTED, 7);
    assertEquals(pitch.step(interval).toString(), "B#4(60)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 7);
    assertEquals(pitch.step(interval).toString(), "Bx4(61)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 8);
    assertEquals(pitch.step(interval).toString(), "Cbb5(58)");
    interval = new Interval(Interval.Quality.DIMINISHED, 8);
    assertEquals(pitch.step(interval).toString(), "Cb5(59)");
    interval = new Interval(Interval.Quality.PERFECT, 8);
    assertEquals(pitch.step(interval).toString(), "Cn5(60)");
    interval = new Interval(Interval.Quality.AUGMENTED, 8);
    assertEquals(pitch.step(interval).toString(), "C#5(61)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 8);
    assertEquals(pitch.step(interval).toString(), "Cx5(62)");

    interval = new Interval(Interval.Quality.DOUBLY_DIMINISHED, 9);
    assertEquals(pitch.step(interval).toString(), "Dbbb5(59)");
    interval = new Interval(Interval.Quality.DIMINISHED, 9);
    assertEquals(pitch.step(interval).toString(), "Dbb5(60)");
    interval = new Interval(Interval.Quality.MINOR, 9);
    assertEquals(pitch.step(interval).toString(), "Db5(61)");
    interval = new Interval(Interval.Quality.MAJOR, 9);
    assertEquals(pitch.step(interval).toString(), "Dn5(62)");
    interval = new Interval(Interval.Quality.AUGMENTED, 9);
    assertEquals(pitch.step(interval).toString(), "D#5(63)");
    interval = new Interval(Interval.Quality.DOUBLY_AUGMENTED, 9);
    assertEquals(pitch.step(interval).toString(), "Dx5(64)");
  }

  @Test
  public void testHigherLower(){
    Pitch pitch;

    pitch = Pitch.fromString("C4");

    assert(pitch.isHigherThan(Pitch.fromString("C3")));
    assert(pitch.isHigherThan(Pitch.fromString("Cb4")));
    assert(pitch.isHigherThan(Pitch.fromString("B3")));
    assert(pitch.isHigherThan(Pitch.fromString("Dbbb4")));
    assert(pitch.isLowerThan(Pitch.fromString("C5")));
    assert(pitch.isLowerThan(Pitch.fromString("C#4")));
    assert(pitch.isLowerThan(Pitch.fromString("Db4")));
    assert(pitch.isLowerThan(Pitch.fromString("B#x3")));

    assertEquals(pitch.getHigherPitch(Key.fromString("D")).toString(), "D4(50)");
    assertEquals(pitch.getLowerPitch(Key.fromString("D")).toString(), "D3(38)");
    assertEquals(pitch.getHigherPitch(Key.fromString("C")).toString(), "C5(60)");
    assertEquals(pitch.getLowerPitch(Key.fromString("C")).toString(), "C3(36)");

    String sorted = Stream
      .of("C4", "G4", "D4", "A4", "E4", "B4", "F4")
      .map(Pitch::fromString)
      .sorted()
      .map(Pitch::toString)
      .collect(Collectors.joining(" "));
    assertEquals(sorted, "C4(48) D4(50) E4(52) F4(53) G4(55) A4(57) B4(59)");
  }

  @Test
  public void testEnharmonic(){
    Pitch a, b;

    String[] pitchesA = {"Cn4", "C#4", "Dn4", "D#4", "En4", "Fn4", "F#4", "Gn4", "G#4", "An4", "A#4", "Bn4"};
    String[] pitchesB = {"Dbb4", "Db4", "Ebb4", "Eb4", "Fb4", "Gbb4", "Gb4", "Abb4", "Ab4", "Bbb4", "Bb4", "Cb5"};
    for(int i = 0; i < pitchesA.length; ++i){
      a = Pitch.fromString(pitchesA[i]);
      b = Pitch.fromString(pitchesB[i]);
      assert(Pitch.isEnharmonic(a, b));
    }

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C4");
    assert(Pitch.isEnharmonic(a, b));

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C5");
    assert(!Pitch.isEnharmonic(a, b));

    a = Pitch.fromString("C#4");
    b = Pitch.fromString("Db5");
    assert(!Pitch.isEnharmonic(a, b));
  }

  @Test
  public void testProgramNumber(){
    Pitch pitch;

    pitch = Pitch.fromProgramNumber(48);
    assertEquals(pitch.getProgramNumber(), 48);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "C4(48)");
    pitch = Pitch.fromProgramNumber(0);
    assertEquals(pitch.getProgramNumber(), 0);
    assertEquals(pitch.getOctave(), 0);
    assertEquals(pitch.toString(), "C0(0)");
    pitch = Pitch.fromProgramNumber(127);
    assertEquals(pitch.getProgramNumber(), 127);
    assertEquals(pitch.getOctave(), 10);
    assertEquals(pitch.toString(), "G10(127)");

    try{
      Pitch.fromProgramNumber(-1);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid program number: -1");
    }
    try{
      Pitch.fromProgramNumber(128);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid program number: 128");
    }
  }

  @Test
  public void testString(){
    Pitch pitch;

    pitch = Pitch.fromString("C4");
    assertEquals(pitch.getProgramNumber(), 48);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "C4(48)");
    pitch = Pitch.fromString("Cn4");
    assertEquals(pitch.getProgramNumber(), 48);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cn4(48)");
    pitch = Pitch.fromString("C#4");
    assertEquals(pitch.getProgramNumber(), 49);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "C#4(49)");
    pitch = Pitch.fromString("Cx4");
    assertEquals(pitch.getProgramNumber(), 50);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cx4(50)");
    pitch = Pitch.fromString("C#x4");
    assertEquals(pitch.getProgramNumber(), 51);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "C#x4(51)");
    pitch = Pitch.fromString("Cb4");
    assertEquals(pitch.getProgramNumber(), 47);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cb4(47)");
    pitch = Pitch.fromString("Cbb4");
    assertEquals(pitch.getProgramNumber(), 46);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cbb4(46)");
    pitch = Pitch.fromString("Cbbb4");
    assertEquals(pitch.getProgramNumber(), 45);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cbbb4(45)");
    pitch = Pitch.fromString("Cn4(48)");
    assertEquals(pitch.getProgramNumber(), 48);
    assertEquals(pitch.getOctave(), 4);
    assertEquals(pitch.toString(), "Cn4(48)");
    try{
      Pitch.fromString("A");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: A (missing octave)");
    }
    try{
      Pitch.fromString("C(48)");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: C(48) (missing octave)");
    }
    try{
      Pitch.fromString("Abbbb4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid accidental string: bbbb");
    }
    try{
      Pitch.fromString("C4(49)");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: C4(49) (program number doesn't match key and octave)");
    }
    try{
      Pitch.fromString("A4B4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: A4B4 (contains extra information)");
    }
    try{
      Pitch.fromString("C4(48");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: C4(48 (missing information)");
    }
    try{
      Pitch.fromString("C4(48))");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: C4(48)) (contains extra information)");
    }
    try{
      Pitch.fromString(null);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string: null");
    }
    try{
      Pitch.fromString("");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string:  (blank)");
    }
    try{
      Pitch.fromString("  ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid pitch string:    (blank)");
    }
  }

  @Test
  public void testHashCodeAndEquals(){
    Pitch a, b;

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C4");
    assert(a.equals(b));
    assert(a.hashCode() == b.hashCode());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("D4");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Pitch.fromString("C#4");
    b = Pitch.fromString("Db4");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Pitch.fromString("C4");
    b = Pitch.fromString("C5");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Pitch.fromString("C4");
    b = a.step(1).step(-1);
    assert(a.equals(b));
    assert(b.hashCode() == b.hashCode());
  }
}
