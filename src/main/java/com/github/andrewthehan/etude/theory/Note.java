
package com.github.andrewthehan.etude.theory;

import java.util.Objects;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.EtudeParser;
import com.github.andrewthehan.etude.util.Exceptional;

public final class Note {
  private final Pitch pitch;
  private final Value value;

  public Note(Pitch pitch, Value value) {
    this.pitch = pitch;
    this.value = value;
  }

  public final Note apply(KeySignature keySignature) {
    Pitch pitch = this.pitch.apply(keySignature);
    if (this.pitch == pitch) {
      return this;
    }
    return new Note(pitch, value);
  }

  public static final Exceptional<Note> fromString(String noteString) {
    return EtudeParser.of(noteString).filter(Objects::nonNull, EtudeException.forNull(Note.class))
        .map(s -> s.split("\\["))
        .filter(s -> s.length >= 2, EtudeException.forInvalid(Note.class, noteString, "missing information"))
        .filter(s -> s.length <= 2, EtudeException.forInvalid(Note.class, noteString, "contains extra information"))
        .filter(s -> !s[0].trim().isEmpty() && !s[1].trim().isEmpty(),
            EtudeException.forInvalid(Note.class, noteString, "missing information"))
        .filter(s -> s[1].contains("]"), EtudeException.forInvalid(Note.class, noteString, "missing closing bracket"))
        .filter(s -> s[1].endsWith("]"),
            EtudeException.forInvalid(Note.class, noteString, "contains extra information"))
        .parse(s -> Pitch.fromString(s[0])).parse(s -> Value.fromString(s[1].substring(0, s[1].length() - 1)))
        .get(a -> new Note((Pitch) a[0], (Value) a[1]));
  }

  @Override
  public final String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(pitch);
    builder.append("[");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public final int hashCode() {
    return Objects.hash(pitch, value);
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Note)) {
      return false;
    }
    if (other == this) {
      return true;
    }

    Note otherNote = (Note) other;

    return Objects.deepEquals(new Object[] { pitch, value },
        new Object[] { otherNote.getPitch(), otherNote.getValue() });
  }

  public final Pitch getPitch() {
    return pitch;
  }

  public final Value getValue() {
    return value;
  }
}
