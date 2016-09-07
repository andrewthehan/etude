
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;
import com.github.andrewthehan.etude.util.ArrayUtil;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModeTest{
  @Test
  public void testStepPattern(){
    Mode root = Mode.IONIAN;
    List<Integer> rootStepPattern = Arrays.asList(ArrayUtil.boxed(root.getStepPattern()));
    Mode current;
    List<Integer> currentStepPattern;

    for(int i = 0; i < Mode.values().length; ++i){
      current = Mode.values()[i];
      currentStepPattern = Arrays.asList(ArrayUtil.boxed(current.getStepPattern()));
      Collections.rotate(currentStepPattern, i);
      assertTrue(rootStepPattern.equals(currentStepPattern));
    }
  }

  @Test
  public void testValueOf(){
    assertEquals(Mode.IONIAN, Mode.valueOf("IONIAN"));
    assertEquals(Mode.DORIAN, Mode.valueOf("DORIAN"));
    assertEquals(Mode.PHRYGIAN, Mode.valueOf("PHRYGIAN"));
    assertEquals(Mode.LYDIAN, Mode.valueOf("LYDIAN"));
    assertEquals(Mode.MIXOLYDIAN, Mode.valueOf("MIXOLYDIAN"));
    assertEquals(Mode.AEOLIAN, Mode.valueOf("AEOLIAN"));
    assertEquals(Mode.LOCRIAN, Mode.valueOf("LOCRIAN"));
  }
}