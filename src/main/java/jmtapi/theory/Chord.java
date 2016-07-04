
package jmtapi.theory;

import jmtapi.util.RegEx;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    List<Pitch> pitches = Arrays
      .stream(quality.getIntervalPattern())
      .map(pitch::step)
      .collect(Collectors.toList());

    if(Inversion.THIRD == inversion && quality.getIntervalPattern().length <= Inversion.THIRD.getValue()){
      throw new RuntimeException("Invalid inversion: " + inversion + " (unable to invert chord with less than 4 pitches to its third inversion)");
    }

    Collections.rotate(pitches, -inversion.getValue());

    this.pitches = pitches
      .stream()
      .toArray(Pitch[]::new);
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
}
