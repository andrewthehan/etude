
package com.github.andrewthehan.etude.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.Test;

public class ArrayUtilTest {
  @Test
  public void testConstructor() {
    try {
      Constructor<ArrayUtil> constructor = ArrayUtil.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
      fail("Expected an InvocationTargetException.");
    } catch (Exception e) {
      assertTrue(e instanceof InvocationTargetException);
      assertTrue(((InvocationTargetException) e).getCause() instanceof AssertionError);
    }
  }

  @Test
  public void testIntReverse() {
    int[] array;
    int[] result;

    array = new int[0];
    result = ArrayUtil.reverse(array);
    assertEquals("[]", Arrays.toString(result));

    try {
      array = null;
      ArrayUtil.reverse(array);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
    }

    array = new int[] { 1 };
    result = ArrayUtil.reverse(array);
    assertEquals("[1]", Arrays.toString(result));

    array = new int[] { 0, 0, 0 };
    result = ArrayUtil.reverse(array);
    assertEquals("[0, 0, 0]", Arrays.toString(result));

    array = new int[] { 1, 2, 3, 4, 5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[5, 4, 3, 2, 1]", Arrays.toString(result));

    array = new int[] { 5, 4, 3, 2, 1 };
    result = ArrayUtil.reverse(array);
    assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(result));

    array = new int[] { 1, 3, 5, 7, 9 };
    result = ArrayUtil.reverse(array);
    assertEquals("[9, 7, 5, 3, 1]", Arrays.toString(result));

    array = new int[] { 0, 2, 4, 6, 8 };
    result = ArrayUtil.reverse(array);
    assertEquals("[8, 6, 4, 2, 0]", Arrays.toString(result));

    array = new int[] { -1, -2, -3, -4, -5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[-5, -4, -3, -2, -1]", Arrays.toString(result));

    array = new int[] { 1, -1, 2, -2, 3, -3 };
    result = ArrayUtil.reverse(array);
    assertEquals("[-3, 3, -2, 2, -1, 1]", Arrays.toString(result));
  }

  @Test
  public void testDoubleReverse() {
    double[] array;
    double[] result;

    array = new double[0];
    result = ArrayUtil.reverse(array);
    assertEquals("[]", Arrays.toString(result));

    try {
      array = null;
      ArrayUtil.reverse(array);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
    }

    array = new double[] { 1 };
    result = ArrayUtil.reverse(array);
    assertEquals("[1.0]", Arrays.toString(result));

    array = new double[] { 0, 0, 0 };
    result = ArrayUtil.reverse(array);
    assertEquals("[0.0, 0.0, 0.0]", Arrays.toString(result));

    array = new double[] { 1, 2, 3, 4, 5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", Arrays.toString(result));

    array = new double[] { 1.1, 2.2, 3.3, 4.4, 5.5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[5.5, 4.4, 3.3, 2.2, 1.1]", Arrays.toString(result));

    array = new double[] { -1.1, -2.2, -3.3, -4.4, -5.5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[-5.5, -4.4, -3.3, -2.2, -1.1]", Arrays.toString(result));
  }

  @Test
  public void testLongReverse() {
    long[] array;
    long[] result;

    array = new long[0];
    result = ArrayUtil.reverse(array);
    assertEquals("[]", Arrays.toString(result));

    try {
      array = null;
      ArrayUtil.reverse(array);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
    }

    array = new long[] { 1 };
    result = ArrayUtil.reverse(array);
    assertEquals("[1]", Arrays.toString(result));

    array = new long[] { 0, 0, 0 };
    result = ArrayUtil.reverse(array);
    assertEquals("[0, 0, 0]", Arrays.toString(result));

    array = new long[] { 1, 2, 3, 4, 5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[5, 4, 3, 2, 1]", Arrays.toString(result));

    array = new long[] { 5, 4, 3, 2, 1 };
    result = ArrayUtil.reverse(array);
    assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(result));

    array = new long[] { 1, 3, 5, 7, 9 };
    result = ArrayUtil.reverse(array);
    assertEquals("[9, 7, 5, 3, 1]", Arrays.toString(result));

    array = new long[] { 0, 2, 4, 6, 8 };
    result = ArrayUtil.reverse(array);
    assertEquals("[8, 6, 4, 2, 0]", Arrays.toString(result));

    array = new long[] { -1, -2, -3, -4, -5 };
    result = ArrayUtil.reverse(array);
    assertEquals("[-5, -4, -3, -2, -1]", Arrays.toString(result));

    array = new long[] { 1, -1, 2, -2, 3, -3 };
    result = ArrayUtil.reverse(array);
    assertEquals("[-3, 3, -2, 2, -1, 1]", Arrays.toString(result));
  }

  @Test
  public void testObjReverse() {
    String[] array;
    String[] result;

    array = new String[0];
    result = ArrayUtil.reverse(array);
    assertEquals("[]", Arrays.toString(result));

    try {
      array = null;
      ArrayUtil.reverse(array);
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
    }

    array = new String[] { "A", "B", "C" };
    result = ArrayUtil.reverse(array);
    assertEquals("[C, B, A]", Arrays.toString(result));
    assertTrue(array[0] instanceof String);

    array = new String[] { "ABC", "DEF", "GHI" };
    result = ArrayUtil.reverse(array);
    assertEquals("[GHI, DEF, ABC]", Arrays.toString(result));
  }

  @Test
  public void testBoxed() {
    assertTrue(ArrayUtil.boxed(new int[0]) instanceof Integer[]);
    assertTrue(ArrayUtil.boxed(new double[0]) instanceof Double[]);
    assertTrue(ArrayUtil.boxed(new long[0]) instanceof Long[]);
  }
}