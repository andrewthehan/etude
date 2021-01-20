
package com.github.andrewthehan.etude.exception;

public class EtudeException extends RuntimeException {
  private EtudeException(String message) {
    super(message);
  }

  public static <C> EtudeException forNull(Class<C> c) {
    return new EtudeException(String.format("[%s] Null value", c.getName()));
  }

  public static <C> EtudeException forIllegalState(Class<C> c, String reason) {
    return new EtudeException(String.format("[%s] Illegal state: %s", c.getName(), reason));
  }

  public static <C, T> EtudeException forIllegalArgument(Class<C> c, T t, String reason) {
    return new EtudeException(String.format("[%s] Illegal argument: %s (%s)", c.getName(), t, reason));
  }

  public static <C, T> EtudeException forInvalid(Class<C> c, T t) {
    return new EtudeException(String.format("[%s] Invalid value: %s", c.getName(), t));
  }

  public static <C, T> EtudeException forInvalid(Class<C> c, T t, String reason) {
    return new EtudeException(String.format("[%s] Invalid value: %s (%s)", c.getName(), t, reason));
  }
}