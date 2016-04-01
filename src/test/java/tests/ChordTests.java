
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChordTests{

	@Test
	public void testConstructor(){
		Chord chord;

		Pitch pitch = Pitch.fromString("C4");

		chord = new Chord(pitch, Chord.Quality.MAJOR, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MINOR, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.DIMINISHED, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.AUGMENTED, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),G#4(56)}[1.0]");

		chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bn4(59)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MINOR_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55),Bb4(58)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.DOMINANT_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bb4(58)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.DIMINISHED_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54),Bbb4(57)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.HALF_DIMINISHED_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gb4(54),Bb4(58)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MINOR_MAJOR_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),Eb4(51),Gn4(55),Bn4(59)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.AUGMENTED_MAJOR_SEVENTH, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),G#4(56),Bn4(59)}[1.0]");

		chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.ROOT, Value.WHOLE);
		assertEquals(chord.toString(), "{Cn4(48),En4(52),Gn4(55),Bn4(59)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.FIRST, Value.WHOLE);
		assertEquals(chord.toString(), "{En4(52),Gn4(55),Bn4(59),Cn4(48)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.SECOND, Value.WHOLE);
		assertEquals(chord.toString(), "{Gn4(55),Bn4(59),Cn4(48),En4(52)}[1.0]");
		chord = new Chord(pitch, Chord.Quality.MAJOR_SEVENTH, Inversion.THIRD, Value.WHOLE);
		assertEquals(chord.toString(), "{Bn4(59),Cn4(48),En4(52),Gn4(55)}[1.0]");

		try{
			new Chord(pitch, Chord.Quality.MAJOR, Inversion.THIRD, Value.WHOLE);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid inversion: THIRD (unable to invert chord with less than 4 pitches to its third inversion)");
		}
	}

	@Test
	public void testString(){
		Chord chord;

		chord = Chord.fromString("{C4}[1]");
		assertEquals(chord.toString(), "{C4(48)}[1.0]");

		chord = Chord.fromString("{C4,E4,G4}[1]");
		assertEquals(chord.toString(), "{C4(48),E4(52),G4(55)}[1.0]");

		try{
			Chord.fromString("C4[1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: C4[1] (missing curly braces that enclose pitches)");
		}

		try{
			Chord.fromString("{C4[1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4[1] (missing curly braces that enclose pitches)");
		}

		try{
			Chord.fromString("C4}[1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: C4}[1] (missing curly braces that enclose pitches)");
		}

		try{
			Chord.fromString("{C4}1");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4}1 (missing brackets that enclose value)");
		}

		try{
			Chord.fromString("{C4}[1");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4}[1 (missing brackets that enclose value)");
		}

		try{
			Chord.fromString("{C4}1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4}1] (missing brackets that enclose value)");
		}

		try{
			Chord.fromString("{C4}a[1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4}a[1] (contains extra information)");
		}

		try{
			Chord.fromString("a{C4}[1]");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: a{C4}[1] (contains extra information)");
		}

		try{
			Chord.fromString("{C4}[1]a");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid chord string: {C4}[1]a (contains extra information)");
		}
	}
}
