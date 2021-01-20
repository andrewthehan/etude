
package com.github.andrewthehan.etude.theory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

public final class KeySignature {
  public enum Quality {
    MAJOR, MINOR;

    public static final int SIZE = Quality.values().length;
  }

  private static final int RELATIVE_MAJOR_TO_MINOR_DISTANCE = -3;

  private static final Letter[] ORDER_OF_FLATS = { Letter.B, Letter.E, Letter.A, Letter.D, Letter.G, Letter.C,
      Letter.F };
  private static final Letter[] ORDER_OF_SHARPS = { Letter.F, Letter.C, Letter.G, Letter.D, Letter.A, Letter.E,
      Letter.B };

  /**
   * Represents the accidentals in the order that they appear in notation. Each
   * key must have an accidental that is not null.
   *
   * Ex. [F#, C#] First accidental is a sharp on F. Second accidental is a sharp
   * on C.
   */
  private final Key[] accidentals;

  public KeySignature(Key[] accidentals) {
    if (Arrays.stream(accidentals).map(Key::getAccidental).anyMatch(a -> a.map(Accidental::getOffset).orElse(0) == 0)) {
      throw EtudeException.forInvalid(KeySignature.class, Arrays.toString(accidentals), "null or Accidental.NATURAL");
    } else if (accidentals.length != Arrays.stream(accidentals).map(Key::getLetter).distinct().count()) {
      throw EtudeException.forInvalid(KeySignature.class, Arrays.toString(accidentals),
          "all letters should be distinct");
    }

    this.accidentals = accidentals;
  }

  public final int getAccidentalCount() {
    return accidentals.length;
  }

  public final Optional<Accidental> getAccidentalFor(Letter letter) {
    return Arrays.stream(accidentals).filter(k -> k.getLetter() == letter).map(Key::getAccidental).findFirst()
        .orElseGet(Optional::empty);
  }

  public static final Letter[] getOrderOfFlats() {
    return KeySignature.getOrderOfFlats(Letter.SIZE);
  }

  public static final Letter[] getOrderOfFlats(int count) {
    return Arrays.copyOfRange(KeySignature.ORDER_OF_FLATS, 0, count);
  }

  public static final Letter[] getOrderOfSharps() {
    return KeySignature.getOrderOfSharps(Letter.SIZE);
  }

  public static final Letter[] getOrderOfSharps(int count) {
    return Arrays.copyOfRange(KeySignature.ORDER_OF_SHARPS, 0, count);
  }

  public static final Exceptional<Key> getKeyFor(Accidental accidental, int count, Quality quality) {
    if (count < 0 || count > Letter.SIZE) {
      return Exceptional.empty(EtudeException.forInvalid(KeySignature.class, count, "out of range"));
    } else if (count == 0 ^ (accidental == null || accidental == Accidental.NATURAL)) {
      return Exceptional.empty(EtudeException.forIllegalArgument(KeySignature.class, accidental,
          "key signature with 0 accidentals should have null or Accidental.NATURAL"));
    }

    Key key;
    Letter letter;
    // determine the key assuming quality is MAJOR
    switch (accidental == null ? Accidental.NATURAL : accidental) {
      case FLAT:
        letter = ORDER_OF_FLATS[Math.floorMod(count - 2, Letter.SIZE)];
        key = new Key(letter,
            // accidental; if flats for key signature contain the letter, make the key flat
            Arrays.stream(ORDER_OF_FLATS).limit(count).anyMatch(l -> l == letter) ? Accidental.FLAT : null);
        break;
      case SHARP:
        letter = ORDER_OF_SHARPS[Math.floorMod(count + 1, Letter.SIZE)];
        key = new Key(letter,
            // accidental; if sharps for key signature contain the letter, make the key
            // sharp
            Arrays.stream(ORDER_OF_SHARPS).limit(count).anyMatch(l -> l == letter) ? Accidental.SHARP : null);
        break;
      case NATURAL:
        letter = Letter.C;
        key = new Key(letter);
        break;
      default:
        return Exceptional.empty(EtudeException.forIllegalArgument(KeySignature.class, accidental,
            "should use null, Accidental.NATURAL, Accidental.FLAT, or Accidental.SHARP for key signatures"));
    }

    if (quality == Quality.MINOR) {
      ImmutablePrioritySet<Policy> policies = count == 0 ? Policy.prioritize(Policy.NONE_OR_NATURAL)
          : Policy.prioritize(Policy.NONE_OR_NATURAL, accidental == Accidental.FLAT ? Policy.FLAT : Policy.SHARP);

      Exceptional<Key> optionalKey = key.step(RELATIVE_MAJOR_TO_MINOR_DISTANCE, policies);
      if (!optionalKey.isPresent()) {
        throw new AssertionError();
      }
      key = optionalKey.get();
    }

    return Exceptional.of(key);
  }

  public static final Exceptional<KeySignature> fromAccidentals(Accidental accidental, int count) {
    if (count < 0 || count > Letter.SIZE) {
      return Exceptional.empty(EtudeException.forInvalid(KeySignature.class, count, "out of range"));
    } else if (count == 0 ^ (accidental == null || accidental == Accidental.NATURAL)) {
      return Exceptional.empty(EtudeException.forIllegalArgument(KeySignature.class, accidental,
          "key signature with 0 accidentals should have null or Accidental.NATURAL"));
    }

    Letter[] letters;
    switch (accidental == null ? Accidental.NATURAL : accidental) {
      case FLAT:
        letters = KeySignature.getOrderOfFlats(count);
        break;
      case SHARP:
        letters = KeySignature.getOrderOfSharps(count);
        break;
      case NATURAL:
        letters = new Letter[0];
        break;
      default:
        return Exceptional.empty(EtudeException.forIllegalArgument(KeySignature.class, accidental,
            "should use null, Accidental.NATURAL, Accidental.FLAT, or Accidental.SHARP for key signatures"));
    }

    Key[] accidentals = Arrays.stream(letters).map(l -> new Key(l, accidental)).toArray(Key[]::new);

    return Exceptional.of(new KeySignature(accidentals));
  }

  public static final KeySignature fromKey(Key key, Quality quality) {
    // arbitrary octave
    Key[] accidentals = new Scale(new Pitch(key, 4),
        quality == Quality.MAJOR ? Scale.Quality.MAJOR : Scale.Quality.NATURAL_MINOR).stream().limit(Letter.SIZE)
            .map(Pitch::getKey).filter(k -> k.hasAccidental() && !k.isNatural()).toArray(Key[]::new);
    if (accidentals.length != 0) {
      accidentals[0].getAccidental().filter(a -> a == Accidental.FLAT || a == Accidental.SHARP).map(a -> {
        switch (a) {
          case FLAT:
            return Arrays.asList(ORDER_OF_FLATS);
          case SHARP:
            return Arrays.asList(ORDER_OF_SHARPS);
          default:
            throw new AssertionError();
        }
      }).ifPresent(ordered -> Arrays.sort(accidentals,
          (a, b) -> Integer.compare(ordered.indexOf(a.getLetter()), ordered.indexOf(b.getLetter()))));
    }
    return new KeySignature(accidentals);
  }

  @Override
  public final String toString() {
    return Arrays.toString(accidentals);
  }

  @Override
  public final int hashCode() {
    return Objects.hash((Object) accidentals);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof KeySignature)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    KeySignature otherKeySignature = (KeySignature) other;

    return Objects.deepEquals(accidentals, otherKeySignature.getAccidentals());
  }

  public final Key[] getAccidentals() {
    return accidentals;
  }
}
