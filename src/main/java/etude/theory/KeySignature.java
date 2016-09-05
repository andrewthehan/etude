
package etude.theory;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

public final class KeySignature{
  public static final Letter[] ORDER_OF_FLATS = "BEADGCF".chars().mapToObj(i -> Letter.fromChar((char) i)).toArray(Letter[]::new);
  public static final Letter[] ORDER_OF_SHARPS = "FCGDAEB".chars().mapToObj(i -> Letter.fromChar((char) i)).toArray(Letter[]::new);
  private final Key key;
  private final Mode mode;

  public KeySignature(Key key, Mode mode){
    this.key = key;
    this.mode = mode;
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
    Key[] keys = new Scale(this)
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
    return (int) (new Scale(this)
      .stream()
      .limit(7)
      .filter(k -> !k.isNone() && !k.isNatural())
      .count());
  }

  public static KeySignature fromAccidentals(Accidental accidental, int count, Mode mode){
    if(count < 0 || count > 7){
      throw new RuntimeException("Invalid accidental count: " + count);
    }

    Key key;
    Letter letter;
    // determine the key assuming mode is MAJOR
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
      default:
        throw new RuntimeException("Invalid accidental type to create KeySignature from: " + accidental);
    }

    // lower key by 3 half steps if the mode is NATURAL_MINOR
    switch(mode){
      case MAJOR:
        break;
      case NATURAL_MINOR:
        key = Key.fromOffset(
          Math.floorMod(key.getOffset() - 3, MusicConstants.KEYS_IN_OCTAVE),
          accidental == Accidental.FLAT
            ? Accidental.Policy.PRIORITIZE_FLAT
            : Accidental.Policy.PRIORITIZE_SHARP
        );
        break;
      default:
        throw new RuntimeException("Invalid mode type to create KeySignature from: " + mode);
    }

    return new KeySignature(key, mode);
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(key);
    builder.append(mode);
    return builder.toString();
  }

  public final Key getKey(){
    return key;
  }

  public final Mode getMode(){
    return mode;
  }
}
