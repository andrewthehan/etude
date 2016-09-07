
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;

import java.util.Arrays;

public final class Interval{
  public enum Quality{
    PERFECT("P"), MAJOR("M"), MINOR("m"), DIMINISHED("d"), DOUBLY_DIMINISHED("dd"), AUGMENTED("A"), DOUBLY_AUGMENTED("AA");

    private final String symbol;

    private Quality(String symbol){
      this.symbol = symbol;
    }

    public static final Quality fromString(String qualityString){
      switch(qualityString){
        case "P": return PERFECT;
        case "M": return MAJOR;
        case "m": return MINOR;
        case "d": return DIMINISHED;
        case "dd": return DOUBLY_DIMINISHED;
        case "A": return AUGMENTED;
        case "AA": return DOUBLY_AUGMENTED;
        default: throw new EtudeException("Invalid quality string: " + qualityString);
      }
    }

    @Override
    public String toString(){
      return symbol;
    }
  }

  private final Quality quality;
  private final int number;

  public Interval(Quality quality, int number){
    if(number <= 0){
      throw new EtudeException("Invalid interval: " + quality + number + " (number must be a positive integer)");
    }
    switch(quality){
      case PERFECT:
        if(!Interval.isPerfect(number)){
          throw new EtudeException("Invalid interval: " + quality + number + " (number cannot have a perfect quality)");
        }
        break;
      case MAJOR: case MINOR:
        if(Interval.isPerfect(number)){
          throw new EtudeException("Invalid interval: " + quality + number + " (number cannot have major or minor quality)");
        }
        break;
      case DIMINISHED: case DOUBLY_DIMINISHED: case AUGMENTED: case DOUBLY_AUGMENTED:
        break;
    }
    this.quality = quality;
    this.number = number;
  }

  public static final Interval between(Pitch a, Pitch b){
    Letter letterA = a.getKey().getLetter();
    Letter letterB = b.getKey().getLetter();

    int letterCount = Letter.values().length;
    
    /**
     * 1 (because no distance == 1)
     * + letterDistance (subtracted 2 because C is the start of the octave)
     * + octaveDistance
     */
    int number = 1
      + (Math.floorMod(letterB.ordinal() - 2, letterCount) - Math.floorMod(letterA.ordinal() - 2, letterCount))
      + (b.getOctave() - a.getOctave()) * letterCount;

    if(number <= 0){
      throw new EtudeException("Cannot create interval with nonpositive number");
    }

    int offset = (b.getProgramNumber() - a.getProgramNumber()) % MusicConstants.KEYS_IN_OCTAVE;
    offset -= Arrays
      .stream(Scale.Quality.MAJOR.getStepPattern())
      .limit((number - 1) % letterCount)
      .sum();

    Quality quality;
    switch(offset){
      case -3:
        if(Interval.isPerfect(number)){
          throw new EtudeException("Cannot create interval for pitches: " + a + " -> " + b);
        }
        quality = Quality.DOUBLY_DIMINISHED;
        break;
      case -2:
        quality = Interval.isPerfect(number) ? Quality.DOUBLY_DIMINISHED : Quality.DIMINISHED;
        break;
      case -1:
        quality = Interval.isPerfect(number) ? Quality.DIMINISHED : Quality.MINOR;
        break;
      case 0:
        quality = Interval.isPerfect(number) ? Quality.PERFECT : Quality.MAJOR;
        break;
      case 1:
        quality = Quality.AUGMENTED;
        break;
      case 2:
        quality = Quality.DOUBLY_AUGMENTED;
        break;
      default:
        throw new EtudeException("Cannot create interval for pitches: " + a + " -> " + b);
    }

    return new Interval(quality, number);
  }

  public final int getOffset(){
    int letterCount = Letter.values().length;

    // initialize offset to take into account octave
    int offset = (number - 1) / letterCount * MusicConstants.KEYS_IN_OCTAVE;

    // take into account normalized number (within the range of an octave)
    offset += Arrays
      .stream(Scale.Quality.MAJOR.getStepPattern())
      .limit((number - 1) % letterCount)
      .sum();

    // take into account quality
    switch(quality){
      case PERFECT: case MAJOR:
        break;
      case MINOR:
        --offset;
        break;
      case DIMINISHED:
        offset -= Interval.isPerfect(number) ? 1 : 2;
        break;
      case DOUBLY_DIMINISHED:
        offset -= Interval.isPerfect(number) ? 2 : 3;
        break;
      case AUGMENTED:
        ++offset;
        break;
      case DOUBLY_AUGMENTED:
        offset += 2;
        break;
    }
    return offset;
  }

  public static final boolean isPerfect(int number){
    int normalized = number % 7;
    return normalized == 1 || normalized == 4 || normalized == 5;
  }

  public static final Interval fromString(String intervalString){
    if(intervalString == null){
      throw new EtudeException("Invalid interval string: " + intervalString);
    }
    else if(intervalString.trim().isEmpty()){
      throw new EtudeException("Invalid interval string: " + intervalString + " (blank)");
    }

    /*
     * has a non-digit / digit before it and a digit / non-digit after it;
     * a valid intervalString doesn't require this regex (only needs to detect
     * non-digit then digit) but in order to provide proper exception messages
     * for invalid intervalStrings, the current regex is required
     */
    String[] split = intervalString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    if(split.length < 2){
      throw new EtudeException("Invalid interval string: " + intervalString + " (missing information)");
    }
    else if(split.length > 2){
      throw new EtudeException("Invalid interval string: " + intervalString + " (contains extra information)");
    }

    Quality quality = Quality.fromString(split[0]);

    int number = Integer.parseInt(split[1]);

    return new Interval(quality, number);
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(quality);
    builder.append(number);
    return builder.toString();
  }

  public final Quality getQuality(){
    return quality;
  }

  public final int getNumber(){
    return number;
  }
}
