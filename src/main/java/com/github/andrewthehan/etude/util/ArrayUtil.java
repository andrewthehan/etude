
package com.github.andrewthehan.etude.util;

import java.lang.reflect.Array;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class ArrayUtil {
  private ArrayUtil() {
    throw new AssertionError();
  }

  public static final int[] reverse(int[] array) {
    return IntStream.range(0, array.length).map(i -> array[(array.length - 1) - i]).toArray();
  }

  public static final double[] reverse(double[] array) {
    return IntStream.range(0, array.length).mapToDouble(i -> array[(array.length - 1) - i]).toArray();
  }

  public static final long[] reverse(long[] array) {
    return IntStream.range(0, array.length).mapToLong(i -> array[(array.length - 1) - i]).toArray();
  }

  @SuppressWarnings("unchecked")
  public static final <T> T[] reverse(T[] array) {
    return IntStream.range(0, array.length).mapToObj(i -> array[(array.length - 1) - i])
        .toArray(size -> (T[]) Array.newInstance(array.getClass().getComponentType(), size));
  }

  public static final Integer[] boxed(int[] array) {
    return IntStream.of(array).boxed().toArray(Integer[]::new);
  }

  public static final Double[] boxed(double[] array) {
    return DoubleStream.of(array).boxed().toArray(Double[]::new);
  }

  public static final Long[] boxed(long[] array) {
    return LongStream.of(array).boxed().toArray(Long[]::new);
  }
}