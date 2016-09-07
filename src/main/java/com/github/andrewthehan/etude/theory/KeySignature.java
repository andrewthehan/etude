
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

public final class KeySignature{
  public enum Quality{
    MAJOR, MINOR;
  }

  public static final Letter[] ORDER_OF_FLATS = "BEADGCF".chars().mapToObj(i -> Letter.fromChar((char) i)).toArray(Letter[]::new);
  public static final Letter[] ORDER_OF_SHARPS = "FCGDAEB".chars().mapToObj(i -> Letter.fromChar((char) i)).toArray(Letter[]::new);
  private final Key key;
  private final Quality quality;

  public KeySignature(Key key, Quality quality){
    this.key = key;
    this.quality = quality;
  }

  public boolean isMajor(){
    return quality == Quality.MAJOR;
  }

  public boolean isMinor(){
    return quality == Quality.MINOR;
  }

  public final Degree degreeOf(Key key){
    int difference = Math.floorMod(key.getLetter().ordinal() - this.key.getLetter().ordinal(), Letter.values().length);
    return Degree.fromValue(difference + 1);
  }

  public final Key keyOf(Degree degree){
    List<Letter> list = Letter.asList(key.getLetter());
    Key key = new Key(list.get(degree.getValue() - 1));
    return key.apply(this);
  }

  public Key[] getKeysWithAccidentals(){
    Key[] keys = new Scale(key, isMajor() ? Scale.Quality.MAJOR : Scale.Quality.NATURAL_MINOR)
      .stream()
      .limit(7)
      .filter(k -> !k.isNone() && !k.isNatural())
      .toArray(Key[]::new);
    if(keys.length != 0){
      List<Letter> ordered;
      switch(keys[0].getAccidental()){
        case FLAT: case DOUBLE_FLAT: case TRIPLE_FLAT:
          ordered = Arrays.asList(ORDER_OF_FLATS);
          break;
        case SHARP: case DOUBLE_SHARP: case TRIPLE_SHARP:
          ordered = Arrays.asList(ORDER_OF_SHARPS);
          break;
        default:
          throw new AssertionError();
      }
      Arrays.sort(keys, (a, b) -> Integer.compare(ordered.indexOf(a.getLetter()), ordered.indexOf(b.getLetter())));
    }
    return keys;
  }

  public int getAccidentalCount(){
    return (int) (new Scale(key, isMajor() ? Scale.Quality.MAJOR : Scale.Quality.NATURAL_MINOR)
      .stream()
      .limit(7)
      .filter(k -> !k.isNone() && !k.isNatural())
      .count());
  }

  public static KeySignature fromAccidentals(Accidental accidental, int count, Quality quality){
    if(count < 0 || count > 7){
      throw new EtudeException("Invalid accidental count: " + count);
    }

    if(count == 0 && (accidental != Accidental.NONE && accidental != Accidental.NATURAL)){
      throw new EtudeException("Invalid count for accidental type: " + count + " " + accidental);
    }

    Key key;
    Letter letter;
    // determine the key assuming quality is MAJOR
    switch(accidental){
      case FLAT:
        letter = ORDER_OF_FLATS[Math.floorMod(count - 2, Letter.values().length)];
        key = new Key(
          letter,
          // accidental; if flats for key signature contain the letter, make the key flat
          Arrays.stream(ORDER_OF_FLATS).limit(count).filter(l -> l == letter).findFirst().isPresent()
            ? Accidental.FLAT
            : Accidental.NONE
        );
        break;
      case SHARP:
        letter = ORDER_OF_SHARPS[Math.floorMod(count + 1, Letter.values().length)];
        key = new Key(
          letter,
          // accidental; if sharps for key signature contain the letter, make the key sharp
          Arrays.stream(ORDER_OF_SHARPS).limit(count).filter(l -> l == letter).findFirst().isPresent()
            ? Accidental.SHARP
            : Accidental.NONE
        );
        break;
      case NONE: case NATURAL:
        if(count != 0){
          throw new EtudeException("Invalid count for accidental type: " + count + " " + accidental);
        }
        letter = Letter.C;
        key = new Key(letter);
        break;
      default:
        throw new EtudeException("Invalid accidental type to create KeySignature from: " + accidental);
    }

    KeySignature keySignature = new KeySignature(key, Quality.MAJOR);

    if(quality == Quality.MINOR){
      keySignature = keySignature.getRelative();
    }

    return keySignature;
  }

  public final KeySignature getParallel(){
    return new KeySignature(key, isMajor() ? Quality.MINOR : Quality.MAJOR);
  }

  public final KeySignature getRelative(){
    Key[] keys = getKeysWithAccidentals();
    
    /**
    * 0 flats/sharps = NONE_OR_NATURAL
    * flats = NONE_OR_NATURAL + FLAT
    * sharps = NONE_OR_NATURAL + SHARP
    */
    ImmutablePrioritySet<Policy> policies = keys.length == 0
      ? Policy.prioritize(Policy.NONE_OR_NATURAL)
      : Policy.prioritize(
          Policy.NONE_OR_NATURAL,
          keys[0].getAccidental() == Accidental.FLAT
            ? Policy.FLAT
            : Policy.SHARP
        );

    /**
    * major -> minor = -3
    * minor -> major = 3
    */
    return new KeySignature(
      Key.fromOffset(
        Math.floorMod(key.getOffset() + (isMajor() ? -3 : 3), MusicConstants.KEYS_IN_OCTAVE),
        policies
      ),
      isMajor() ? Quality.MINOR : Quality.MAJOR
    );
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(key);
    builder.append(quality);
    return builder.toString();
  }

  public final Key getKey(){
    return key;
  }

  public final Quality getQuality(){
    return quality;
  }
}
