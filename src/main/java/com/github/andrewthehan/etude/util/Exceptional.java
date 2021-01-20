
package com.github.andrewthehan.etude.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;

public final class Exceptional<T> {
  private final T value;
  private final RuntimeException exception;

  private Exceptional(T value, RuntimeException exception){
    if((value == null) == (exception == null)){
      throw new AssertionError();
    }

    this.value = value;
    this.exception = exception;
  }

  private static RuntimeException getDefaultException(){
    return new RuntimeException();
  }

  public static <T> Exceptional<T> fromOptional(Optional<T> optional){
    return Exceptional.fromOptional(optional, Exceptional.getDefaultException());
  }
  
  public static <T> Exceptional<T> fromOptional(Optional<T> optional, RuntimeException exception){
    return Exceptional.fromOptional(optional, t -> exception);
  }
  
  public static <T> Exceptional<T> fromOptional(Optional<T> optional, Function<Optional<T>, RuntimeException> func){
    return Exceptional.ofNullable(optional.orElse(null), func.apply(optional));
  }

  public static <T> Exceptional<T> empty(){
    return Exceptional.empty(Exceptional.getDefaultException());
  }

  public static <T> Exceptional<T> empty(RuntimeException exception) {
    return Exceptional.ofNullable(null, exception);
  }

  public Exceptional<T> filter(Predicate<? super T> predicate){
    return filter(predicate, Exceptional.getDefaultException());
  }

  public Exceptional<T> filter(Predicate<? super T> predicate, RuntimeException exception){
    return filter(predicate, t -> exception);
  }

  public Exceptional<T> filter(Predicate<? super T> predicate, Function<T, RuntimeException> func) {
    return isPresent()
      ? (predicate.test(value)
        ? this
        : Exceptional.ofNullable(null, func.apply(value))
      )
      : this;
  }

  public <U> Exceptional<U> flatMap(Function<? super T, Exceptional<U>> mapper) {
    return isPresent()
      ? mapper.apply(get())
      : Exceptional.empty();
  }

  public T get() {
    if(!isPresent()){
      throw exception;
    }
    return value;
  }

  public void ifPresent(Consumer<? super T> consumer) {
    if(isPresent()){
      consumer.accept(value);
    }
  }

  public boolean isPresent() {
    return value != null;
  }

  public <U> Exceptional<U> map(Function<? super T, ? extends U> mapper) {
    return map(mapper, Exceptional.getDefaultException());
  }

  public <U> Exceptional<U> map(Function<? super T, ? extends U> mapper, RuntimeException exception) {
    return map(mapper, t -> exception);
  }

  public <U> Exceptional<U> map(Function<? super T, ? extends U> mapper, Function<T, RuntimeException> func) {
    if(!isPresent()){
      return Exceptional.empty(exception);
    }
    U u = mapper.apply(value);
    return Exceptional.ofNullable(u, u == null ? func.apply(value) : null);
  }

  public static <T> Exceptional<T> of(T value){
    return Exceptional.of(value, Exceptional.getDefaultException());
  }

  public static <T> Exceptional<T> of(T value, RuntimeException exception){
    if(value == null){
      throw exception;
    }
    return new Exceptional<>(value, null);
  }

  public static <T> Exceptional<T> ofNullable(T value){
    return Exceptional.ofNullable(value, Exceptional.getDefaultException());
  }

  public static <T> Exceptional<T> ofNullable(T value, RuntimeException exception){
    return new Exceptional<>(value, value == null ? exception : null);
  }

  public T orElse(T other){
    return isPresent()
      ? value
      : other;
  }

  public T orElseGet(Supplier<? extends T> other){
    return isPresent()
      ? value
      : other.get();
  }

  public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
    if(!isPresent()){
      throw exceptionSupplier.get();
    }

    return value;
  }

  public RuntimeException getException(){
    return exception;
  }

  public Exceptional<T> withException(RuntimeException exception){
    return isPresent()
      ? this
      : Exceptional.empty(exception);
  }
}