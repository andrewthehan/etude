
package tests;

import infinotes.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntervalTests{

	@Test
	public void testConstructor(){
		Interval interval;

		interval = new Interval(Quality.PERFECT, 1);
		assertEquals(interval.toString(), "P1");
		interval = new Interval(Quality.PERFECT, 5);
		assertEquals(interval.toString(), "P5");
		interval = new Interval(Quality.PERFECT, 8);
		assertEquals(interval.toString(), "P8");
		interval = new Interval(Quality.MAJOR, 3);
		assertEquals(interval.toString(), "M3");
		interval = new Interval(Quality.MINOR, 3);
		assertEquals(interval.toString(), "m3");
		interval = new Interval(Quality.AUGMENTED, 4);
		assertEquals(interval.toString(), "A4");
		interval = new Interval(Quality.DIMINISHED, 3);
		assertEquals(interval.toString(), "d3");
		interval = new Interval(Quality.DIMINISHED, 5);
		assertEquals(interval.toString(), "d5");

		try{
			new Interval(Quality.PERFECT, -5);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid interval: P-5 (number must be a positive integer)");
		}
		try{
			new Interval(Quality.PERFECT, 2);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid interval: P2 (number cannot have a perfect quality)");
		}
		try{
			new Interval(Quality.MAJOR, 5);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid interval: M5 (number cannot have major or minor quality)");
		}
		try{
			new Interval(Quality.MINOR, 5);
			fail("Expected an exception.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Invalid interval: m5 (number cannot have major or minor quality)");
		}
	}

	@Test
	public void testOffset(){
		Interval interval;

		interval = new Interval(Quality.PERFECT, 1);
		assertEquals(interval.getOffset(), 0);
		interval = new Interval(Quality.PERFECT, 5);
		assertEquals(interval.getOffset(), 7);
		interval = new Interval(Quality.PERFECT, 8);
		assertEquals(interval.getOffset(), 12);
		interval = new Interval(Quality.MAJOR, 3);
		assertEquals(interval.getOffset(), 4);
		interval = new Interval(Quality.MINOR, 3);
		assertEquals(interval.getOffset(), 3);
		interval = new Interval(Quality.AUGMENTED, 4);
		assertEquals(interval.getOffset(), 6);
		interval = new Interval(Quality.DIMINISHED, 3);
		assertEquals(interval.getOffset(), 2);
		interval = new Interval(Quality.DIMINISHED, 5);
		assertEquals(interval.getOffset(), 6);

		interval = new Interval(Quality.MAJOR, 10);
		assertEquals(interval.getOffset(), 16);
		interval = new Interval(Quality.PERFECT, 12);
		assertEquals(interval.getOffset(), 19);
	}

	@Test
	public void testPerfect(){
		assert(Interval.isPerfect(1));
		assert(!Interval.isPerfect(2));
		assert(!Interval.isPerfect(3));
		assert(Interval.isPerfect(4));
		assert(Interval.isPerfect(5));
		assert(!Interval.isPerfect(6));
		assert(!Interval.isPerfect(7));
		assert(Interval.isPerfect(8));
		assert(!Interval.isPerfect(9));
	}
}
