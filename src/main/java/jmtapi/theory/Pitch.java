
package etude.theory;

import etude.util.RegEx;

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
      throw new RuntimeException("Invalid program number: " + programNumber);
    }
  }

  public final Pitch apply(KeySignature keySignature){
    return new Pitch(key.apply(keySignature), octave);
  }

  public Pitch step(int amount){
    return step(amount, Accidental.Policy.PRIORITIZE_NATURAL);
  }

  public final Pitch step(int amount, Accidental.Policy policy){
    if(Accidental.Policy.MAINTAIN_LETTER == policy){
      int offset = key.getAccidental().getOffset();
      if(offset + amount > Accidental.TRIPLE_SHARP.getOffset() || offset + amount < Accidental.TRIPLE_FLAT.getOffset()){
        throw new RuntimeException("Can't move pitch " + amount + " step(s) up while maintaining letter: " + this);
      }
      return new Pitch(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() + amount)), octave);
    }
    return Pitch.fromProgramNumber(getProgramNumber() + amount, policy);
  }

  public final Pitch step(Interval interval){
    // determine the letter
    List<Letter> list = Letter.asList(key.getLetter());
    Letter letter = list.get(Math.floorMod(interval.getNumber() - 1, Letter.values().length));

    // initialize accidental to be the accidental of the new letter in the key signature of this key
    Accidental accidental = new Key(letter).apply(new KeySignature(key, Mode.MAJOR)).getAccidental();
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

    int octaveOffset = (interval.getNumber() + key.getLetter().getOffset() - letter.getOffset()) / Letter.values().length;
    return new Pitch(new Key(letter, accidental), octave + octaveOffset);
  }

  public Pitch halfStepUp(){
    return halfStepUp(Accidental.Policy.PRIORITIZE_SHARP);
  }

  public final Pitch halfStepUp(Accidental.Policy policy){
    if(Accidental.Policy.MAINTAIN_LETTER == policy){
      if(key.isTripleSharp()){
        throw new RuntimeException("Can't move pitch half step up while maintaining letter: " + this);
      }
      return new Pitch(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() + 1)), octave);
    }
    return Pitch.fromProgramNumber(getProgramNumber() + 1, policy);
  }

  public Pitch halfStepDown(){
    return halfStepDown(Accidental.Policy.PRIORITIZE_FLAT);
  }

  public final Pitch halfStepDown(Accidental.Policy policy){
    if(Accidental.Policy.MAINTAIN_LETTER == policy){
      if(key.isTripleFlat()){
        throw new RuntimeException("Can't move pitch half step down while maintaining letter: " + this);
      }
      return new Pitch(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() - 1)), octave);
    }
    return Pitch.fromProgramNumber(getProgramNumber() - 1, policy);
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
    return Pitch.fromProgramNumber(programNumber, Accidental.Policy.PRIORITIZE_NATURAL);
  }

  public static final Pitch fromProgramNumber(int programNumber, Accidental.Policy policy){
    if(programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER){
      throw new RuntimeException("Invalid program number: " + programNumber);
    }
    Key key = Key.fromOffset(Math.floorMod(programNumber, MusicConstants.KEYS_IN_OCTAVE), policy);
    int octave = programNumber / MusicConstants.KEYS_IN_OCTAVE;
    return new Pitch(key, octave);
  }

  public final int getProgramNumber(){
    return octave * MusicConstants.KEYS_IN_OCTAVE + key.getOffset();
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
      throw new RuntimeException("Invalid pitch string: " + pitchString);
    }
    else if(pitchString.trim().isEmpty()){
      throw new RuntimeException("Invalid pitch string: " + pitchString + " (blank)");
    }
    // longest prefix that contains only letters or #
    String keyString = RegEx.extract("^[a-zA-Z#]*", pitchString);
    Key key = Key.fromString(keyString);
    // first number of length greater than 0 thats followed by an open parentheses (if there is any)
    String octaveString = RegEx.extract("\\d+(?![^(]*\\))", pitchString);
    if(octaveString == null){
      throw new RuntimeException("Invalid pitch string: " + pitchString + " (missing octave)");
    }
    int octave = Integer.parseInt(octaveString);
    Pitch pitch = new Pitch(key, octave);

    // a number that has an open parentheses somewhere before it
    String programNumber = RegEx.extract("(?<=\\()\\d+", pitchString);
    if(programNumber != null){
      if(pitch.getProgramNumber() != Integer.parseInt(programNumber)){
        throw new RuntimeException("Invalid pitch string: " + pitchString + " (program number doesn't match key and octave)");
      }
    }

    String converted = pitch.toString();
    if(programNumber == null){
      String convertedNoParentheses = converted.substring(0, converted.indexOf("("));
      if(!convertedNoParentheses.equals(pitchString)){
        if(convertedNoParentheses.length() > pitchString.length()){
          throw new RuntimeException("Invalid pitch string: " + pitchString + " (missing information)");
        }
        else{
          throw new RuntimeException("Invalid pitch string: " + pitchString + " (contains extra information)");
        }
      }
    }
    else{
      if(!converted.equals(pitchString)){
        if(converted.length() > pitchString.length()){
          throw new RuntimeException("Invalid pitch string: " + pitchString + " (missing information)");
        }
        else{
          throw new RuntimeException("Invalid pitch string: " + pitchString + " (contains extra information)");
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
