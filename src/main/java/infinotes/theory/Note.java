
package infinotes.theory;

import infinotes.util.RegEx;

/*
 * Key with the concept of octave
 */
public class Note{
  private final Key key;
  private final int octave;

  public Note(Key key, int octave){
    this.key = key;
    this.octave = octave;
  }

  public Note semitoneUp(){
    return semitoneUp(Accidental.Policy.PRIORITIZE_SHARP);
  }

  public final Note semitoneUp(Accidental.Policy policy){
    if(Accidental.Policy.MAINTAIN_LETTER == policy){
      if(key.isDoubleSharp()){
        throw new RuntimeException("Can't move note semitone up while maintaining letter: " + this);
      }
      return new Note(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() + 1)), octave);
    }
    return Note.fromProgramNumber(getProgramNumber() + 1, policy);
  }

  public Note semitoneDown(){
    return semitoneDown(Accidental.Policy.PRIORITIZE_FLAT);
  }

  public final Note semitoneDown(Accidental.Policy policy){
    if(Accidental.Policy.MAINTAIN_LETTER == policy){
      if(key.isDoubleFlat()){
        throw new RuntimeException("Can't move note semitone down while maintaining letter: " + this);
      }
      return new Note(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() - 1)), octave);
    }
    return Note.fromProgramNumber(getProgramNumber() - 1, policy);
  }

  public static final Note fromProgramNumber(int programNumber){
    return Note.fromProgramNumber(programNumber, Accidental.Policy.PRIORITIZE_NATURAL);
  }

  public static final Note fromProgramNumber(int programNumber, Accidental.Policy policy){
    if(programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER){
      throw new RuntimeException("Invalid program number: " + programNumber);
    }
    Key key = Key.fromOffset(Math.floorMod(programNumber, MusicConstants.KEYS_IN_OCTAVE), policy);
    int octave = (int) Math.floor((double) programNumber / MusicConstants.KEYS_IN_OCTAVE);
    return new Note(key, octave);
  }

  public final int getProgramNumber(){
    return octave * MusicConstants.KEYS_IN_OCTAVE + key.getOffset();
  }

  /**
   * Any input in the form
   *   - ${key}${octave}
   *   - ${key}${octave}(${program number})
   * is accepted and converted into a Note.
   * ${program number} is intentionally not accepted because #fromProgramNumber
   * exists and should be used instead.
   */
  public static final Note fromString(String noteString){
    // longest prefix that contains only letters
    Key key = Key.fromString(RegEx.extract("^[a-zA-Z]{1,}", noteString));
    // first number of length greater than 1 thats followed by an open parentheses (if there is any)
    String octaveString = RegEx.extract("\\d{1,}(?![^(]*\\))", noteString);
    if(octaveString == null){
      throw new RuntimeException("Invalid note string: " + noteString + " (missing octave)");
    }
    int octave = Integer.parseInt(octaveString);
    Note note = new Note(key, octave);

    // a number that has an open parentheses somewhere before it
    String programNumber = RegEx.extract("(?<=\\()\\d{1,}", noteString);
    if(programNumber != null){
      if(note.getProgramNumber() != Integer.parseInt(programNumber)){
        throw new RuntimeException("Invalid note string: " + noteString + " (program number doesn't match key and octave)");
      }
    }

    String converted = note.toString();
    if(programNumber == null){
      String convertedNoParentheses = converted.substring(0, converted.indexOf("("));
      if(!convertedNoParentheses.equals(noteString)){
        if(convertedNoParentheses.length() > noteString.length()){
          throw new RuntimeException("Invalid note string: " + noteString + " (missing information)");
        }
        else{
          throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
        }
      }
    }
    else{
      if(!converted.equals(noteString)){
        if(converted.length() > noteString.length()){
          throw new RuntimeException("Invalid note string: " + noteString + " (missing information)");
        }
        else{
          throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
        }
      }
    }
    return note;
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

  public final Key getKey(){
    return key;
  }

  public final int getOctave(){
    return octave;
  }
}
