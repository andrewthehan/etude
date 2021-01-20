
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.Exceptional;

public enum Degree {
  TONIC, SUPERTONIC, MEDIANT, SUBDOMINANT, DOMINANT, SUBMEDIANT, LEADING_TONE;

  public static final int SIZE = Degree.values().length;

  public static final boolean isValid(int value) {
    return 1 <= value && value <= Degree.SIZE;
  }

  public static final Exceptional<Degree> fromValue(int value) {
    return Exceptional.of(value).filter(Degree::isValid, EtudeException.forInvalid(Degree.class, value, "out of range"))
        .map(i -> Degree.values()[i - 1]);
  }

  public final int getValue() {
    return ordinal() + 1;
  }
}
