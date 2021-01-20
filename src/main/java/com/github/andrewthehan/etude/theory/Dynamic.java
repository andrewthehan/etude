
package com.github.andrewthehan.etude.theory;

import java.util.Objects;

import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.MathUtil;

public final class Dynamic {
  public static final int MINIMUM_VELOCITY = 0;
  public static final int MAXIMUM_VELOCITY = 127;

  public static final Dynamic PIANISSISSIMO = new Dynamic(16);
  public static final Dynamic PIANISSIMO = new Dynamic(32);
  public static final Dynamic PIANO = new Dynamic(48);
  public static final Dynamic MEZZO_PIANO = new Dynamic(64);
  public static final Dynamic MEZZO_FORTE = new Dynamic(80);
  public static final Dynamic FORTE = new Dynamic(96);
  public static final Dynamic FORTISSIMO = new Dynamic(112);
  public static final Dynamic FORTISSISSIMO = new Dynamic(127);

  private final int velocity;

  public Dynamic(int velocity) {
    this.velocity = MathUtil.clamp(MINIMUM_VELOCITY, MAXIMUM_VELOCITY, velocity);
  }

  public final Dynamic crescendo(int amount) {
    return new Dynamic(velocity + amount);
  }

  public final Dynamic diminuendo(int amount) {
    return new Dynamic(velocity - amount);
  }

  public static final Exceptional<Dynamic> fromString(String dynamicString) {
    return Exceptional.ofNullable(dynamicString).map(String::trim).filter(s -> !s.isEmpty()).flatMap(s -> {
      Dynamic dynamic;
      switch (s) {
        case "ppp":
          dynamic = PIANISSISSIMO;
          break;
        case "pp":
          dynamic = PIANISSIMO;
          break;
        case "p":
          dynamic = PIANO;
          break;
        case "mp":
          dynamic = MEZZO_PIANO;
          break;
        case "mf":
          dynamic = MEZZO_FORTE;
          break;
        case "f":
          dynamic = FORTE;
          break;
        case "ff":
          dynamic = FORTISSIMO;
          break;
        case "fff":
          dynamic = FORTISSISSIMO;
          break;
        default:
          dynamic = null;
      }
      return Exceptional.ofNullable(dynamic);
    });
  }

  @Override
  public final String toString() {
    return String.valueOf(velocity);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(velocity);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Dynamic)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Dynamic otherDynamic = (Dynamic) other;

    return Objects.deepEquals(velocity, otherDynamic.getVelocity());
  }

  public final int getVelocity() {
    return velocity;
  }
}