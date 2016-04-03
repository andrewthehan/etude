
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;

public class KeyTests{

	@Test
	public void testConstructor(){
		Key key;

		key = new Key(Letter.C);
		assertEquals(key.toString(), "C");
		key = new Key(Letter.G, Accidental.NONE);
		assertEquals(key.toString(), "G");
		key = new Key(Letter.D, Accidental.NATURAL);
		assertEquals(key.toString(), "Dn");
		key = new Key(Letter.A, Accidental.SHARP);
		assertEquals(key.toString(), "A#");
		key = new Key(Letter.E, Accidental.DOUBLE_SHARP);
		assertEquals(key.toString(), "Ex");
		key = new Key(Letter.B, Accidental.TRIPLE_SHARP);
		assertEquals(key.toString(), "B#x");
		key = new Key(Letter.F, Accidental.FLAT);
		assertEquals(key.toString(), "Fb");
		key = new Key(Letter.C, Accidental.DOUBLE_FLAT);
		assertEquals(key.toString(), "Cbb");
		key = new Key(Letter.G, Accidental.TRIPLE_FLAT);
		assertEquals(key.toString(), "Gbbb");
	}

	@Test
	public void testKeySignature(){
		KeySignature ks;
		Iterator<Letter> letters;

		ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Cn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Dn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "En");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Fn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Gn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "An");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Bn");

		ks = new KeySignature(Key.fromString("E"), Mode.MAJOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		assertEquals(new Key(letters.next()).apply(ks).toString(), "En");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "F#");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "G#");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "An");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Bn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "C#");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "D#");

		ks = new KeySignature(Key.fromString("Gb"), Mode.MAJOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Gb");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Ab");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Bb");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Cb");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Db");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Eb");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Fn");

		ks = new KeySignature(Key.fromString("A"), Mode.NATURAL_MINOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		assertEquals(new Key(letters.next()).apply(ks).toString(), "An");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Bn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Cn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Dn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "En");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Fn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Gn");

		ks = new KeySignature(Key.fromString("C"), Mode.HARMONIC_MINOR);
		letters = Letter.iterator(ks.getKey().getLetter());
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Cn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Dn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Eb");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Fn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Gn");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Ab");
		assertEquals(new Key(letters.next()).apply(ks).toString(), "Bn");
	}

	@Test
	public void testManipulations(){
		Key key;

		key = new Key(Letter.C);

		key = key.none();
		assertEquals(key.toString(), "C");
		assert(key.isNone());
		key = key.natural();
		assertEquals(key.toString(), "Cn");
		assert(key.isNatural());
		key = key.sharp();
		assertEquals(key.toString(), "C#");
		assert(key.isSharp());
		key = key.doubleSharp();
		assertEquals(key.toString(), "Cx");
		assert(key.isDoubleSharp());
		key = key.tripleSharp();
		assertEquals(key.toString(), "C#x");
		assert(key.isTripleSharp());
		key = key.flat();
		assertEquals(key.toString(), "Cb");
		assert(key.isFlat());
		key = key.doubleFlat();
		assertEquals(key.toString(), "Cbb");
		assert(key.isDoubleFlat());
		key = key.tripleFlat();
		assertEquals(key.toString(), "Cbbb");
		assert(key.isTripleFlat());
	}

	@Test
	public void testEnharmonic(){
		Key a, b;

		String[] keysA = {"Cn", "C#", "Dn", "D#", "En", "Fn", "F#", "Gn", "G#", "An", "A#", "Bn"};
		String[] keysB = {"Dbb", "Db", "Ebb", "Eb", "Fb", "Gbb", "Gb", "Abb", "Ab", "Bbb", "Bb", "Cb"};
		for(int i = 0; i < keysA.length; ++i){
			a = Key.fromString(keysA[i]);
			b = Key.fromString(keysB[i]);
			assert(Key.isEnharmonic(a, b));
		}
	}

	@Test
	public void testOffset(){
		Key key;

		String[] keysSharp = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		for(int i = 0; i < keysSharp.length; ++i){
			key = Key.fromOffset(i, Accidental.Policy.PRIORITIZE_SHARP);
			assertEquals(key.getOffset(), i);
			assertEquals(key.toString(), keysSharp[i]);
		}
		String[] keysFlat = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
		for(int i = 0; i < keysFlat.length; ++i){
			key = Key.fromOffset(i, Accidental.Policy.PRIORITIZE_FLAT);
			assertEquals(key.getOffset(), i);
			assertEquals(key.toString(), keysFlat[i]);
		}
	}

	@Test
	public void testString(){
		Key key;

		String[] keys = {"C", "Dn", "E#", "Fx", "G#x", "Ab", "Bbb", "Cbbb"};
		for(int i = 0; i < keys.length; ++i){
			key = Key.fromString(keys[i]);
			assertEquals(key.toString(), keys[i]);
		}

		try{
			Key.fromString(null);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid key string: null");
		}
		try{
			Key.fromString("");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid key string:  (blank)");
		}
		try{
			Key.fromString("  ");
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid key string:    (blank)");
		}
	}
}
