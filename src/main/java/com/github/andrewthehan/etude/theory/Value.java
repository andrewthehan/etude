
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.RegEx;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Objects;

public final class Value{
  public enum Type{
    DOUBLE_WHOLE, WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH, HUNDRED_TWENTY_EIGHTH, TWO_HUNDRED_FIFTY_SIXTH;

    public static final int SIZE = Type.values().length;

    public static final Type fromBaseDuration(double baseDuration){
      double ordinal = 1 - Math.log(baseDuration) / Math.log(2);
      if(ordinal % 1 != 0){
        throw EtudeException.forInvalid(Type.class, baseDuration, "should be a power of 2");
      }
      return Type.values()[(int) ordinal];
    }

    public static final Exceptional<Type> fromString(String typeString){
      return EtudeParser
        .of(typeString)
        .filter(Objects::nonNull, EtudeException.forNull(Type.class))
        .parse(s -> Arrays
          .stream(Type.values())
          .filter(t -> s.equals(t.name()))
          .findFirst()
          .map(Exceptional::of)
          .orElseGet(Exceptional::empty)
          .withException(EtudeException.forInvalid(Type.class, s))
        )
        .get(a -> (Type) a[0]);
    }

    public final double getBaseDuration(){
      return Math.pow(2, 1 - ordinal());
    }
  }

  public static final Value DOUBLE_WHOLE = new Value(Value.Type.DOUBLE_WHOLE);
  public static final Value WHOLE = new Value(Value.Type.WHOLE);
  public static final Value HALF = new Value(Value.Type.HALF);
  public static final Value QUARTER = new Value(Value.Type.QUARTER);
  public static final Value EIGHTH = new Value(Value.Type.EIGHTH);
  public static final Value SIXTEENTH = new Value(Value.Type.SIXTEENTH);
  public static final Value THIRTY_SECOND = new Value(Value.Type.THIRTY_SECOND);
  public static final Value SIXTY_FOURTH = new Value(Value.Type.SIXTY_FOURTH);
  public static final Value HUNDRED_TWENTY_EIGHTH = new Value(Value.Type.HUNDRED_TWENTY_EIGHTH);
  public static final Value TWO_HUNDRED_FIFTY_SIXTH = new Value(Value.Type.TWO_HUNDRED_FIFTY_SIXTH);

  private final Type type;
  private final int dotCount;
  private final double duration;

  public Value(Type type){
    this(type, 0, type.getBaseDuration());
  }

  public Value(Type type, int dotCount){
    this(type, dotCount, type.getBaseDuration() * Math.pow(1.5, dotCount));
  }

  public Value(Type type, int dotCount, double duration){
    this.type = type;
    this.dotCount = dotCount;
    this.duration = duration;

    if(dotCount < 0){
      throw EtudeException.forInvalid(Value.class, dotCount, "should not be less than 0");
    }
  }

  public final Value dotted(){
    return new Value(type, dotCount + 1, duration * 1.5);
  }

  public final Value undotted(){
    return new Value(type, dotCount - 1, duration / 1.5);
  }

  public final Value tuplet(int n){
    if(type == Type.TWO_HUNDRED_FIFTY_SIXTH){
      throw EtudeException.forIllegalState(Value.class, "etude does not support value types smaller than " + type);
    }
    return new Value(Type.values()[type.ordinal() + 1], 0, duration / n);
  }

  public final Value triplet(){
    return tuplet(3);
  }

  public static final Exceptional<Value> fromString(String valueString){
    return EtudeParser
      .of(valueString)
      .filter(Objects::nonNull, EtudeException.forNull(Value.class))
      .parse(s -> {
        // longest prefix that contains only letters or .
        String prefix = RegEx.extract("^[a-zA-Z.]+", valueString);

        int dotIndex = prefix.indexOf('.');
        String typeString;
        int dotCount;
        if(dotIndex == -1){
          typeString = prefix;
          dotCount = 0; 
        }
        else{
          typeString = prefix.substring(0, dotIndex);
          dotCount = prefix.length() - dotIndex;
        }
        Exceptional<Type> type = Type.fromString(typeString);
        if(!type.isPresent()){
          return Exceptional.empty();
        }

        // a number that has an open parentheses somewhere before it
        String durationString = RegEx.extract("(?<=\\()[0-9.]+", valueString);
        double duration = durationString != null
          ? Double.parseDouble(durationString)
          : type.get().getBaseDuration() * Math.pow(1.5, dotCount);

        return Exceptional.of(new Value(type.get(), dotCount, duration));
      })
      .get(a -> (Value) a[0]);
  }

  @Override
  public final String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(type);
    builder.append(CharBuffer.allocate(dotCount).toString().replace('\0', '.'));
    builder.append("(");
    builder.append(duration);
    builder.append(")");
    return builder.toString();
  }

  @Override
  public final int hashCode(){
    return Objects.hash(type, dotCount, duration);
  }

  @Override
  public final boolean equals(Object other){
    if(!(other instanceof Value)){
      return false;
    }
    if(other == this){
      return true;
    }

    Value otherValue = (Value) other;

    return Objects.deepEquals(
      new Object[]{
        type, dotCount, duration
      },
      new Object[]{
        otherValue.getType(), otherValue.getDotCount(), otherValue.getDuration()
      }
    );
  }

  public final Type getType(){
    return type;
  }

  public final int getDotCount(){
    return dotCount;
  }

  public final double getDuration(){
    return duration;
  }
}
