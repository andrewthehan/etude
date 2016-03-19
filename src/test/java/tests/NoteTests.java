
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTests{

	@Test
	public void testConstructor(){
    Note note;

    note = new Note(Key.fromString("C"), 4);
    assertEquals(note.toString(), "C4(48)");
    note = new Note(Key.fromString("C"), 0);
    assertEquals(note.toString(), "C0(0)");
    note = new Note(Key.fromString("G"), 10);
    assertEquals(note.toString(), "G10(127)");
	}

	@Test
	public void testManipulations(){
		Note note;

		note = Note.fromProgramNumber(0);
		for(int i = 0; i < 127; i++){
			note = note.semitoneUp();
		}
		assertEquals(note.getProgramNumber(), 127);
		assertEquals(note.toString(), "G10(127)");
		for(int i = 0; i < 127; i++){
			note = note.semitoneDown();
		}
		assertEquals(note.getProgramNumber(), 0);
		assertEquals(note.toString(), "C0(0)");

		note = Note.fromString("Cbb4");
		note = note.semitoneUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cb4(47)");
		note = note.semitoneUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cn4(48)");
		note = note.semitoneUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "C#4(49)");
		note = note.semitoneUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cx4(50)");
		try{
			note = note.semitoneUp(Accidental.Policy.MAINTAIN_LETTER);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Can't move note semitone up while maintaining letter: Cx4(50)");
		}
		note = note.semitoneDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "C#4(49)");
		note = note.semitoneDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cn4(48)");
		note = note.semitoneDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cb4(47)");
		note = note.semitoneDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cbb4(46)");
		try{
			note = note.semitoneDown(Accidental.Policy.MAINTAIN_LETTER);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Can't move note semitone down while maintaining letter: Cbb4(46)");
		}

		String[] notesSharp = {"C4(48)", "C#4(49)", "D4(50)", "D#4(51)", "E4(52)", "F4(53)", "F#4(54)", "G4(55)", "G#4(56)", "A4(57)", "A#4(58)", "B4(59)", "C5(60)"};
		String[] notesFlat = {"C4(48)", "Db4(49)", "D4(50)", "Eb4(51)", "E4(52)", "F4(53)", "Gb4(54)", "G4(55)", "Ab4(56)", "A4(57)", "Bb4(58)", "B4(59)", "C5(60)"};

		note = Note.fromProgramNumber(47);
		for(int i = 0; i < notesSharp.length; ++i){
			note = note.semitoneUp(Accidental.Policy.PRIORITIZE_SHARP);
			assertEquals(note.toString(), notesSharp[i]);
		}
		note = note.semitoneUp();
		for(int i = notesSharp.length - 1; i >= 0; --i){
			note = note.semitoneDown(Accidental.Policy.PRIORITIZE_SHARP);
			assertEquals(note.toString(), notesSharp[i]);
		}

		note = Note.fromProgramNumber(47);
		for(int i = 0; i < notesFlat.length; ++i){
			note = note.semitoneUp(Accidental.Policy.PRIORITIZE_FLAT);
			assertEquals(note.toString(), notesFlat[i]);
		}
		note = note.semitoneUp();
		for(int i = notesFlat.length - 1; i >= 0; --i){
			note = note.semitoneDown(Accidental.Policy.PRIORITIZE_FLAT);
			assertEquals(note.toString(), notesFlat[i]);
		}
	}

  @Test
  public void testProgramNumber(){
    Note note;

    note = Note.fromProgramNumber(48);
    assertEquals(note.getProgramNumber(), 48);
    assertEquals(note.getOctave(), 4);
    assertEquals(note.toString(), "C4(48)");
    note = Note.fromProgramNumber(0);
    assertEquals(note.getProgramNumber(), 0);
    assertEquals(note.getOctave(), 0);
    assertEquals(note.toString(), "C0(0)");
    note = Note.fromProgramNumber(127);
    assertEquals(note.getProgramNumber(), 127);
    assertEquals(note.getOctave(), 10);
    assertEquals(note.toString(), "G10(127)");

		try{
			Note.fromProgramNumber(-1);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid program number: -1");
		}
		try{
			Note.fromProgramNumber(128);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid program number: 128");
		}
  }

	@Test
	public void testString(){
    Note note;

    note = Note.fromString("C4");
    assertEquals(note.getProgramNumber(), 48);
    assertEquals(note.getOctave(), 4);
    assertEquals(note.toString(), "C4(48)");
    note = Note.fromString("Cn4");
    assertEquals(note.getProgramNumber(), 48);
    assertEquals(note.getOctave(), 4);
    assertEquals(note.toString(), "Cn4(48)");
    note = Note.fromString("Cbb4");
    assertEquals(note.getProgramNumber(), 46);
    assertEquals(note.getOctave(), 4);
    assertEquals(note.toString(), "Cbb4(46)");
    note = Note.fromString("Cn4(48)");
    assertEquals(note.getProgramNumber(), 48);
    assertEquals(note.getOctave(), 4);
    assertEquals(note.toString(), "Cn4(48)");
		try{
			Note.fromString("A");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: A (missing octave)");
		}
		try{
			Note.fromString("C(48)");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: C(48) (missing octave)");
		}
		try{
			Note.fromString("Abbb4");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid accidental string: bbb");
		}
		try{
			Note.fromString("C4(49)");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: C4(49) (program number doesn't match key and octave)");
		}
		try{
			Note.fromString("A4B4");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: A4B4 (contains extra information)");
		}
		try{
			Note.fromString("C4(48");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: C4(48 (missing information)");
		}
		try{
			Note.fromString("C4(48))");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid note string: C4(48)) (contains extra information)");
		}
	}
}
