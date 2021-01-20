
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class ValueTest {

  @Test
  public void testConstructor() {
    Value value;

    value = new Value(Value.Type.QUARTER);
    assertEquals(Value.QUARTER, value);
    assertEquals("QUARTER(0.25)", value.toString());

    value = new Value(Value.Type.QUARTER, 0);
    assertEquals(Value.QUARTER, value);
    assertEquals("QUARTER(0.25)", value.toString());

    value = new Value(Value.Type.QUARTER, 1);
    assertEquals(Value.QUARTER.dotted(), value);
    assertEquals("QUARTER.(0.375)", value.toString());

    value = new Value(Value.Type.QUARTER, 0, 0.25);
    assertEquals(Value.QUARTER, value);
    assertEquals("QUARTER(0.25)", value.toString());

    value = new Value(Value.Type.HALF, 1);
    assertEquals(Value.HALF.dotted(), value);
    assertEquals("HALF.(0.75)", value.toString());

    value = new Value(Value.Type.HALF, 1, 0.75);
    assertEquals(Value.HALF.dotted(), value);
    assertEquals("HALF.(0.75)", value.toString());

    value = new Value(Value.Type.WHOLE, 0);
    assertEquals(Value.WHOLE, value);
    assertEquals("WHOLE(1.0)", value.toString());

    value = new Value(Value.Type.WHOLE, 1);
    assertEquals(Value.WHOLE.dotted(), value);
    assertEquals("WHOLE.(1.5)", value.toString());

    value = new Value(Value.Type.WHOLE, 2);
    assertEquals(Value.WHOLE.dotted().dotted(), value);
    assertEquals("WHOLE..(2.25)", value.toString());

    value = new Value(Value.Type.WHOLE, 3);
    assertEquals(Value.WHOLE.dotted().dotted().dotted(), value);
    assertEquals("WHOLE...(3.375)", value.toString());
  }

  @Test
  public void testDotted() {
    Value value;

    value = new Value(Value.Type.DOUBLE_WHOLE);
    assertEquals(2.0, value.getDuration(), 0);
    assertEquals(2.0, value.dotted().undotted().getDuration(), 0);
    assertEquals(2.0 * 1.5, value.dotted().getDuration(), 0);
    assertEquals(2.0 * 1.5 * 1.5, value.dotted().dotted().getDuration(), 0);

    value = new Value(Value.Type.QUARTER);
    assertEquals(0.25, value.getDuration(), 0);
    assertEquals(0.25, value.dotted().undotted().getDuration(), 0);
    assertEquals(0.25 * 1.5, value.dotted().getDuration(), 0);
    assertEquals(0.25 * 1.5 * 1.5, value.dotted().dotted().getDuration(), 0);

    try {
      Value.QUARTER.undotted();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testTuplet() {
    Value value;

    value = new Value(Value.Type.WHOLE);
    assertEquals(1.0 / 3, value.triplet().getDuration(), 0);
    assertEquals(1.0 / 3, value.tuplet(3).getDuration(), 0);
    assertEquals(1.0 / 5, value.tuplet(5).getDuration(), 0);

    try {
      Value.TWO_HUNDRED_FIFTY_SIXTH.tuplet(1);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testString() {
    Value value;

    value = Value.fromString("WHOLE").get();
    assertEquals(Value.WHOLE, value);

    value = Value.fromString("WHOLE(1)").get();
    assertEquals(Value.WHOLE, value);

    value = Value.fromString("WHOLE(1.0)").get();
    assertEquals(Value.WHOLE, value);

    value = Value.fromString("HALF(0.5)").get();
    assertEquals(Value.WHOLE.tuplet(2), value);

    value = Value.fromString("EIGHTH(0.0833)").get();
    assertEquals(Value.QUARTER.triplet().getType(), value.getType());
    assertEquals(Value.QUARTER.triplet().getDotCount(), value.getDotCount());
    assertEquals(Value.QUARTER.triplet().getDuration(), value.getDuration(), 0.0001);

    value = Value.fromString("QUARTER.").get();
    assertEquals(Value.QUARTER.dotted(), value);
    assertEquals(Value.QUARTER.dotted().getType(), value.getType());
    assertEquals(Value.QUARTER.dotted().getDotCount(), value.getDotCount());
    assertEquals(Value.QUARTER.dotted().getDuration(), value.getDuration(), 0);
  }

  @Test
  public void testHashCodeAndEquals() {
    Value a, b;

    a = new Value(Value.Type.QUARTER);
    b = new Value(Value.Type.QUARTER);
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = new Value(Value.Type.HALF);
    b = new Value(Value.Type.WHOLE).tuplet(2);
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = new Value(Value.Type.HALF).tuplet(3);
    b = new Value(Value.Type.WHOLE).tuplet(6);
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = new Value(Value.Type.QUARTER).dotted();
    b = new Value(Value.Type.QUARTER, 1);
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());
  }
}
