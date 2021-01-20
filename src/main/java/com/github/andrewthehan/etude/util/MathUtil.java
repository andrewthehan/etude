
package com.github.andrewthehan.etude.util;

public final class MathUtil {
  private MathUtil(){
    throw new AssertionError();
  }
  
  public static final int clamp(int min, int max, int value){
    if(max < min){
      throw new IllegalArgumentException("max should not be less than min");
    }
    return Math.max(min, Math.min(max, value));
  }
}