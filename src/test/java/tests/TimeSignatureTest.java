
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class TimeSignatureTest{

	@Test
	public void testConstructor(){
		TimeSignature timeSignature;

		timeSignature = new TimeSignature(4, 1);
		assertEquals(timeSignature.getOneBeat(), Value.WHOLE);

		timeSignature = new TimeSignature(4, 2);
		assertEquals(timeSignature.getOneBeat(), Value.HALF);

		timeSignature = new TimeSignature(4, 4);
		assertEquals(timeSignature.getOneBeat(), Value.QUARTER);

		timeSignature = new TimeSignature(4, 8);
		assertEquals(timeSignature.getOneBeat(), Value.EIGHTH);

		timeSignature = new TimeSignature(4, 16);
		assertEquals(timeSignature.getOneBeat(), Value.SIXTEENTH);

		timeSignature = new TimeSignature(4, 32);
		assertEquals(timeSignature.getOneBeat(), Value.THIRTY_SECOND);

		timeSignature = new TimeSignature(4, 64);
		assertEquals(timeSignature.getOneBeat(), Value.SIXTY_FOURTH);

		timeSignature = new TimeSignature(4, 128);
		assertEquals(timeSignature.getOneBeat(), Value.HUNDRED_TWENTY_EIGHTH);

		timeSignature = new TimeSignature(4, 256);
		assertEquals(timeSignature.getOneBeat(), Value.TWO_HUNDRED_FIFTY_SIXTH);
	}

	@Test
	public void testDurationOfMeasure(){
		TimeSignature timeSignature;

		timeSignature = new TimeSignature(4, 4);
		assertEquals(timeSignature.getDurationOfMeasure(), 4 * 0.25, 0);

		timeSignature = new TimeSignature(2, 4);
		assertEquals(timeSignature.getDurationOfMeasure(), 2 * 0.25, 0);

		timeSignature = new TimeSignature(3, 4);
		assertEquals(timeSignature.getDurationOfMeasure(), 3 * 0.25, 0);

		timeSignature = new TimeSignature(6, 8);
		assertEquals(timeSignature.getDurationOfMeasure(), 6 * 0.125, 0);

	}
}
