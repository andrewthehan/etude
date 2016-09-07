
package com.github.andrewthehan.etude.util;

import java.util.function.Function;
import java.util.Iterator;

public class InfiniteIterator<E> implements Iterator<E>{
  private final E initial;
  private final Function<E, E> function;
  private E current;

  private InfiniteIterator(E initial, Function<E, E> function){
    this.initial = initial;
    this.function = function;
    current = null;
  }

  public static final <E> InfiniteIterator<E> of(E initial, Function<E, E> function){
    return new InfiniteIterator<E>(initial, function);
  }

  public void reset(){
    current = null;
  }
  
  @Override
  public final boolean hasNext(){
    return true;
  }

  @Override
  public E next(){
    if(current == null){
      current = initial;
    }
    else{
      current = function.apply(current);
    }
    return current;
  }

  @Override
  public void remove(){
    throw new UnsupportedOperationException();
  }
}