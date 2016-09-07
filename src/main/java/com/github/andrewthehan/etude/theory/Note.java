
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;

/*
 * Pitch with the concept of value
 */
public final class Note{
  private final Pitch pitch;
  private final Value value;

  public Note(Pitch pitch, Value value){
    this.pitch = pitch;
    this.value = value;
  }

  public static final Note fromString(String noteString){
    if(noteString == null){
      throw new EtudeException("Invalid note string: " + noteString);
    }
    else if(noteString.trim().isEmpty()){
      throw new EtudeException("Invalid note string: " + noteString + " (blank)");
    }

    String[] split = noteString.split("\\[");
    if(split.length < 2 || split[0].trim().isEmpty() || split[1].trim().isEmpty()){
      throw new EtudeException("Invalid note string: " + noteString + " (missing information)");
    }
    else if(split.length > 2){
      throw new EtudeException("Invalid note string: " + noteString + " (contains extra information)");
    }

    Pitch pitch = Pitch.fromString(split[0]);

    if(!split[1].contains("]")){
      throw new EtudeException("Invalid note string: " + noteString + " (missing closing bracket)");
    }
    else if(!split[1].endsWith("]")){
      throw new EtudeException("Invalid note string: " + noteString + " (contains extra information)");
    }

    Value value = Value.fromString(split[1].substring(0, split[1].length() - 1));
    return new Note(pitch, value);
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(pitch);
    builder.append("[");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode(){
    return pitch.hashCode() ^ value.hashCode();
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof Note)){
      return false;
    }
    if(other == this){
      return true;
    }

    Note otherNote = (Note) other;
    return pitch.equals(otherNote.getPitch()) && value == otherNote.getValue();
  }

  public final Pitch getPitch(){
    return pitch;
  }

  public final Value getValue(){
    return value;
  }
}
