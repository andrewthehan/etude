
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.RegEx;

import java.util.Arrays;
import java.util.Objects;

public final class Interval{
  public enum Quality{
    PERFECT("P"), MAJOR("M"), MINOR("m"), DIMINISHED("d"), DOUBLY_DIMINISHED("dd"), AUGMENTED("A"), DOUBLY_AUGMENTED("AA");

    public static final int SIZE = Quality.values().length;

    private final String symbol;

    private Quality(String symbol){
      this.symbol = symbol;
    }

    public static final Exceptional<Quality> fromString(String qualityString){
      return EtudeParser
        .of(qualityString)
        .filter(Objects::nonNull, EtudeException.forNull(Quality.class))
        .parse(s -> Arrays
          .stream(Quality.values())
          .filter(a -> s.equals(a.toString()))
          .findFirst()
          .map(Exceptional::of)
          .orElseGet(Exceptional::empty)
          .withException(EtudeException.forInvalid(Quality.class, s))
        )
        .get(a -> (Quality) a[0]);
    }

    @Override
    public final String toString(){
      return symbol;
    }
  }

  private final Quality quality;
  private final int number;

  public Interval(Quality quality, int number){
    if(number <= 0){
      throw EtudeException.forInvalid(Interval.class, number, "should be a positive integer");
    }
    switch(quality){
      case PERFECT:
        if(!Interval.isPerfect(number)){
          throw EtudeException.forInvalid(Interval.class, quality.toString() + number, number + " cannot have a perfect quality");
        }
        break;
      case MAJOR: case MINOR:
        if(Interval.isPerfect(number)){
          throw EtudeException.forInvalid(Interval.class, quality.toString() + number, number + " cannot have major or minor quality");
        }
        break;
      case DIMINISHED: case DOUBLY_DIMINISHED: case AUGMENTED: case DOUBLY_AUGMENTED:
        break;
    }
    this.quality = quality;
    this.number = number;
  }

  public static final boolean isEnharmonic(Interval a, Interval b){
    return a.getOffset() == b.getOffset();
  }

  public static final Exceptional<Interval> between(Pitch a, Pitch b){
    if(a == null || b == null){
      return Exceptional.empty(EtudeException.forNull(Pitch.class));
    }

    Letter letterA = a.getKey().getLetter();
    Letter letterB = b.getKey().getLetter();
    
    /**
     * 1 (because no distance == 1)
     * + letterDistance (subtracted 2 because C is the start of the octave)
     * + octaveDistance
     */
    int number = 1
      + (Math.floorMod(letterB.ordinal() - 2, Letter.SIZE) - Math.floorMod(letterA.ordinal() - 2, Letter.SIZE))
      + (b.getOctave() - a.getOctave()) * Letter.SIZE;

    if(number <= 0){
      return Exceptional.empty(EtudeException.forInvalid(Interval.class, number, "cannot create an interval with a nonpositive number"));
    }

    int offset = (b.getProgramNumber() - a.getProgramNumber()) % MusicConstants.KEYS_IN_OCTAVE;
    offset -= Arrays
      .stream(Scale.Quality.MAJOR.getStepPattern())
      .limit((number - 1) % Letter.SIZE)
      .sum();

    Quality quality;
    switch(offset){
      case -3:
        if(Interval.isPerfect(number)){
          return Exceptional.empty(EtudeException.forInvalid(Interval.class, number, "cannot create a perfect interval with an offset of -3"));
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
        return Exceptional.empty(EtudeException.forInvalid(Interval.class, offset, "offset is too large"));
    }

    return Exceptional.of(new Interval(quality, number));
  }

  public final boolean isSimple(){
    return number <= Letter.SIZE + 1;
  }

  public final boolean isCompound(){
    return number > Letter.SIZE + 1;
  }

  /**
   * follows the rules on https://en.wikipedia.org/wiki/Interval_(music)#Inversion
   * important for the inverse of compound intervals for which definitions online differ
   *
   * @return Inverted interval
   */
  public final Interval inverse(){
    Quality invertedQuality;

    switch(quality){
      case PERFECT:
        invertedQuality = Quality.PERFECT;
        break;
      case MAJOR:
        invertedQuality = Quality.MINOR;
        break;
      case MINOR:
        invertedQuality = Quality.MAJOR;
        break;
      case DIMINISHED:
        invertedQuality = Quality.AUGMENTED;
        break;
      case DOUBLY_DIMINISHED:
        invertedQuality = Quality.DOUBLY_AUGMENTED;
        break;
      case AUGMENTED:
        invertedQuality = Quality.DIMINISHED;
        break;
      case DOUBLY_AUGMENTED:
        invertedQuality = Quality.DOUBLY_DIMINISHED;
        break;
      default:
        throw new AssertionError();
    }

    // inverses add up to 9 assuming number is in the range [1, 8]
    int invertedNumber = number != 1 && number % Letter.SIZE == 1 ? 1 : 9 - (number % Letter.SIZE);

    return new Interval(invertedQuality, invertedNumber);
  }

  public final int getOffset(){
    // initialize offset to take into account octave
    int offset = (number - 1) / Letter.SIZE * MusicConstants.KEYS_IN_OCTAVE;

    // take into account normalized number (within the range of an octave)
    offset += Arrays
      .stream(Scale.Quality.MAJOR.getStepPattern())
      .limit((number - 1) % Letter.SIZE)
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
    int normalized = number % Letter.SIZE;
    return normalized == 1 || normalized == 4 || normalized == 5;
  }

  public static final Exceptional<Interval> fromString(String intervalString){
    return EtudeParser
      .of(intervalString)
      .filter(Objects::nonNull, EtudeException.forNull(Interval.class))
      /*
       * has a non-digit / digit before it and a digit / non-digit after it;
       * a valid intervalString doesn't require this regex (only needs to detect
       * non-digit then digit) but in order to provide proper exception messages
       * for invalid intervalStrings, the current regex is required
       */
      .map(s -> intervalString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"))
      .filter(s -> s.length >= 2, EtudeException.forInvalid(Interval.class, intervalString, "missing information"))
      .filter(s -> s.length <= 2, EtudeException.forInvalid(Interval.class, intervalString, "contains extra information"))
      .parse(s -> Quality.fromString(s[0]))
      .parse(s -> Exceptional
        .of(s[1])
        .map(i -> RegEx.extract("\\d+", i), EtudeException.forInvalid(Interval.class, intervalString, "doesn't contain a valid number"))
        .map(Integer::parseInt)
      )
      .get(a -> new Interval((Quality) a[0], (Integer) a[1]));
  }

  @Override
  public final String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(quality);
    builder.append(number);
    return builder.toString();
  }

  @Override
  public final int hashCode(){
    return Objects.hash(quality, number);
  }

  @Override
  public final boolean equals(Object other){
    if(!(other instanceof Interval)){
      return false;
    }
    if(other == this){
      return true;
    }

    Interval otherInterval = (Interval) other;

    return Objects.deepEquals(
      new Object[]{
        quality, number
      },
      new Object[]{
        otherInterval.getQuality(), otherInterval.getNumber()
      }
    );
  }

  public final Quality getQuality(){
    return quality;
  }

  public final int getNumber(){
    return number;
  }
}
