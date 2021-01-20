
package com.github.andrewthehan.etude.theory;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.CircularIterator;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.InfiniteIterator;
import com.github.andrewthehan.etude.util.StreamUtil;

public final class Chord {

  public enum Inversion {
    ROOT, FIRST, SECOND, THIRD;

    public static final int SIZE = Inversion.values().length;

    public final int getValue() {
      return ordinal();
    }
  }

  public enum Quality {
    // Obtained from https://en.wikipedia.org/wiki/Seventh_chord#Tertian
    MAJOR(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MAJOR, 3),
        new Interval(Interval.Quality.PERFECT, 5) }),
    MINOR(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
        new Interval(Interval.Quality.PERFECT, 5) }),
    DIMINISHED(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
        new Interval(Interval.Quality.DIMINISHED, 5) }),
    AUGMENTED(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MAJOR, 3),
        new Interval(Interval.Quality.AUGMENTED, 5) }),
    MAJOR_SEVENTH(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MAJOR, 3),
        new Interval(Interval.Quality.PERFECT, 5), new Interval(Interval.Quality.MAJOR, 7) }),
    MINOR_SEVENTH(new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
        new Interval(Interval.Quality.PERFECT, 5), new Interval(Interval.Quality.MINOR, 7) }),
    DOMINANT_SEVENTH(
        new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MAJOR, 3),
            new Interval(Interval.Quality.PERFECT, 5), new Interval(Interval.Quality.MINOR, 7) }),
    DIMINISHED_SEVENTH(
        new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
            new Interval(Interval.Quality.DIMINISHED, 5), new Interval(Interval.Quality.DIMINISHED, 7) }),
    HALF_DIMINISHED_SEVENTH(
        new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
            new Interval(Interval.Quality.DIMINISHED, 5), new Interval(Interval.Quality.MINOR, 7) }),
    MINOR_MAJOR_SEVENTH(
        new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MINOR, 3),
            new Interval(Interval.Quality.PERFECT, 5), new Interval(Interval.Quality.MAJOR, 7) }),
    AUGMENTED_MAJOR_SEVENTH(
        new Interval[] { new Interval(Interval.Quality.PERFECT, 1), new Interval(Interval.Quality.MAJOR, 3),
            new Interval(Interval.Quality.AUGMENTED, 5), new Interval(Interval.Quality.MAJOR, 7) });

    public static final int SIZE = Quality.values().length;

    private final Interval[] intervalPattern;

    private Quality(Interval[] intervalPattern) {
      this.intervalPattern = intervalPattern;
    }

    public final Interval[] getIntervalPattern() {
      return intervalPattern.clone();
    }
  }

  private final Pitch[] pitches;

  public Chord(Pitch[] pitches) {
    this.pitches = pitches;
  }

  public Chord(Pitch pitch, Quality quality) {
    this(pitch, quality, Inversion.ROOT);
  }

  public Chord(Pitch pitch, Quality quality, Inversion inversion) {
    pitches = Chord.builder().setRoot(pitch).add(quality).setInversion(inversion).build().pitches.clone();
  }

  public static final Exceptional<Chord> fromString(String chordString) {
    return EtudeParser.of(chordString).filter(Objects::nonNull, EtudeException.forNull(Accidental.class)).parse(s -> {
      if (!s.contains("[") || !s.contains("]")) {
        return Exceptional.empty(EtudeException.forInvalid(Chord.class, s, "missing brackets that enclose pitches"));
      }

      if (!s.startsWith("[") || !s.endsWith("]")) {
        return Exceptional.empty(EtudeException.forInvalid(Chord.class, s, "contains extra information"));
      }

      String pitchesString = s.substring(1, s.length() - 1);

      if (pitchesString.contains("[") || pitchesString.contains("]")) {
        return Exceptional.empty(EtudeException.forInvalid(Chord.class, s, "contains extra brackets"));
      }

      // Pitch[] pitches = Arrays
      // .stream(pitchesString.split(","))
      // .map(Pitch::fromString)
      // .filter(Exceptional::isPresent)
      // .map(Exceptional::get)
      // .toArray(Pitch[]::new);

      // traditional for loop because can't do generic array creation
      // (Exceptional<Pitch>[])
      String[] pitchStrings = pitchesString.split(",");
      Pitch[] pitches = new Pitch[pitchStrings.length];
      for (int i = 0; i < pitchStrings.length; ++i) {
        Exceptional<Pitch> pitch = Pitch.fromString(pitchStrings[i]);
        if (!pitch.isPresent()) {
          return Exceptional.empty(EtudeException.forInvalid(Chord.class, s, pitch.getException().getMessage()));
        }
        pitches[i] = pitch.get();
      }

      return Exceptional.of(pitches);
    }).get(a -> new Chord((Pitch[]) a[0]));
  }

  @Override
  public final String toString() {
    return Arrays.toString(pitches);
  }

  @Override
  public final int hashCode() {
    return Objects.hash((Object) pitches);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Chord)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Chord otherChord = (Chord) other;

    return Objects.deepEquals(pitches, otherChord.getPitches());
  }

  public final Stream<Pitch> stream() {
    return stream(Direction.DEFAULT);
  }

  public final Stream<Pitch> stream(Direction direction) {
    return StreamUtil.fromIterator(iterator(direction));
  }

  public final Iterator<Pitch> iterator() {
    return iterator(Direction.DEFAULT);
  }

  public final Iterator<Pitch> iterator(Direction direction) {
    Key[] keys = Arrays.stream(pitches).map(Pitch::getKey).toArray(Key[]::new);
    CircularIterator<Key> it = direction == Direction.ASCENDING ? CircularIterator.of(keys)
        : CircularIterator.of(keys).reverse();

    // skip first key because equal to initial pitch's (pitches[0]) key
    it.next();
    return InfiniteIterator.of(pitches[0],
        direction == Direction.ASCENDING ? previous -> previous.getHigherPitch(it.next()).get()
            : previous -> previous.getLowerPitch(it.next()).get());
  }

  public final Pitch[] getPitches() {
    return pitches.clone();
  }

  public static final RequiresRoot builder() {
    return new Builder();
  }

  public interface Base {
    Chord build();
  }

  public interface RequiresRoot {
    Manipulate setRoot(Pitch root);
  }

  public interface Manipulate extends Base {
    Manipulate add(Interval interval);

    default Manipulate add(Quality quality) {
      Arrays.stream(quality.getIntervalPattern()).forEach(this::add);
      return this;
    }

    End setInversion(Inversion inversion);

    End setBottomDegree(Degree degree);
  }

  public interface End extends Base {
  }

  private static final class Builder implements RequiresRoot, Manipulate, End {
    private final Set<Pitch> pitches;
    private Pitch root;
    private Degree bottomDegree;

    public Builder() {
      pitches = new TreeSet<Pitch>();
      bottomDegree = Degree.TONIC;
    }

    @Override
    public final Manipulate setRoot(Pitch root) {
      this.root = root;
      return this;
    }

    @Override
    public final Manipulate add(Interval interval) {
      // TODO: Exceptional#get() can fail here. Handle more elegantly (potentially
      // change Builder#build() to return an Exceptional?)
      pitches.add(root.step(interval).get());
      return this;
    }

    @Override
    public final End setInversion(Inversion inversion) {
      switch (inversion) {
        case ROOT:
          return setBottomDegree(Degree.TONIC);
        case FIRST:
          return setBottomDegree(Degree.MEDIANT);
        case SECOND:
          return setBottomDegree(Degree.DOMINANT);
        case THIRD:
          return setBottomDegree(Degree.LEADING_TONE);
        default:
          throw new AssertionError();
      }
    }

    @Override
    public final End setBottomDegree(Degree bottomDegree) {
      this.bottomDegree = bottomDegree;
      return this;
    }

    @Override
    public final Chord build() {
      Deque<Pitch> deque = new ArrayDeque<Pitch>(pitches);

      // arbitrary octave
      Scale scale = new Scale(new Pitch(root.getKey(), 4), Scale.Quality.MAJOR);
      Letter letter = scale.getPitches()[bottomDegree.getValue() - 1].getKey().getLetter();
      Exceptional<Pitch> exceptional = deque.stream().filter(p -> p.getKey().getLetter() == letter).findFirst()
          .map(Exceptional::of).orElseGet(Exceptional::empty);

      if (!exceptional.isPresent()) {
        throw EtudeException.forIllegalState(Chord.class, "Unable to invert chord: missing " + bottomDegree + " pitch");
      }

      Pitch lowestPitch = exceptional.get();

      // guaranteed to terminate because the exceptional above
      while (lowestPitch != deque.peekFirst()) {
        deque.offerLast(lowestPitch.getHigherPitch(deque.pollFirst().getKey()).get());
      }

      Pitch[] array = deque.toArray(new Pitch[0]);

      return new Chord(array);
    }
  }
}
