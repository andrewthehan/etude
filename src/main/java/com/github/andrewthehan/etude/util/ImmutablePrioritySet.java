
package com.github.andrewthehan.etude.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public final class ImmutablePrioritySet<E> extends TreeSet<E> {
  private ImmutablePrioritySet(List<? extends E> list) {
    // calling super(list) uses TreeSet#addAll which is not supported in
    // ImmutablePrioritySet
    super((a, b) -> Integer.compare(list.indexOf(a), list.indexOf(b)));
    // calling super.addAll(c) uses TreeSet#add which is not supported in
    // ImmutablePrioritySet
    list.forEach(super::add);
  }

  public static final ImmutablePrioritySet<Integer> of(int[] array) {
    return ImmutablePrioritySet.of(ArrayUtil.boxed(array));
  }

  public static final ImmutablePrioritySet<Double> of(double[] array) {
    return ImmutablePrioritySet.of(ArrayUtil.boxed(array));
  }

  public static final ImmutablePrioritySet<Long> of(long[] array) {
    return ImmutablePrioritySet.of(ArrayUtil.boxed(array));
  }

  public static final <E> ImmutablePrioritySet<E> of(E[] array) {
    return new ImmutablePrioritySet<E>(Arrays.asList(array));
  }

  public final int compare(E a, E b) {
    if (!contains(a) || !contains(b)) {
      throw new NoSuchElementException();
    }
    return comparator().compare(a, b);
  }

  @Override
  public final Object clone() {
    return this;
  }

  @Override
  public final boolean add(E e) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final E pollFirst() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final E pollLast() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }
}