
package jmtapi.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public final class Streams {
  public static final <T> Stream<T> fromIterator(Iterator<T> iterator){
    return StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(
        iterator,
        Spliterator.ORDERED
      ),
    false);
  }
}