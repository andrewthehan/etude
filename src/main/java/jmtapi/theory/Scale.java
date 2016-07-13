
package jmtapi.theory;

import jmtapi.util.CircularIterator;
import jmtapi.util.Streams;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Scale{
  private final KeySignature keySignature;

  public Scale(KeySignature keySignature){
    this.keySignature = keySignature;
  }

  public final Stream<Key> stream(){
    return Streams.fromIterator(iterator());
  }

  public final Iterator<Key> iterator(){
    return new CircularIterator<Key>(getKeys());
  }

  public final Key[] getKeys(){
    return Arrays
      .stream(Degree.values())
      .map(keySignature::keyOf)
      .toArray(Key[]::new);
  }

  public final List<Key> asList(){
    return Arrays.asList(getKeys());
  }

  @Override
  public String toString(){
    return keySignature.toString();
  }
}