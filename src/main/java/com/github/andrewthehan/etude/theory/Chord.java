
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;

import java.util.stream.Collectors;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public final class Chord{
  public enum Quality{
    // Obtained from https://en.wikipedia.org/wiki/Seventh_chord#Tertian
    MAJOR(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5)
    }),
    MINOR(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5)
    }),
    DIMINISHED(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5)
    }),
    AUGMENTED(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.AUGMENTED, 5)
    }),
    MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    }),
    MINOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }),
    DOMINANT_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }),
    DIMINISHED_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5),
      new Interval(Interval.Quality.DIMINISHED, 7)
    }),
    HALF_DIMINISHED_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }),
    MINOR_MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    }),
    AUGMENTED_MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.AUGMENTED, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    });

    private final Interval[] intervalPattern;

    private Quality(Interval[] intervalPattern){
      this.intervalPattern = intervalPattern;
    }

    public final Interval[] getIntervalPattern(){
      return intervalPattern;
    }
  }

  private final Pitch[] pitches;

  public Chord(Pitch[] pitches){
    this.pitches = pitches;
  }

  public Chord(Pitch pitch, Quality quality){
    this(pitch, quality, Inversion.ROOT);
  }

  public Chord(Pitch pitch, Quality quality, Inversion inversion){
    pitches = Chord
      .builder()
      .setRoot(pitch)
      .add(quality)
      .setInversion(inversion)
      .build()
      .pitches
      .clone();
  }

  public static final Chord fromString(String chordString){
    if(chordString == null){
      throw new EtudeException("Invalid chord string: " + chordString);
    }
    else if(chordString.trim().isEmpty()){
      throw new EtudeException("Invalid chord string: " + chordString + " (blank)");
    }

    if(!chordString.contains("[") || !chordString.contains("]")){
      throw new EtudeException("Invalid chord string: " + chordString + " (missing brackets that enclose pitches)");
    }

    if(!chordString.startsWith("[") || !chordString.endsWith("]")){
      throw new EtudeException("Invalid chord string: " + chordString + " (contains extra information)");
    }

    String pitchesString = chordString.substring(1, chordString.length() - 1);

    if(pitchesString.contains("[") || pitchesString.contains("]")){
      throw new EtudeException("Invalid chord string: " + chordString + " (contains extra brackets)");
    }

    Pitch[] pitches = Arrays
      .stream(pitchesString.split(","))
      .map(s -> s.trim())
      .map(Pitch::fromString)
      .toArray(Pitch[]::new);

    return new Chord(pitches);
  }

  @Override
  public String toString(){
    return Arrays.toString(pitches);
  }

  public final Pitch[] getPitches(){
    return pitches;
  }
  
  public static RequiresRoot builder(){
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

    default Manipulate add(Quality quality){
      Arrays
        .stream(quality.getIntervalPattern())
        .forEach(this::add);
      return this;
    }

    End setInversion(Inversion inversion);

    End setBottomDegree(Degree degree);
  }

  public interface End extends Base {
  }

  private static class Builder implements Base, RequiresRoot, Manipulate, End {
    private Set<Pitch> pitches;
    private Pitch root;
    private Degree bottomDegree;

    public Builder(){
      pitches = new TreeSet<Pitch>();
      bottomDegree = Degree.TONIC;
    }

    @Override
    public Manipulate setRoot(Pitch root){
      this.root = root;
      return this;
    }

    @Override
    public Manipulate add(Interval interval){
      pitches.add(root.step(interval));
      return this;
    }

    @Override
    public End setInversion(Inversion inversion){
      switch(inversion){
        case ROOT: return setBottomDegree(Degree.TONIC);
        case FIRST: return setBottomDegree(Degree.MEDIANT);
        case SECOND: return setBottomDegree(Degree.DOMINANT);
        case THIRD: return setBottomDegree(Degree.LEADING_TONE);
        default: throw new AssertionError();
      }
    }

    @Override
    public End setBottomDegree(Degree bottomDegree){
      this.bottomDegree = bottomDegree;
      return this;
    }

    @Override
    public Chord build(){
      Deque<Pitch> deque = new ArrayDeque<Pitch>(pitches);

      KeySignature keySignature = new KeySignature(root.getKey(), KeySignature.Quality.MAJOR);
      Letter letter = keySignature.keyOf(bottomDegree).getLetter();
      Optional<Pitch> optional = deque
        .stream()
        .filter(p -> p.getKey().getLetter() == letter)
        .findFirst();

      if(!optional.isPresent()){
        throw new EtudeException("Unable to invert chord: missing " + bottomDegree + " pitch");
      } 

      Pitch lowestPitch = optional.get();

      while(lowestPitch != deque.peekFirst()){
        deque.offerLast(lowestPitch.getHigherPitch(deque.pollFirst().getKey()));
      }

      Pitch[] array = deque.toArray(new Pitch[0]);

      return new Chord(array);
    }
  }
}
