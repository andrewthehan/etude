
package com.github.andrewthehan.etude.test.util;

import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImmutablePrioritySetTest{
  @Test
  public void testIntConstructor(){
    int[] values;
    ImmutablePrioritySet<Integer> priorities;
    Iterator<Integer> it;

    values = new int[]{0, 1, 2};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());
    assertFalse(it.hasNext());

    values = new int[]{2, 1, 0};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(2 == it.next());
    assertTrue(1 == it.next());
    assertTrue(0 == it.next());
    assertFalse(it.hasNext());

    values = new int[]{0, 2, 1};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0 == it.next());
    assertTrue(2 == it.next());
    assertTrue(1 == it.next());
    assertFalse(it.hasNext());

    values = new int[]{0, -1, -2};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0 == it.next());
    assertTrue(-1 == it.next());
    assertTrue(-2 == it.next());
    assertFalse(it.hasNext());

    values = new int[]{1, -1, 2, -2};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(1 == it.next());
    assertTrue(-1 == it.next());
    assertTrue(2 == it.next());
    assertTrue(-2 == it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void testDoubleConstructor(){
    double[] values;
    ImmutablePrioritySet<Double> priorities;
    Iterator<Double> it;

    values = new double[]{0.0, 1.1, 2.2};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0.0 == it.next());
    assertTrue(1.1 == it.next());
    assertTrue(2.2 == it.next());
    assertFalse(it.hasNext());

    values = new double[]{0.1, 0.01, 0.001};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0.1 == it.next());
    assertTrue(0.01 == it.next());
    assertTrue(0.001 == it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void testLongConstructor(){
    long[] values;
    ImmutablePrioritySet<Long> priorities;
    Iterator<Long> it;

    values = new long[]{0, 1, 2};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertTrue(0 == it.next());
    assertTrue(1 == it.next());
    assertTrue(2 == it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void testObjConstructor(){
    String[] values;
    ImmutablePrioritySet<String> priorities;
    Iterator<String> it;

    values = new String[]{"A", "B", "C"};
    priorities = ImmutablePrioritySet.of(values);
    it = priorities.iterator();
    assertEquals("A", it.next());
    assertEquals("B", it.next());
    assertEquals("C", it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void testCompare(){
    int[] values;
    ImmutablePrioritySet<Integer> priorities;

    values = new int[]{0, 1, -1};
    priorities = ImmutablePrioritySet.of(values);
    assertTrue(priorities.compare(0, 0) == 0);
    assertTrue(priorities.compare(0, 1) == -1);
    assertTrue(priorities.compare(0, -1) == -1);
    assertTrue(priorities.compare(1, 0) == 1);
    assertTrue(priorities.compare(1, 1) == 0);
    assertTrue(priorities.compare(1, -1) == -1);
    assertTrue(priorities.compare(-1, 0) == 1);
    assertTrue(priorities.compare(-1, 1) == 1);
    assertTrue(priorities.compare(-1, -1) == 0);

    try{
      priorities.compare(2, 0);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertTrue(e instanceof NoSuchElementException);
    }

    try{
      priorities.compare(0, 2);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertTrue(e instanceof NoSuchElementException);
    }

    try{
      priorities.compare(2, 2);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertTrue(e instanceof NoSuchElementException);
    }
  }

  @Test
  public void testUnsupportedOperations(){
    int[] values = new int[]{0, 1, 2};
    ImmutablePrioritySet<Integer> priorities = ImmutablePrioritySet.of(values);
    
    try{
      priorities.add(null);
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.addAll(null);
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.clear();
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.clone();
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.pollFirst();
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.pollLast();
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }

    try{
      priorities.remove(null);
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }
  }
}