
package com.github.andrewthehan.etude.theory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.ArrayUtil;
import com.github.andrewthehan.etude.util.CircularIterator;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;
import com.github.andrewthehan.etude.util.InfiniteIterator;
import com.github.andrewthehan.etude.util.StreamUtil;

public final class Scale {
  public static final class Quality {
    public static final Quality MAJOR = new Quality(new int[] { 2, 2, 1, 2, 2, 2, 1 });
    public static final Quality NATURAL_MINOR = new Quality(new int[] { 2, 1, 2, 2, 1, 2, 2 });
    public static final Quality HARMONIC_MINOR = new Quality(new int[] { 2, 1, 2, 2, 1, 3, 1 });
    public static final Quality MELODIC_MINOR = new Quality(new int[] { 2, 1, 2, 2, 2, 2, 1 },
        new int[] { -2, -2, -1, -2, -2, -1, -2 });
    public static final Quality CHROMATIC = new Quality(new int[] { 1 });
    public static final Quality WHOLE_TONE = new Quality(new int[] { 2 });

    private final int[] ascending;
    private final int[] descending;

    public Quality(int[] stepPattern) {
      // reverse array and negate all values (e.g. [1, 3, 5] -> [-5, -3, -1])
      this(stepPattern, IntStream.of(ArrayUtil.reverse(stepPattern)).map(Math::negateExact).toArray());
    }

    public Quality(int[] ascending, int[] descending) {
      if (ascending.length == 0) {
        throw EtudeException.forIllegalArgument(Quality.class, ascending, "step pattern array should not be empty");
      }
      this.ascending = ascending;
      this.descending = descending;
    }

    public final int[] getStepPattern() {
      return getStepPattern(Direction.DEFAULT);
    }

    public final int[] getStepPattern(Direction direction) {
      switch (direction) {
        case ASCENDING:
          return ascending.clone();
        case DESCENDING:
          return descending.clone();
        default:
          throw new AssertionError();
      }
    }

    public final boolean isOctaveRepeating() {
      return isOctaveRepeating(Direction.DEFAULT);
    }

    public final boolean isOctaveRepeating(Direction direction) {
      return Math.abs(IntStream.of(getStepPattern(direction)).sum()) == MusicConstants.KEYS_IN_OCTAVE;
    }

    @Override
    public final int hashCode() {
      return Objects.hash(ascending, descending);
    }

    @Override
    public final boolean equals(Object other) {
      if (!(other instanceof Quality)) {
        return false;
      }
      if (other == this) {
        return true;
      }

      Quality otherQuality = (Quality) other;

      return Objects.deepEquals(new Object[] { ascending, descending }, new Object[] {
          otherQuality.getStepPattern(Direction.ASCENDING), otherQuality.getStepPattern(Direction.DESCENDING) });
    }
  }

  private final Pitch pitch;
  private final Quality quality;

  public Scale(Pitch pitch, int[] stepPattern) {
    this(pitch, new Quality(stepPattern));
  }

  public Scale(Pitch pitch, int[] ascending, int[] descending) {
    this(pitch, new Quality(ascending, descending));
  }

  public Scale(Pitch pitch, Quality quality) {
    this.pitch = pitch;
    this.quality = quality;
  }

  public final ImmutablePrioritySet<Policy> getDefaultPolicy(Direction direction) {
    if (quality.isOctaveRepeating(direction) && quality.getStepPattern(direction).length == Letter.SIZE) {
      // prioritize unique letters
      Iterator<Letter> it = Letter.iterator(direction, pitch.getKey().getLetter());
      return Policy.prioritize(new Policy() {
        private Letter current = it.next();

        @Override
        public boolean test(Key k) {
          boolean pass = k.getLetter() == current;
          // each time the current key finds a match, go to the next letter
          if (pass) {
            current = it.next();
          }
          return pass;
        }
      });
    } else {
      return Policy.DEFAULT_PRIORITY;
    }
  }

  public final Stream<Pitch> stream() {
    return stream(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Stream<Pitch> stream(Direction direction) {
    return stream(direction, getDefaultPolicy(direction));
  }

  public final Stream<Pitch> stream(ImmutablePrioritySet<Policy> policies) {
    return stream(Direction.DEFAULT, policies);
  }

  public final Stream<Pitch> stream(Direction direction, ImmutablePrioritySet<Policy> policies) {
    return StreamUtil.fromIterator(iterator(direction, policies));
  }

  public final Iterator<Pitch> iterator() {
    return iterator(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Iterator<Pitch> iterator(Direction direction) {
    return iterator(direction, getDefaultPolicy(direction));
  }

  public final Iterator<Pitch> iterator(ImmutablePrioritySet<Policy> policies) {
    return iterator(Direction.DEFAULT, policies);
  }

  public final Iterator<Pitch> iterator(Direction direction, ImmutablePrioritySet<Policy> policies) {
    CircularIterator<Integer> it = CircularIterator.of(quality.getStepPattern(direction));
    return InfiniteIterator.of(Pitch.fromProgramNumber(pitch.getProgramNumber(), policies).get(),
        previous -> previous.step(it.next(), policies).get());
  }

  public final Pitch[] getPitches() {
    return getPitches(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Pitch[] getPitches(Direction direction) {
    return getPitches(direction, getDefaultPolicy(direction));
  }

  public final Pitch[] getPitches(ImmutablePrioritySet<Policy> policies) {
    return getPitches(Direction.DEFAULT, policies);
  }

  public final Pitch[] getPitches(Direction direction, ImmutablePrioritySet<Policy> policies) {
    return stream(direction, policies).limit(quality.getStepPattern(direction).length).toArray(Pitch[]::new);
  }

  @Override
  public final String toString() {
    return toString(Direction.DEFAULT);
  }

  public final String toString(Direction direction) {
    return Arrays.toString(getPitches(direction));
  }

  @Override
  public final int hashCode() {
    return Objects.hash(pitch, quality);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Scale)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Scale otherScale = (Scale) other;

    return Objects.deepEquals(new Object[] { pitch, quality },
        new Object[] { otherScale.getPitch(), otherScale.getQuality() });
  }

  public final Pitch getPitch() {
    return pitch;
  }

  public final Quality getQuality() {
    return quality;
  }
}