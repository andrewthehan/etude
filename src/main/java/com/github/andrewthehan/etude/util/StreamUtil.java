
package com.github.andrewthehan.etude.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtil {
  private StreamUtil() {
    throw new AssertionError();
  }

  public static final <T> Stream<T> fromIterator(Iterator<T> iterator) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
  }
}