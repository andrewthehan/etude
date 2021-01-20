
package com.github.andrewthehan.etude.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CircularIterator<E> implements Iterator<E>{
  private static final int DEFAULT_INCREMENT_AMOUNT = 1;

  private final E[] values;
  private int i;

  private CircularIterator(E[] values, int startingIndex){
    this.values = values;
    i = startingIndex - getIncrementAmount();
  }

  public static final CircularIterator<Integer> of(int[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Integer> of(int[] values, int startingIndex){
    return CircularIterator.of(ArrayUtil.boxed(values), startingIndex);
  }

  public static final CircularIterator<Double> of(double[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Double> of(double[] values, int startingIndex){
    return CircularIterator.of(ArrayUtil.boxed(values), startingIndex);
  }

  public static final CircularIterator<Long> of(long[] values){
    return CircularIterator.of(values, 0);
  }

  public static final CircularIterator<Long> of(long[] values, int startingIndex){
    return CircularIterator.of(ArrayUtil.boxed(values), startingIndex);
  }

  public static final <E> CircularIterator<E> of(E[] values){
    return new CircularIterator<E>(values, 0);
  }

  public static final <E> CircularIterator<E> of(E[] values, E startingElement){
    List<E> list = Arrays.asList(values);
    // contains uses .equals(), not ==
    if(!list.contains(startingElement)){
      throw new NoSuchElementException();
    }
    return new CircularIterator<E>(values, list.indexOf(startingElement));
  }

  public static final <E> CircularIterator<E> of(E[] values, int startingIndex){
    return new CircularIterator<E>(values, startingIndex);
  }

  public final CircularIterator<E> reverse(){
    int amount = getIncrementAmount();
    return new CircularIterator<E>(values, i + getIncrementAmount()){
      @Override
      protected int getIncrementAmount(){
        return -amount;
      }
    };
  }

  public E[] getValues(){
    return values;
  }

  public int getCycleLength(){
    return getValues().length;
  }

  protected int getIncrementAmount(){
    return DEFAULT_INCREMENT_AMOUNT;
  }

  protected void increment(){
    i = Math.floorMod(i + getIncrementAmount(), getCycleLength());
  }

  @Override
  public final boolean hasNext(){
    return true;
  }

  @Override
  public final E next(){
    increment();
    return getValues()[i];
  }

  @Override
  public void remove(){
    throw new UnsupportedOperationException();
  }
}