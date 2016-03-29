
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public void testKeySignature(){
		KeySignature ks;
		Iterator<Letter> letters;
		Note note;

		ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		note = new Note(ks.getKey(), 4);
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Cn5(60)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Dn4(50)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "En4(52)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Fn4(53)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Gn4(55)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "An4(57)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Bn4(59)");

		ks = new KeySignature(Key.fromString("A"), Mode.NATURAL_MINOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		note = new Note(ks.getKey(), 4);
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "An5(69)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Bn4(59)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Cn5(60)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Dn5(62)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "En5(64)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Fn5(65)");
		assertEquals(note.getHigherNote(new Key(letters.next())).apply(ks).toString(), "Gn5(67)");
	}

	@Test
	public void testSteps(){
		Note note;

		note = Note.fromProgramNumber(0);
		for(int i = 0; i < 127; i++){
			note = note.halfStepUp();
		}
		assertEquals(note.getProgramNumber(), 127);
		assertEquals(note.toString(), "G10(127)");
		for(int i = 0; i < 127; i++){
			note = note.halfStepDown();
		}
		assertEquals(note.getProgramNumber(), 0);
		assertEquals(note.toString(), "C0(0)");
		note = note.step(127);
		assertEquals(note.getProgramNumber(), 127);
		assertEquals(note.toString(), "G10(127)");
		note = note.step(-127);
		assertEquals(note.getProgramNumber(), 0);
		assertEquals(note.toString(), "C0(0)");

		note = Note.fromString("Cbb4");
		note = note.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cb4(47)");
		note = note.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cn4(48)");
		note = note.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "C#4(49)");
		note = note.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cx4(50)");
		try{
			note = note.halfStepUp(Accidental.Policy.MAINTAIN_LETTER);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Can't move note half step up while maintaining letter: Cx4(50)");
		}
		note = note.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "C#4(49)");
		note = note.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cn4(48)");
		note = note.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cb4(47)");
		note = note.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
		assertEquals(note.toString(), "Cbb4(46)");
		try{
			note = note.halfStepDown(Accidental.Policy.MAINTAIN_LETTER);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Can't move note half step down while maintaining letter: Cbb4(46)");
		}

		String[] notesSharp = {"C4(48)", "C#4(49)", "D4(50)", "D#4(51)", "E4(52)", "F4(53)", "F#4(54)", "G4(55)", "G#4(56)", "A4(57)", "A#4(58)", "B4(59)", "C5(60)"};
		String[] notesFlat = {"C4(48)", "Db4(49)", "D4(50)", "Eb4(51)", "E4(52)", "F4(53)", "Gb4(54)", "G4(55)", "Ab4(56)", "A4(57)", "Bb4(58)", "B4(59)", "C5(60)"};

		note = Note.fromProgramNumber(47);
		for(int i = 0; i < notesSharp.length; ++i){
			note = note.halfStepUp(Accidental.Policy.PRIORITIZE_SHARP);
			assertEquals(note.toString(), notesSharp[i]);
		}
		note = note.halfStepUp();
		for(int i = notesSharp.length - 1; i >= 0; --i){
			note = note.halfStepDown(Accidental.Policy.PRIORITIZE_SHARP);
			assertEquals(note.toString(), notesSharp[i]);
		}

		note = Note.fromProgramNumber(47);
		for(int i = 0; i < notesFlat.length; ++i){
			note = note.halfStepUp(Accidental.Policy.PRIORITIZE_FLAT);
			assertEquals(note.toString(), notesFlat[i]);
		}
		note = note.halfStepUp();
		for(int i = notesFlat.length - 1; i >= 0; --i){
			note = note.halfStepDown(Accidental.Policy.PRIORITIZE_FLAT);
			assertEquals(note.toString(), notesFlat[i]);
		}
	}

	@Test
	public void testInterval(){
		Note note;
		Interval interval;

		note = Note.fromString("C4");

		interval = new Interval(Interval.Quality.DIMINISHED, 1);
		assertEquals(note.step(interval).toString(), "Cb4(47)");
		interval = new Interval(Interval.Quality.PERFECT, 1);
		assertEquals(note.step(interval).toString(), "Cn4(48)");
		interval = new Interval(Interval.Quality.AUGMENTED, 1);
		assertEquals(note.step(interval).toString(), "C#4(49)");

		interval = new Interval(Interval.Quality.DIMINISHED, 2);
		assertEquals(note.step(interval).toString(), "Dbb4(48)");
		interval = new Interval(Interval.Quality.MINOR, 2);
		assertEquals(note.step(interval).toString(), "Db4(49)");
		interval = new Interval(Interval.Quality.MAJOR, 2);
		assertEquals(note.step(interval).toString(), "Dn4(50)");
		interval = new Interval(Interval.Quality.AUGMENTED, 2);
		assertEquals(note.step(interval).toString(), "D#4(51)");

		interval = new Interval(Interval.Quality.DIMINISHED, 3);
		assertEquals(note.step(interval).toString(), "Ebb4(50)");
		interval = new Interval(Interval.Quality.MINOR, 3);
		assertEquals(note.step(interval).toString(), "Eb4(51)");
		interval = new Interval(Interval.Quality.MAJOR, 3);
		assertEquals(note.step(interval).toString(), "En4(52)");
		interval = new Interval(Interval.Quality.AUGMENTED, 3);
		assertEquals(note.step(interval).toString(), "E#4(53)");

		interval = new Interval(Interval.Quality.DIMINISHED, 4);
		assertEquals(note.step(interval).toString(), "Fb4(52)");
		interval = new Interval(Interval.Quality.PERFECT, 4);
		assertEquals(note.step(interval).toString(), "Fn4(53)");
		interval = new Interval(Interval.Quality.AUGMENTED, 4);
		assertEquals(note.step(interval).toString(), "F#4(54)");

		interval = new Interval(Interval.Quality.DIMINISHED, 5);
		assertEquals(note.step(interval).toString(), "Gb4(54)");
		interval = new Interval(Interval.Quality.PERFECT, 5);
		assertEquals(note.step(interval).toString(), "Gn4(55)");
		interval = new Interval(Interval.Quality.AUGMENTED, 5);
		assertEquals(note.step(interval).toString(), "G#4(56)");

		interval = new Interval(Interval.Quality.DIMINISHED, 6);
		assertEquals(note.step(interval).toString(), "Abb4(55)");
		interval = new Interval(Interval.Quality.MINOR, 6);
		assertEquals(note.step(interval).toString(), "Ab4(56)");
		interval = new Interval(Interval.Quality.MAJOR, 6);
		assertEquals(note.step(interval).toString(), "An4(57)");
		interval = new Interval(Interval.Quality.AUGMENTED, 6);
		assertEquals(note.step(interval).toString(), "A#4(58)");

		interval = new Interval(Interval.Quality.DIMINISHED, 7);
		assertEquals(note.step(interval).toString(), "Bbb4(57)");
		interval = new Interval(Interval.Quality.MINOR, 7);
		assertEquals(note.step(interval).toString(), "Bb4(58)");
		interval = new Interval(Interval.Quality.MAJOR, 7);
		assertEquals(note.step(interval).toString(), "Bn4(59)");
		interval = new Interval(Interval.Quality.AUGMENTED, 7);
		assertEquals(note.step(interval).toString(), "B#4(60)");

		interval = new Interval(Interval.Quality.DIMINISHED, 8);
		assertEquals(note.step(interval).toString(), "Cb5(59)");
		interval = new Interval(Interval.Quality.PERFECT, 8);
		assertEquals(note.step(interval).toString(), "Cn5(60)");
		interval = new Interval(Interval.Quality.AUGMENTED, 8);
		assertEquals(note.step(interval).toString(), "C#5(61)");

		interval = new Interval(Interval.Quality.DIMINISHED, 9);
		assertEquals(note.step(interval).toString(), "Dbb5(60)");
		interval = new Interval(Interval.Quality.MINOR, 9);
		assertEquals(note.step(interval).toString(), "Db5(61)");
		interval = new Interval(Interval.Quality.MAJOR, 9);
		assertEquals(note.step(interval).toString(), "Dn5(62)");
		interval = new Interval(Interval.Quality.AUGMENTED, 9);
		assertEquals(note.step(interval).toString(), "D#5(63)");
	}

	@Test
	public void testHigherLower(){
		Note note;

		note = Note.fromString("C4");

		assert(note.isHigherThan(Note.fromString("C3")));
		assert(note.isHigherThan(Note.fromString("Cb4")));
		assert(note.isHigherThan(Note.fromString("B3")));
		assert(note.isLowerThan(Note.fromString("C5")));
		assert(note.isLowerThan(Note.fromString("C#4")));
		assert(note.isLowerThan(Note.fromString("Db4")));

		assertEquals(note.getHigherNote(Key.fromString("D")).toString(), "D4(50)");
		assertEquals(note.getLowerNote(Key.fromString("D")).toString(), "D3(38)");
		assertEquals(note.getHigherNote(Key.fromString("C")).toString(), "C5(60)");
		assertEquals(note.getLowerNote(Key.fromString("C")).toString(), "C3(36)");

		String sorted = Stream
			.of("C4", "G4", "D4", "A4", "E4", "B4", "F4")
			.map(Note::fromString)
			.sorted()
			.map(Note::toString)
			.collect(Collectors.joining(" "));
		assertEquals(sorted, "C4(48) D4(50) E4(52) F4(53) G4(55) A4(57) B4(59)");
	}

	@Test
	public void testEnharmonic(){
		Note a, b;

		String[] notesA = {"Cn4", "C#4", "Dn4", "D#4", "En4", "Fn4", "F#4", "Gn4", "G#4", "An4", "A#4", "Bn4"};
		String[] notesB = {"Dbb4", "Db4", "Ebb4", "Eb4", "Fb4", "Gbb4", "Gb4", "Abb4", "Ab4", "Bbb4", "Bb4", "Cb5"};
		for(int i = 0; i < notesA.length; ++i){
			a = Note.fromString(notesA[i]);
			b = Note.fromString(notesB[i]);
			assert(Note.isEnharmonic(a, b));
		}

		a = Note.fromString("C4");
		b = Note.fromString("C4");
		assert(Note.isEnharmonic(a, b));

		a = Note.fromString("C4");
		b = Note.fromString("C5");
		assert(!Note.isEnharmonic(a, b));

		a = Note.fromString("C#4");
		b = Note.fromString("Db5");
		assert(!Note.isEnharmonic(a, b));
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
		note = Note.fromString("C#4");
		assertEquals(note.getProgramNumber(), 49);
		assertEquals(note.getOctave(), 4);
		assertEquals(note.toString(), "C#4(49)");
		note = Note.fromString("Cx4");
		assertEquals(note.getProgramNumber(), 50);
		assertEquals(note.getOctave(), 4);
		assertEquals(note.toString(), "Cx4(50)");
		note = Note.fromString("Cb4");
		assertEquals(note.getProgramNumber(), 47);
		assertEquals(note.getOctave(), 4);
		assertEquals(note.toString(), "Cb4(47)");
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
