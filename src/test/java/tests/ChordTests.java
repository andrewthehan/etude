
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChordTests{

	@Test
	public void testConstructor(){
		Chord chord;

		Note note = Note.fromString("C4");

		chord = new Chord(note, Chord.Quality.MAJOR_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),En4(52),Gn4(55),Bn4(59)]");
		chord = new Chord(note, Chord.Quality.MINOR_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),Eb4(51),Gn4(55),Bb4(58)]");
		chord = new Chord(note, Chord.Quality.DOMINANT_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),En4(52),Gn4(55),Bb4(58)]");
		chord = new Chord(note, Chord.Quality.DIMINISHED_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),Eb4(51),Gb4(54),Bbb4(57)]");
		chord = new Chord(note, Chord.Quality.HALF_DIMINISHED_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),Eb4(51),Gb4(54),Bb4(58)]");
		chord = new Chord(note, Chord.Quality.MINOR_MAJOR_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),Eb4(51),Gn4(55),Bn4(59)]");
		chord = new Chord(note, Chord.Quality.AUGMENTED_MAJOR_SEVENTH);
		assertEquals(chord.toString(), "[Cn4(48),En4(52),G#4(56),Bn4(59)]");

		chord = new Chord(note, Chord.Quality.MAJOR_SEVENTH, Inversion.ROOT);
		assertEquals(chord.toString(), "[Cn4(48),En4(52),Gn4(55),Bn4(59)]");
		chord = new Chord(note, Chord.Quality.MAJOR_SEVENTH, Inversion.FIRST);
		assertEquals(chord.toString(), "[En4(52),Gn4(55),Bn4(59),Cn4(48)]");
		chord = new Chord(note, Chord.Quality.MAJOR_SEVENTH, Inversion.SECOND);
		assertEquals(chord.toString(), "[Gn4(55),Bn4(59),Cn4(48),En4(52)]");
		chord = new Chord(note, Chord.Quality.MAJOR_SEVENTH, Inversion.THIRD);
		assertEquals(chord.toString(), "[Bn4(59),Cn4(48),En4(52),Gn4(55)]");

		try{
			new Chord(note, Chord.Quality.MAJOR, Inversion.THIRD);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid inversion: THIRD (unable to invert chord with less than 4 notes to its third inversion)");
		}
	}
}
