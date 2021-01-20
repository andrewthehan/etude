
package com.github.andrewthehan.etude.theory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;
import com.github.andrewthehan.etude.util.RegEx;

/*
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public final class Pitch implements Comparable<Pitch> {
  private final Key key;
  private final int octave;

  public Pitch(Key key, int octave) {
    this.key = key;
    this.octave = octave;

    int programNumber = getProgramNumber();
    if (programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER
        || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER) {
      throw EtudeException.forInvalid(Pitch.class, programNumber, "out of program number range");
    }
  }

  public final Pitch apply(KeySignature keySignature) {
    Key key = this.key.apply(keySignature);
    if (this.key == key) {
      return this;
    }
    return new Pitch(key, octave);
  }

  public final Exceptional<Pitch> step(int amount) {
    return step(amount, Policy.DEFAULT_PRIORITY);
  }

  public final Exceptional<Pitch> step(int amount, ImmutablePrioritySet<Policy> policies) {
    return Pitch.fromProgramNumber(getProgramNumber() + amount, policies);
  }

  public final Exceptional<Pitch> step(Interval interval) {
    // determine the letter
    List<Letter> list = Letter.asList(key.getLetter());
    Letter letter = list.get(Math.floorMod(interval.getNumber() - 1, Letter.SIZE));

    // initialize accidental to be the accidental of the new letter in the key
    // signature of this key
    Optional<Accidental> accidental = new Key(letter).apply(KeySignature.fromKey(key, KeySignature.Quality.MAJOR))
        .getAccidental();

    int accidentalOffset = accidental.map(Accidental::getOffset).orElse(0);
    // change accidental based on interval's quality
    switch (interval.getQuality()) {
      case PERFECT:
      case MAJOR:
        break;
      case MINOR:
        accidental = Accidental.fromOffset(accidentalOffset - 1).map(Optional::of).orElse(Optional.empty());
        break;
      case DIMINISHED:
        accidental = Accidental.fromOffset(accidentalOffset - (Interval.isPerfect(interval.getNumber()) ? 1 : 2))
            .map(Optional::of).orElse(Optional.empty());
        break;
      case DOUBLY_DIMINISHED:
        accidental = Accidental.fromOffset(accidentalOffset - (Interval.isPerfect(interval.getNumber()) ? 2 : 3))
            .map(Optional::of).orElse(Optional.empty());
        break;
      case AUGMENTED:
        accidental = Accidental.fromOffset(accidentalOffset + 1).map(Optional::of).orElse(Optional.empty());
        break;
      case DOUBLY_AUGMENTED:
        accidental = Accidental.fromOffset(accidentalOffset + 2).map(Optional::of).orElse(Optional.empty());
        break;
    }

    // prefer null over natural
    accidental = accidental.filter(a -> a != Accidental.NATURAL);

    // refer to Interval.between for how this equation was derived
    int octaveOffset = (interval.getNumber() - 1 + (Math.floorMod(this.getKey().getLetter().ordinal() - 2, Letter.SIZE)
        - Math.floorMod(letter.ordinal() - 2, Letter.SIZE))) / Letter.SIZE;

    return Exceptional.of(new Pitch(new Key(letter, accidental), octave + octaveOffset));
  }

  public final Exceptional<Pitch> halfStepUp() {
    return halfStepUp(Policy.DEFAULT_PRIORITY);
  }

  public final Exceptional<Pitch> halfStepUp(ImmutablePrioritySet<Policy> policies) {
    return Pitch.fromProgramNumber(getProgramNumber() + 1, policies);
  }

  public final Exceptional<Pitch> halfStepDown() {
    return halfStepDown(Policy.DEFAULT_PRIORITY);
  }

  public final Exceptional<Pitch> halfStepDown(ImmutablePrioritySet<Policy> policies) {
    return Pitch.fromProgramNumber(getProgramNumber() - 1, policies);
  }

  public final Exceptional<Pitch> getHigherPitch(Key key) {
    // TODO: remove try catch
    try {
      Pitch pitch = new Pitch(key, octave);
      // should never loop more than twice
      while (!pitch.isHigherThan(this)) {
        pitch = new Pitch(key, pitch.getOctave() + 1);
      }
      return Exceptional.of(pitch);
    } catch (EtudeException e) {
      return Exceptional.empty();
    }
  }

  public final Exceptional<Pitch> getLowerPitch(Key key) {
    // TODO: remove try catch
    try {
      Pitch pitch = new Pitch(key, octave);
      // should never loop more than twice
      while (!pitch.isLowerThan(this)) {
        pitch = new Pitch(key, pitch.getOctave() - 1);
      }
      return Exceptional.of(pitch);
    } catch (EtudeException e) {
      return Exceptional.empty();
    }
  }

  public final boolean isHigherThan(Pitch pitch) {
    return getProgramNumber() > pitch.getProgramNumber();
  }

  public final boolean isLowerThan(Pitch pitch) {
    return getProgramNumber() < pitch.getProgramNumber();
  }

  @Override
  public final int compareTo(Pitch other) {
    return Integer.compare(getProgramNumber(), other.getProgramNumber());
  }

  public static final boolean isEnharmonic(Pitch a, Pitch b) {
    return a.getProgramNumber() == b.getProgramNumber();
  }

  public static final Exceptional<Pitch> fromProgramNumber(int programNumber) {
    return Pitch.fromProgramNumber(programNumber, Policy.DEFAULT_PRIORITY);
  }

  public static final Exceptional<Pitch> fromProgramNumber(int programNumber, ImmutablePrioritySet<Policy> policies) {
    return EtudeParser.of(programNumber)
        .filter(p -> MusicConstants.SMALLEST_PROGRAM_NUMBER <= p && p <= MusicConstants.LARGEST_PROGRAM_NUMBER,
            EtudeException.forInvalid(Pitch.class, programNumber, "out of range"))
        .parse(p -> {
          Exceptional<Key> key = Key.fromOffset(Math.floorMod(p, MusicConstants.KEYS_IN_OCTAVE), policies);
          if (!key.isPresent()) {
            return Exceptional.empty(key.getException());
          }

          Key actualKey = key.get();
          int octave = p / MusicConstants.KEYS_IN_OCTAVE;
          /**
           * Key offsets are bounded by the range [0, MusicConstants.KEYS_IN_OCTAVE)
           * whereas program numbers go across octave boundaries. If [actual key offset]
           * is equal to [offset after normalizing], then octave is not changed. If
           * [actual key offset] is lower than [offset after normalizing], then octave is
           * raised by 1. If [actual key offset] is higher than [offset after
           * normalizing], then octave is lowered by 1.
           */
          octave += (actualKey.getOffset()
              - (actualKey.getLetter().getOffset() + actualKey.getAccidental().map(Accidental::getOffset).orElse(0)))
              / MusicConstants.KEYS_IN_OCTAVE;
          return Exceptional.of(new Pitch(actualKey, octave));
        }).get(a -> (Pitch) a[0]);
  }

  public final int getProgramNumber() {
    return octave * MusicConstants.KEYS_IN_OCTAVE + key.getLetter().getOffset()
        + key.getAccidental().map(Accidental::getOffset).orElse(0);
  }

  /**
   * Any input in the form - ${key}${octave} - ${key}${octave}(${program number})
   * is accepted and converted into a Pitch. ${program number} is intentionally
   * not accepted because #fromProgramNumber exists and should be used instead.
   *
   * @param pitchString the string to be parsed
   * @return an optional Pitch representative of the pitchString
   */
  public static final Exceptional<Pitch> fromString(String pitchString) {
    return EtudeParser.of(pitchString).filter(Objects::nonNull, EtudeException.forNull(Pitch.class)).parse(s -> {
      // longest prefix that contains only letters or #
      String keyString = RegEx.extract("^[a-zA-Z#]*", s);
      return Key.fromString(keyString);
    }).parse(s -> Exceptional
        // first number of length greater than 0 thats followed by an open parentheses
        // (if there is any)
        .of(RegEx.extract("\\d+(?![^(]*\\))", s),
            EtudeException.forInvalid(Pitch.class, pitchString, "doesn't contain a valid octave"))
        .map(Integer::parseInt)).get(a -> new Pitch((Key) a[0], (int) a[1])).filter(p -> {
          // a number that has an open parentheses somewhere before it
          String programNumber = RegEx.extract("(?<=\\()\\d+", pitchString);
          return programNumber == null || p.getProgramNumber() == Integer.parseInt(programNumber);
        }, EtudeException.forInvalid(Pitch.class, pitchString, "program number doesn't match key and octave"))
        .filter(p -> {
          String converted = p.toString();

          String programNumber = RegEx.extract("(?<=\\()\\d+", pitchString);
          if (programNumber == null) {
            converted = converted.substring(0, converted.indexOf("("));
          }

          return converted.equals(pitchString);
        }, EtudeException.forInvalid(Pitch.class, pitchString));
  }

  @Override
  public final String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(key);
    builder.append(octave);
    builder.append("(");
    builder.append(getProgramNumber());
    builder.append(")");
    return builder.toString();
  }

  @Override
  public final int hashCode() {
    return Objects.hash(key, octave);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Pitch)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Pitch otherPitch = (Pitch) other;

    return Objects.deepEquals(new Object[] { key, octave },
        new Object[] { otherPitch.getKey(), otherPitch.getOctave() });
  }

  public final Key getKey() {
    return key;
  }

  public final int getOctave() {
    return octave;
  }
}
