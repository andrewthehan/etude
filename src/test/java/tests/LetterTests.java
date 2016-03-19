
package tests;

import infinotes.theory.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LetterTests{

	@Test
	public void testOffset(){
		Letter letter;

		letter = Letter.A;
		assertEquals(letter.getOffset(), 9);
		letter = Letter.B;
		assertEquals(letter.getOffset(), 11);
		letter = Letter.C;
		assertEquals(letter.getOffset(), 0);
		letter = Letter.D;
		assertEquals(letter.getOffset(), 2);
		letter = Letter.E;
		assertEquals(letter.getOffset(), 4);
		letter = Letter.F;
		assertEquals(letter.getOffset(), 5);
		letter = Letter.G;
		assertEquals(letter.getOffset(), 7);
	}

	@Test
	public void testChar(){
		Letter letter;

		letter = Letter.fromChar('A');
		assertEquals(letter, Letter.A);
		assertEquals(letter.toString(), "A");

		letter = Letter.fromChar('B');
		assertEquals(letter, Letter.B);
		assertEquals(letter.toString(), "B");

		letter = Letter.fromChar('C');
		assertEquals(letter, Letter.C);
		assertEquals(letter.toString(), "C");

		letter = Letter.fromChar('D');
		assertEquals(letter, Letter.D);
		assertEquals(letter.toString(), "D");

		letter = Letter.fromChar('E');
		assertEquals(letter, Letter.E);
		assertEquals(letter.toString(), "E");

		letter = Letter.fromChar('F');
		assertEquals(letter, Letter.F);
		assertEquals(letter.toString(), "F");

		letter = Letter.fromChar('G');
		assertEquals(letter, Letter.G);
		assertEquals(letter.toString(), "G");
	}
}
