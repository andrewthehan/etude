
package com.github.andrewthehan.etude.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class EtudeParser<T> {
  private final T t;
  private final RuntimeException exception;
  private final List<Object> objects;

  private EtudeParser(T t, RuntimeException exception) {
    this.t = t;
    this.exception = exception;
    objects = new ArrayList<>();
  }

  private boolean isInvalid() {
    return exception != null;
  }

  public static <T> EtudeParser<T> of(T t) {
    return new EtudeParser<>(t, null);
  }

  public static <T> EtudeParser<T> of(T t, RuntimeException exception) {
    return new EtudeParser<>(t, exception);
  }

  public <S> EtudeParser<S> map(Function<T, S> func) {
    return isInvalid() ? EtudeParser.of(null, exception) : EtudeParser.of(func.apply(t));
  }

  public EtudeParser<T> filter(Predicate<T> predicate, RuntimeException exception) {
    return isInvalid() ? this : (predicate.test(t) ? this : EtudeParser.of(t, exception));
  }

  public <S> EtudeParser<T> parse(Function<T, Exceptional<S>> func) {
    if (isInvalid()) {
      return this;
    }

    Exceptional<S> exceptional = func.apply(t);
    if (!exceptional.isPresent()) {
      return EtudeParser.of(t, exceptional.getException());
    }

    objects.add(exceptional.get());

    return this;
  }

  public <S> Exceptional<S> get(Function<Object[], S> func) {
    return isInvalid() ? Exceptional.empty(exception) : Exceptional.of(func.apply(objects.toArray()));
  }
}