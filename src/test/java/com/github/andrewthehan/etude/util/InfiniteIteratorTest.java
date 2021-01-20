
package com.github.andrewthehan.etude.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InfiniteIteratorTest {
  @Test
  public void testIntConstructor() {
    InfiniteIterator<Integer> it;

    it = InfiniteIterator.of(0, previous -> previous + 1);
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());

    it = InfiniteIterator.of(0, previous -> previous + 2);
    assertTrue(0 == it.next());
    assertTrue(2 == it.next());
    assertTrue(4 == it.next());

    it = InfiniteIterator.of(1, previous -> previous + 2);
    assertTrue(1 == it.next());
    assertTrue(3 == it.next());
    assertTrue(5 == it.next());

    it = InfiniteIterator.of(0, previous -> previous + 2);
    assertTrue(0 == it.next());
    assertTrue(2 == it.next());
    assertTrue(4 == it.next());
  }

  @Test
  public void testDoubleConstructor() {
    InfiniteIterator<Double> it;

    it = InfiniteIterator.of(0.0, previous -> previous + 0.1);
    assertTrue(0.0 == it.next());
    assertTrue(0.1 == it.next());
    assertTrue(0.2 == it.next());

    it = InfiniteIterator.of(0.0, previous -> previous + 1);
    assertTrue(0.0 == it.next());
    assertTrue(1.0 == it.next());
    assertTrue(2.0 == it.next());
  }

  @Test
  public void testLongConstructor() {
    InfiniteIterator<Long> it;

    it = InfiniteIterator.of(0L, previous -> previous + 1);
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());

    it = InfiniteIterator.of(0L, previous -> previous + 2);
    assertTrue(0 == it.next());
    assertTrue(2 == it.next());
    assertTrue(4 == it.next());

    it = InfiniteIterator.of(1L, previous -> previous + 2);
    assertTrue(1 == it.next());
    assertTrue(3 == it.next());
    assertTrue(5 == it.next());

    it = InfiniteIterator.of(0L, previous -> previous + 2);
    assertTrue(0 == it.next());
    assertTrue(2 == it.next());
    assertTrue(4 == it.next());
  }

  @Test
  public void testObjConstructor() {
    InfiniteIterator<String> it;

    it = InfiniteIterator.of("A", previous -> previous + "A");
    assertEquals("A", it.next());
    assertEquals("AA", it.next());
    assertEquals("AAA", it.next());
  }

  @Test
  public void testReset() {
    InfiniteIterator<Integer> it;

    it = InfiniteIterator.of(0, previous -> previous + 1);
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());
    it.reset();
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());
  }

  @Test
  public void testRemove() {
    InfiniteIterator<Integer> it;

    it = InfiniteIterator.of(0, previous -> previous + 1);
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());
    try {
      it.remove();
    } catch (Exception e) {
      assertTrue(e instanceof UnsupportedOperationException);
    }
    assertTrue(3 == it.next());
    assertTrue(4 == it.next());
    assertTrue(5 == it.next());
  }
}