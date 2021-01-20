
package com.github.andrewthehan.etude.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class RegExTest {
  @Test
  public void testConstructor() {
    try {
      Constructor<RegEx> constructor = RegEx.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
      fail("Expected an InvocationTargetException.");
    } catch (Exception e) {
      assertTrue(e instanceof InvocationTargetException);
      assertTrue(((InvocationTargetException) e).getCause() instanceof AssertionError);
    }
  }

  @Test
  public void testRegEx() {
    assertEquals("abc", RegEx.extract("a(\\w)*", "cbabc"));
    assertEquals("ba", RegEx.extract("b(\\w)?", "cbabc"));
    assertEquals(null, RegEx.extract("d(\\w)*", "cbabc"));
  }
}