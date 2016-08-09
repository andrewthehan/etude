
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
