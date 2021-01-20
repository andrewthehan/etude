
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.util.ArrayUtil;

import java.util.stream.IntStream;

public enum Mode{
  IONIAN(new int[]{2, 2, 1, 2, 2, 2, 1}),
  DORIAN(new int[]{2, 1, 2, 2, 2, 1, 2}),
  PHRYGIAN(new int[]{1, 2, 2, 2, 1, 2, 2}),
  LYDIAN(new int[]{2, 2, 2, 1, 2, 2, 1}),
  MIXOLYDIAN(new int[]{2, 2, 1, 2, 2, 1, 2}),
  AEOLIAN(new int[]{2, 1, 2, 2, 1, 2, 2}),
  LOCRIAN(new int[]{1, 2, 2, 1, 2, 2, 2});

  public static final int SIZE = Mode.values().length;

  private final int[] ascending, descending;

  private Mode(int[] stepPattern){
    ascending = stepPattern;
    descending = IntStream.of(ArrayUtil.reverse(stepPattern)).map(Math::negateExact).toArray();
  }

  public final int[] getStepPattern(){
    return getStepPattern(Direction.DEFAULT);
  }

  public final int[] getStepPattern(Direction direction){
    switch(direction){
      case ASCENDING: return ascending.clone();
      case DESCENDING: return descending.clone();
      default: throw new AssertionError();
    }
  }
}
