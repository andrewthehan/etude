
package com.github.andrewthehan.etude.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamUtilTest {
  @Test
  public void testConstructor() {
    try {
      Constructor<StreamUtil> constructor = StreamUtil.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
      fail("Expected an InvocationTargetException.");
    } catch (Exception e) {
      assertTrue(e instanceof InvocationTargetException);
      assertTrue(((InvocationTargetException) e).getCause() instanceof AssertionError);
    }
  }

  @Test
  public void testFromIterator() {
    Iterator<String> it;

    it = Stream.<String>empty().iterator();
    assertFalse(it.hasNext());

    it = Stream.of("A", "B", "C").iterator();
    assertEquals("A", it.next());
    assertEquals("B", it.next());
    assertEquals("C", it.next());
    assertFalse(it.hasNext());

    it = Stream.of("A", "A", "A").iterator();
    assertEquals("A", it.next());
    assertEquals("A", it.next());
    assertEquals("A", it.next());
    assertFalse(it.hasNext());
  }
}