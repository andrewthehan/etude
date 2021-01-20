
package com.github.andrewthehan.etude.util;

import java.util.function.Function;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InfiniteIterator<E> implements Iterator<E>{
  private final E initial;
  private final Function<E, E> func;
  private E current;
  private E next;

  private InfiniteIterator(E initial, Function<E, E> func){
    this.initial = initial;
    this.func = func;
    current = null;
    next = initial;
  }

  public static final <E> InfiniteIterator<E> of(E initial, Function<E, E> func){
    return new InfiniteIterator<E>(initial, func);
  }

  public void reset(){
    current = null;
    next = initial;
  }
  
  @Override
  public final boolean hasNext(){
    try{
      if(next == null){
        next = func.apply(current);
      }
      return next != null;
    }
    catch(Exception e){
      return false;
    }
  }

  @Override
  public final E next(){
    if(hasNext()){
      current = next;
      next = null;
      return current;
    }
    throw new NoSuchElementException();
  }

  @Override
  public void remove(){
    throw new UnsupportedOperationException();
  }
}