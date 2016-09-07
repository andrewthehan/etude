
package com.github.andrewthehan.etude.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CircularIterator<E> implements Iterator<E>{
  private final E[] values;
  private int i;

  private CircularIterator(E[] values, int startingIndex){
    this.values = values;
    i = startingIndex;
  }

  public static final CircularIterator<Integer> of(int[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Integer> of(int[] values, int startingIndex){
    return new CircularIterator<Integer>(null, startingIndex){
      private final Integer[] innerValues = ArrayUtil.boxed(values);

      @Override
      public Integer[] getValues(){
        return innerValues;
      }
    };
  }

  public static final CircularIterator<Double> of(double[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Double> of(double[] values, int startingIndex){
    return new CircularIterator<Double>(null, startingIndex){
      private final Double[] innerValues = ArrayUtil.boxed(values);

      @Override
      public Double[] getValues(){
        return innerValues;
      }
    };
  }

  public static final CircularIterator<Long> of(long[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Long> of(long[] values, int startingIndex){
    return new CircularIterator<Long>(null, startingIndex){
      private final Long[] innerValues = ArrayUtil.boxed(values);

      @Override
      public Long[] getValues(){
        return innerValues;
      }
    };
  }

  public static final <E> CircularIterator<E> of(E[] values){
    return new CircularIterator<E>(values, 0);
  }

  public static final <E> CircularIterator<E> of(E[] values, E startingElement){
    List<E> list = Arrays.asList(values);
    // contains uses .equals(), not ==
    if(!list.contains(startingElement)){
      throw new RuntimeException("Values doesn't contain starting element: " + startingElement);
    }
    return new CircularIterator<E>(values, list.indexOf(startingElement));
  }

  public static final <E> CircularIterator<E> of(E[] values, int startingIndex){
    return new CircularIterator<E>(values, startingIndex);
  }

  public E[] getValues(){
    return values;
  }

  protected E getCurrentValue(){
    return getValues()[i];
  }

  protected void increment(){
    i = (i + 1) % getCycleLength();
  }

  public int getCycleLength(){
    return getValues().length;
  }

  @Override
  public final boolean hasNext(){
    return true;
  }

  @Override
  public E next(){
    E e = getCurrentValue();
    increment();
    return e;
  }

  @Override
  public void remove(){
    throw new UnsupportedOperationException();
  }
}