
package com.github.andrewthehan.etude.theory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;
import com.github.andrewthehan.etude.util.StreamUtil;

/*
 * Letter with the concept of accidental
 */
public final class Key {
  private final Letter letter;
  private final Optional<Accidental> accidental;

  public Key(Letter letter) {
    this(letter, Optional.empty());
  }

  public Key(Letter letter, Accidental accidental) {
    this(letter, Optional.ofNullable(accidental));
  }

  public Key(Letter letter, Optional<Accidental> accidental) {
    this.letter = letter;
    this.accidental = accidental;
  }

  public Key(Letter letter, Exceptional<Accidental> accidental) {
    this.letter = letter;
    this.accidental = accidental.map(Optional::of).orElse(Optional.empty());
  }

  public final Exceptional<Key> step(int amount) {
    return step(amount, Policy.DEFAULT_PRIORITY);
  }

  public final Exceptional<Key> step(int amount, ImmutablePrioritySet<Policy> policies) {
    return Key.fromOffset(Math.floorMod(getOffset() + amount, MusicConstants.KEYS_IN_OCTAVE), policies);
  }

  public final Key apply(KeySignature keySignature) {
    Optional<Accidental> accidental = keySignature.getAccidentalFor(letter);

    if (this.accidental.equals(accidental)) {
      return this;
    }

    return new Key(letter, accidental);
  }

  public final Key removeAccidental() {
    if (!hasAccidental()) {
      return this;
    }
    return new Key(letter);
  }

  public final Key natural() {
    if (isNatural()) {
      return this;
    }
    return new Key(letter, Accidental.NATURAL);
  }

  public final Key sharp() {
    if (isSharp()) {
      return this;
    }
    return new Key(letter, Accidental.SHARP);
  }

  public final Key doubleSharp() {
    if (isDoubleSharp()) {
      return this;
    }
    return new Key(letter, Accidental.DOUBLE_SHARP);
  }

  public final Key tripleSharp() {
    if (isTripleSharp()) {
      return this;
    }
    return new Key(letter, Accidental.TRIPLE_SHARP);
  }

  public final Key flat() {
    if (isFlat()) {
      return this;
    }
    return new Key(letter, Accidental.FLAT);
  }

  public final Key doubleFlat() {
    if (isDoubleFlat()) {
      return this;
    }
    return new Key(letter, Accidental.DOUBLE_FLAT);
  }

  public final Key tripleFlat() {
    if (isTripleFlat()) {
      return this;
    }
    return new Key(letter, Accidental.TRIPLE_FLAT);
  }

  public final boolean hasAccidental() {
    return accidental.isPresent();
  }

  public final boolean isNatural() {
    return accidental.orElse(null) == Accidental.NATURAL;
  }

  public final boolean isSharp() {
    return accidental.orElse(null) == Accidental.SHARP;
  }

  public final boolean isDoubleSharp() {
    return accidental.orElse(null) == Accidental.DOUBLE_SHARP;
  }

  public final boolean isTripleSharp() {
    return accidental.orElse(null) == Accidental.TRIPLE_SHARP;
  }

  public final boolean isFlat() {
    return accidental.orElse(null) == Accidental.FLAT;
  }

  public final boolean isDoubleFlat() {
    return accidental.orElse(null) == Accidental.DOUBLE_FLAT;
  }

  public final boolean isTripleFlat() {
    return accidental.orElse(null) == Accidental.TRIPLE_FLAT;
  }

  public final Exceptional<Key> getEnharmonicEquivalent(Letter letter) {
    return Exceptional.ofNullable(letter).flatMap(l -> {
      if (this.letter == letter) {
        return Exceptional.of(this);
      }

      int targetOffset = getOffset();
      int initialOffset = letter.getOffset();

      int accidentalOffset = targetOffset - initialOffset;

      // assuming key is above, check if some sharp can offset enough
      if (accidentalOffset > Accidental.TRIPLE_SHARP.getOffset()) {
        // assumption was wrong, now assume key is below
        accidentalOffset -= MusicConstants.KEYS_IN_OCTAVE;
        // check if some flat can offset enough
        if (accidentalOffset < Accidental.TRIPLE_FLAT.getOffset()) {
          return Exceptional.empty(EtudeException.forInvalid(Key.class, accidentalOffset));
        }
      } else if (accidentalOffset < Accidental.TRIPLE_FLAT.getOffset()) {
        accidentalOffset += MusicConstants.KEYS_IN_OCTAVE;
        if (accidentalOffset > Accidental.TRIPLE_SHARP.getOffset()) {
          return Exceptional.empty(EtudeException.forInvalid(Key.class, accidentalOffset));
        }
      }

      Exceptional<Accidental> accidental = Accidental.fromOffset(accidentalOffset);
      return Exceptional.of(new Key(letter, accidental));
    });
  }

  public final Exceptional<Key> getEnharmonicEquivalent(ImmutablePrioritySet<Policy> policies) {
    // all keys that are enharmonic equivalents
    Key[] keys = Letter.stream().limit(Letter.SIZE).map(this::getEnharmonicEquivalent).filter(Exceptional::isPresent)
        .map(Exceptional::get).toArray(Key[]::new);

    // find the first policy that tests true for some enharmonic key and return that
    // key
    return StreamUtil.fromIterator(policies.iterator())
        .map(policy -> Arrays.stream(keys).filter(policy::test).findFirst().map(Exceptional::of)
            .orElseGet(Exceptional::empty))
        .filter(Exceptional::isPresent).map(Exceptional::get).findFirst().map(Exceptional::of)
        .orElseGet(Exceptional::empty).withException(
            EtudeException.forInvalid(Key.class, "unable to find an enharmonic equivalent for the given policies"));
  }

  public static final boolean isEnharmonic(Key a, Key b) {
    return a.getOffset() == b.getOffset();
  }

  public static final Exceptional<Key> fromOffset(int offset) {
    return Key.fromOffset(offset, Policy.DEFAULT_PRIORITY);
  }

  public static final Exceptional<Key> fromOffset(int offset, ImmutablePrioritySet<Policy> policies) {
    if (policies.isEmpty()) {
      throw EtudeException.forIllegalArgument(Key.class, policies, "should not be empty");
    }

    Letter letter = null;
    Accidental accidental = null;

    // determine key without taking into account policies
    // maintain order of cases for fall throughs to function correctly
    switch (Math.floorMod(offset, MusicConstants.KEYS_IN_OCTAVE)) {
      case 11:
        letter = Letter.B;
        break;
      case 10:
        accidental = Accidental.SHARP;
        // fall through
      case 9:
        letter = Letter.A;
        break;
      case 8:
        accidental = Accidental.SHARP;
        // fall through
      case 7:
        letter = Letter.G;
        break;
      case 6:
        accidental = Accidental.SHARP;
        // fall through
      case 5:
        letter = Letter.F;
        break;
      case 4:
        letter = Letter.E;
        break;
      case 3:
        accidental = Accidental.SHARP;
        // fall through
      case 2:
        letter = Letter.D;
        break;
      case 1:
        accidental = Accidental.SHARP;
        // fall through
      case 0:
        letter = Letter.C;
        break;
      default:
        throw new AssertionError();
    }

    return new Key(letter, accidental).getEnharmonicEquivalent(policies);
  }

  public final int getOffset() {
    return Math.floorMod(letter.getOffset() + accidental.map(Accidental::getOffset).orElse(0),
        MusicConstants.KEYS_IN_OCTAVE);
  }

  // TODO: remove unchecked
  @SuppressWarnings("unchecked")
  public static final Exceptional<Key> fromString(String keyString) {
    return EtudeParser.of(keyString).filter(Objects::nonNull, EtudeException.forNull(Key.class))
        .filter(s -> !s.trim().isEmpty(), EtudeException.forInvalid(Key.class, keyString, "empty string"))
        .parse(s -> Letter.fromChar(s.charAt(0)))
        .parse(s -> Exceptional.of(Accidental.fromString(s.length() == 1 ? null : s.substring(1))))
        .get(a -> new Key((Letter) a[0], (Exceptional<Accidental>) a[1]));
  }

  @Override
  public final String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(letter);
    builder.append(accidental.map(Accidental::toString).orElse(""));
    return builder.toString();
  }

  @Override
  public final int hashCode() {
    return Objects.hash(letter, accidental.orElse(null));
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Key)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Key otherKey = (Key) other;

    return Objects.deepEquals(new Object[] { letter, accidental.orElse(null) },
        new Object[] { otherKey.getLetter(), otherKey.getAccidental().orElse(null) });
  }

  public final Letter getLetter() {
    return letter;
  }

  public final Optional<Accidental> getAccidental() {
    return accidental;
  }
}
