
package com.github.andrewthehan.etude.test.util;

import com.github.andrewthehan.etude.util.CircularIterator;

import static org.junit.Assert.*;
import org.junit.Test;

public class CircularIteratorTest{
  @Test
  public void testIntConstructorAndNextAndHasNext(){
    int[] intValues = new int[]{0, 1, 2};
    CircularIterator<Integer> intIt = CircularIterator.of(intValues);
    for(int i = 0; i < 10; ++i){
      assertTrue(0 == intIt.next());
      assertTrue(1 == intIt.next());
      assertTrue(2 == intIt.next());     
    }
    assertTrue(intIt.hasNext());
    intIt = CircularIterator.of(intValues, 1);
    for(int i = 0; i < 10; ++i){
      assertTrue(1 == intIt.next());
      assertTrue(2 == intIt.next());     
      assertTrue(0 == intIt.next());
    }
    assertTrue(intIt.hasNext());

    intValues = new int[]{0, 2, 4};
    intIt = CircularIterator.of(intValues, 1);
    for(int i = 0; i < 10; ++i){
      assertTrue(2 == intIt.next());
      assertTrue(4 == intIt.next());     
      assertTrue(0 == intIt.next());
    }
    assertTrue(intIt.hasNext());
  }

  @Test
  public void testDoubleConstructorAndNextAndHasNext(){
    double[] doubleValues = new double[]{0.0, 1.1, 2.2};
    CircularIterator<Double> doubleIt = CircularIterator.of(doubleValues);
    for(int i = 0; i < 10; ++i){
      assertTrue(0.0 == doubleIt.next());
      assertTrue(1.1 == doubleIt.next());
      assertTrue(2.2 == doubleIt.next());     
    }
    assertTrue(doubleIt.hasNext());
    doubleIt = CircularIterator.of(doubleValues, 1);
    for(int i = 0; i < 10; ++i){
      assertTrue(1.1 == doubleIt.next());
      assertTrue(2.2 == doubleIt.next());     
      assertTrue(0.0 == doubleIt.next());
    }
    assertTrue(doubleIt.hasNext());
  }

	@Test
  public void testLongConstructorAndNextAndHasNext(){
    long[] longValues = new long[]{0, 1, 2};
    CircularIterator<Long> longIt = CircularIterator.of(longValues);
    for(int i = 0; i < 10; ++i){
      assertTrue(0 == longIt.next());
      assertTrue(1 == longIt.next());
      assertTrue(2 == longIt.next());     
    }
    assertTrue(longIt.hasNext());
    longIt = CircularIterator.of(longValues, 1);
    for(int i = 0; i < 10; ++i){
      assertTrue(1 == longIt.next());
      assertTrue(2 == longIt.next());     
      assertTrue(0 == longIt.next());
    }
    assertTrue(longIt.hasNext());
  }

  @Test
  public void testObjConstructorAndNextAndHasNext(){
    String[] objValues = new String[]{"A", "B", "C"};
    CircularIterator<String> objIt = CircularIterator.of(objValues);
    for(int i = 0; i < 10; ++i){
      assertEquals("A", objIt.next());
      assertEquals("B", objIt.next());
      assertEquals("C", objIt.next());     
    }
    assertTrue(objIt.hasNext());
    objIt = CircularIterator.of(objValues, 1);
    for(int i = 0; i < 10; ++i){
      assertEquals("B", objIt.next());
      assertEquals("C", objIt.next());     
      assertEquals("A", objIt.next());
    }
    assertTrue(objIt.hasNext());
    objIt = CircularIterator.of(objValues, "B");
    for(int i = 0; i < 10; ++i){
      assertEquals("B", objIt.next());
      assertEquals("C", objIt.next());     
      assertEquals("A", objIt.next());
    }
    assertTrue(objIt.hasNext());
    objIt = CircularIterator.of(objValues, new String("B"));
    for(int i = 0; i < 10; ++i){
      assertEquals("B", objIt.next());
      assertEquals("C", objIt.next());     
      assertEquals("A", objIt.next());
    }
    assertTrue(objIt.hasNext());
    try{
      CircularIterator.of(objValues, "D");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Values doesn't contain starting element: D", e.getMessage());
    }
  }

  @Test
  public void testRemove(){
    try{
      CircularIterator.of(new String[]{""}).remove();
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertTrue(e instanceof UnsupportedOperationException);
    }
  }
}