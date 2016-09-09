
package com.github.andrewthehan.etude.test.util;

import com.github.andrewthehan.etude.util.RegEx;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RegExTest{
  @Test
  public void testConstructor(){
    try{
      Constructor<RegEx> constructor = RegEx.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
      fail("Expected an InvocationTargetException.");
    }
    catch(Exception e){
      assertTrue(e instanceof InvocationTargetException);
      try{
        throw ((InvocationTargetException) e).getCause();
      }
      catch(Throwable ee){
        assertTrue(ee instanceof AssertionError);
      }
    }
  }

  @Test
  public void testRegEx(){
    assertEquals("abc", RegEx.extract("a(\\w)*", "cbabc"));
    assertEquals("ba", RegEx.extract("b(\\w)?", "cbabc"));
    assertEquals(null, RegEx.extract("d(\\w)*", "cbabc"));
  }
}