
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeSignatureTest {

  @Test
  public void testConstructor() {
    TimeSignature timeSignature;

    timeSignature = new TimeSignature(4, 1);
    assertEquals(Value.WHOLE, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 2);
    assertEquals(Value.HALF, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 4);
    assertEquals(Value.QUARTER, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 8);
    assertEquals(Value.EIGHTH, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 16);
    assertEquals(Value.SIXTEENTH, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 32);
    assertEquals(Value.THIRTY_SECOND, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 64);
    assertEquals(Value.SIXTY_FOURTH, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 128);
    assertEquals(Value.HUNDRED_TWENTY_EIGHTH, timeSignature.getOneBeat());

    timeSignature = new TimeSignature(4, 256);
    assertEquals(Value.TWO_HUNDRED_FIFTY_SIXTH, timeSignature.getOneBeat());
  }

  @Test
  public void testDurationOfMeasure() {
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
