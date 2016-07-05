
package jmtapi.theory;

import java.util.stream.Collectors;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public final class Chord{
  public enum Quality{
    MAJOR(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5)
    }, "maj"),
    MINOR(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5)
    }, "min"),
    DIMINISHED(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5)
    }, "dim"),
    AUGMENTED(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.AUGMENTED, 5)
    }, "aug"),
    MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    }, "maj7"),
    MINOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }, "min7"),
    DOMINANT_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }, "7"),
    DIMINISHED_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5),
      new Interval(Interval.Quality.DIMINISHED, 7)
    }, "dim7"),
    HALF_DIMINISHED_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.DIMINISHED, 5),
      new Interval(Interval.Quality.MINOR, 7)
    }, "mMaj7"),
    MINOR_MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MINOR, 3),
      new Interval(Interval.Quality.PERFECT, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    }, "min7"),
    AUGMENTED_MAJOR_SEVENTH(new Interval[]{
      new Interval(Interval.Quality.PERFECT, 1),
      new Interval(Interval.Quality.MAJOR, 3),
      new Interval(Interval.Quality.AUGMENTED, 5),
      new Interval(Interval.Quality.MAJOR, 7)
    }, "min7");

    private final Interval[] intervalPattern;
    private final String symbol;

    private Quality(Interval[] intervalPattern, String symbol){
      this.intervalPattern = intervalPattern;
      this.symbol = symbol;
    }

    public final Interval[] getIntervalPattern(){
      return intervalPattern;
    }

    @Override
    public String toString(){
      return symbol;
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
      .pitches;
  }

  public static final Chord fromString(String chordString){
    if(chordString == null){
      throw new RuntimeException("Invalid chord string: " + chordString);
    }
    else if(chordString.trim().isEmpty()){
      throw new RuntimeException("Invalid chord string: " + chordString + " (blank)");
    }

    if(!chordString.contains("{") || !chordString.contains("}")){
      throw new RuntimeException("Invalid chord string: " + chordString + " (missing curly braces that enclose pitches)");
    }

    if(!chordString.startsWith("{") || !chordString.endsWith("}")){
      throw new RuntimeException("Invalid chord string: " + chordString + " (contains extra information)");
    }

    String pitchesString = chordString.substring(1, chordString.length() - 1);

    if(pitchesString.contains("{") || pitchesString.contains("}")){
      throw new RuntimeException("Invalid chord string: " + chordString + " (contains extra curly braces)");
    }

    Pitch[] pitches = Arrays
      .stream(pitchesString.split(","))
      .map(Pitch::fromString)
      .toArray(Pitch[]::new);

    return new Chord(pitches);
  }

  public static RequiresRoot builder(){
    return new Builder();
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append("{");
    builder.append(Arrays.stream(pitches).map(Pitch::toString).collect(Collectors.joining(",")));
    builder.append("}");
    return builder.toString();
  }

  public final Pitch[] getPitches(){
    return pitches;
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
        default: return null;
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

      KeySignature ks = new KeySignature(root.getKey(), Mode.MAJOR);
      Letter letter = ks.keyOf(bottomDegree).getLetter();
      Optional<Pitch> optional = deque
        .stream()
        .filter(p -> p.getKey().getLetter() == letter)
        .findFirst();

      if(!optional.isPresent()){
        throw new RuntimeException("Unable to invert chord: missing " + bottomDegree + " pitch");
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
