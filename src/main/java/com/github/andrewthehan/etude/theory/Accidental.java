
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.EtudeParser;
import java.util.Arrays;
import java.util.Objects;

public enum Accidental {
  TRIPLE_FLAT(-3, "bbb"), DOUBLE_FLAT(-2, "bb"), FLAT(-1, "b"), NATURAL(0, "n"), SHARP(1, "#"), DOUBLE_SHARP(2, "x"),
  TRIPLE_SHARP(3, "#x");

  public static final int SIZE = Accidental.values().length;

  private final int offset;
  private final String symbol;

  private Accidental(int offset, String symbol) {
    this.offset = offset;
    this.symbol = symbol;
  }

  public static final boolean isValid(int offset) {
    return TRIPLE_FLAT.getOffset() <= offset && offset <= TRIPLE_SHARP.getOffset();
  }

  public static final Exceptional<Accidental> fromOffset(int offset) {
    return Exceptional.of(offset)
        .filter(Accidental::isValid, EtudeException.forInvalid(Accidental.class, offset, "out of range"))
        .map(i -> Accidental.values()[i - TRIPLE_FLAT.getOffset()]);
  }

  public final int getOffset() {
    return offset;
  }

  public static final boolean isValid(String accidentalString) {
    return Arrays.stream(Accidental.values()).map(Accidental::toString).anyMatch(accidentalString::equals);
  }

  public static final Exceptional<Accidental> fromString(String accidentalString) {
    return EtudeParser.of(accidentalString).filter(Objects::nonNull, EtudeException.forNull(Accidental.class))
        .parse(
            s -> Arrays.stream(Accidental.values()).filter(a -> s.equals(a.toString())).findFirst().map(Exceptional::of)
                .orElseGet(Exceptional::empty).withException(EtudeException.forInvalid(Accidental.class, s)))
        .get(a -> (Accidental) a[0]);
  }

  @Override
  public final String toString() {
    return symbol;
  }
}
