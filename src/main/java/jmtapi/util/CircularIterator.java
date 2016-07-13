
package jmtapi.util;

import java.util.Arrays;
import java.util.Iterator;

public final class CircularIterator<E> implements Iterator<E>{
  private final E[] values;
  private int i;

  public CircularIterator(E[] values){
    this(values, 0);
  }

  public CircularIterator(E[] values, E startingElement){
    this(values, Arrays.asList(values).indexOf(startingElement));
  }

  public CircularIterator(E[] values, int startingIndex){
    this.values = values;
    i = startingIndex - 1;
  }

  @Override
  public boolean hasNext(){
    return true;
  }

  @Override
  public E next(){
    i = (i + 1) % values.length;
    return values[i];
  }

  @Override
  public void remove(){
    throw new UnsupportedOperationException();
  }
}