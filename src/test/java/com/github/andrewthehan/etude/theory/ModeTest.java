
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.andrewthehan.etude.util.ArrayUtil;

import org.junit.Test;

public class ModeTest {
  @Test
  public void testStepPattern() {
    Mode root = Mode.IONIAN;
    List<Integer> rootStepPattern = Arrays.asList(ArrayUtil.boxed(root.getStepPattern()));
    Mode current;
    List<Integer> currentStepPattern;

    for (int i = 0; i < Mode.SIZE; ++i) {
      current = Mode.values()[i];
      currentStepPattern = Arrays.asList(ArrayUtil.boxed(current.getStepPattern()));
      Collections.rotate(currentStepPattern, i);
      assertTrue(rootStepPattern.equals(currentStepPattern));
    }
  }

  @Test
  public void testValueOf() {
    assertEquals(Mode.IONIAN, Mode.valueOf("IONIAN"));
    assertEquals(Mode.DORIAN, Mode.valueOf("DORIAN"));
    assertEquals(Mode.PHRYGIAN, Mode.valueOf("PHRYGIAN"));
    assertEquals(Mode.LYDIAN, Mode.valueOf("LYDIAN"));
    assertEquals(Mode.MIXOLYDIAN, Mode.valueOf("MIXOLYDIAN"));
    assertEquals(Mode.AEOLIAN, Mode.valueOf("AEOLIAN"));
    assertEquals(Mode.LOCRIAN, Mode.valueOf("LOCRIAN"));
  }
}