
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;
import com.github.andrewthehan.etude.util.RegEx;

import java.util.List;

/*
 * Key with the concept of octave
 */
public class Pitch implements Comparable<Pitch>{
  private final Key key;
  private final int octave;

  public Pitch(Key key, int octave){
    this.key = key;
    this.octave = octave;

    int programNumber = getProgramNumber();
    if(programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER){
      throw new EtudeException("Invalid program number: " + programNumber);
    }
  }

  public final Pitch apply(KeySignature keySignature){
    return new Pitch(key.apply(keySignature), octave);
  }

  public Pitch step(int amount){
    return step(amount, Policy.DEFAULT_PRIORITY);
  }

  public final Pitch step(int amount, ImmutablePrioritySet<Policy> policies){
    return Pitch.fromProgramNumber(getProgramNumber() + amount, policies);
  }

  public final Pitch step(Interval interval){
    int letterCount = Letter.values().length;
    // determine the letter
    List<Letter> list = Letter.asList(key.getLetter());
    Letter letter = list.get(Math.floorMod(interval.getNumber() - 1, letterCount));

    // initialize accidental to be the accidental of the new letter in the key signature of this key
    Accidental accidental = new Key(letter).apply(new KeySignature(key, KeySignature.Quality.MAJOR)).getAccidental();
    // change accidental based on interval's quality
    switch(interval.getQuality()){
      case PERFECT: MAJOR:
        break;
      case MINOR:
        accidental = Accidental.fromOffset(accidental.getOffset() - 1);
        break;
      case DIMINISHED:
        accidental = Accidental.fromOffset(accidental.getOffset() - (Interval.isPerfect(interval.getNumber()) ? 1 : 2));
        break;
      case DOUBLY_DIMINISHED:
        accidental = Accidental.fromOffset(accidental.getOffset() - (Interval.isPerfect(interval.getNumber()) ? 2 : 3));
        break;
      case AUGMENTED:
        accidental = Accidental.fromOffset(accidental.getOffset() + 1);
        break;
      case DOUBLY_AUGMENTED:
        accidental = Accidental.fromOffset(accidental.getOffset() + 2);
        break;
    }

    // refer to Interval.between for how this equation was derived
    int octaveOffset = (interval.getNumber()
      - 1
      + (Math.floorMod(this.getKey().getLetter().ordinal() - 2, letterCount) - Math.floorMod(letter.ordinal() - 2, letterCount))
    ) / letterCount;

    return new Pitch(new Key(letter, accidental), octave + octaveOffset);
  }

  public Pitch halfStepUp(){
    return halfStepUp(Policy.DEFAULT_PRIORITY);
  }

  public final Pitch halfStepUp(ImmutablePrioritySet<Policy> policies){
    return Pitch.fromProgramNumber(getProgramNumber() + 1, policies);
  }

  public Pitch halfStepDown(){
    return halfStepDown(Policy.DEFAULT_PRIORITY);
  }

  public final Pitch halfStepDown(ImmutablePrioritySet<Policy> policies){
    return Pitch.fromProgramNumber(getProgramNumber() - 1, policies);
  }

  public final Pitch getHigherPitch(Key key){
    Pitch pitch = new Pitch(key, octave);
    if(!isLowerThan(pitch)){
      pitch = new Pitch(key, octave + 1);
    }
    return pitch;
  }

  public final Pitch getLowerPitch(Key key){
    Pitch pitch = new Pitch(key, octave);
    if(!isHigherThan(pitch)){
      pitch = new Pitch(key, octave - 1);
    }
    return pitch;
  }

  public final boolean isHigherThan(Pitch pitch){
    return getProgramNumber() > pitch.getProgramNumber();
  }

  public final boolean isLowerThan(Pitch pitch){
    return getProgramNumber() < pitch.getProgramNumber();
  }

  @Override
  public int compareTo(Pitch other){
    return Integer.compare(getProgramNumber(), other.getProgramNumber());
  }

  public static final boolean isEnharmonic(Pitch a, Pitch b){
    return a.getProgramNumber() == b.getProgramNumber();
  }

  public static final Pitch fromProgramNumber(int programNumber){
    return Pitch.fromProgramNumber(programNumber, Policy.DEFAULT_PRIORITY);
  }

  public static final Pitch fromProgramNumber(int programNumber, ImmutablePrioritySet<Policy> policies){
    if(programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER){
      throw new EtudeException("Invalid program number: " + programNumber);
    }
    Key key = Key.fromOffset(Math.floorMod(programNumber, MusicConstants.KEYS_IN_OCTAVE), policies);
    if(key == null){
      return null;
    }
    int octave = programNumber / MusicConstants.KEYS_IN_OCTAVE;
    /**
    * Key offsets are bounded by the range [0, MusicConstants.KEYS_IN_OCTAVE) whereas program numbers go across octave boundaries.
    * If [actual key offset] is equal to [offset after normalizing], then octave is not changed.
    * If [actual key offset] is lower than [offset after normalizing], then octave is raised by 1.
    * If [actual key offset] is higher than [offset after normalizing], then octave is lowered by 1.
    */
    octave += (key.getOffset() - (key.getLetter().getOffset() + key.getAccidental().getOffset())) / MusicConstants.KEYS_IN_OCTAVE;
    return new Pitch(key, octave);
  }

  public final int getProgramNumber(){
    return octave * MusicConstants.KEYS_IN_OCTAVE + key.getLetter().getOffset() + key.getAccidental().getOffset();
  }

  /**
  * Any input in the form
  *   - ${key}${octave}
  *   - ${key}${octave}(${program number})
  * is accepted and converted into a Pitch.
  * ${program number} is intentionally not accepted because #fromProgramNumber
  * exists and should be used instead.
  */
  public static final Pitch fromString(String pitchString){
    if(pitchString == null){
      throw new EtudeException("Invalid pitch string: " + pitchString);
    }
    else if(pitchString.trim().isEmpty()){
      throw new EtudeException("Invalid pitch string: " + pitchString + " (blank)");
    }
    // longest prefix that contains only letters or #
    String keyString = RegEx.extract("^[a-zA-Z#]*", pitchString);
    Key key = Key.fromString(keyString);
    // first number of length greater than 0 thats followed by an open parentheses (if there is any)
    String octaveString = RegEx.extract("\\d+(?![^(]*\\))", pitchString);
    if(octaveString == null){
      throw new EtudeException("Invalid pitch string: " + pitchString + " (missing octave)");
    }
    int octave = Integer.parseInt(octaveString);
    Pitch pitch = new Pitch(key, octave);

    // a number that has an open parentheses somewhere before it
    String programNumber = RegEx.extract("(?<=\\()\\d+", pitchString);
    if(programNumber != null){
      if(pitch.getProgramNumber() != Integer.parseInt(programNumber)){
        throw new EtudeException("Invalid pitch string: " + pitchString + " (program number doesn't match key and octave)");
      }
    }

    String converted = pitch.toString();
    if(programNumber == null){
      String convertedNoParentheses = converted.substring(0, converted.indexOf("("));
      if(!convertedNoParentheses.equals(pitchString)){
        if(convertedNoParentheses.length() > pitchString.length()){
          throw new EtudeException("Invalid pitch string: " + pitchString + " (missing information)");
        }
        else{
          throw new EtudeException("Invalid pitch string: " + pitchString + " (contains extra information)");
        }
      }
    }
    else{
      if(!converted.equals(pitchString)){
        if(converted.length() > pitchString.length()){
          throw new EtudeException("Invalid pitch string: " + pitchString + " (missing information)");
        }
        else{
          throw new EtudeException("Invalid pitch string: " + pitchString + " (contains extra information)");
        }
      }
    }
    return pitch;
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(key);
    builder.append(octave);
    builder.append("(");
    builder.append(getProgramNumber());
    builder.append(")");
    return builder.toString();
  }

  @Override
  public int hashCode(){
    return key.hashCode() ^ octave;
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof Pitch)){
      return false;
    }
    if(other == this){
      return true;
    }

    Pitch otherPitch = (Pitch) other;
    return key.equals(otherPitch.getKey()) && octave == otherPitch.getOctave();
  }

  public final Key getKey(){
    return key;
  }

  public final int getOctave(){
    return octave;
  }
}
