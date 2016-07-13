
package jmtapi.theory;

import jmtapi.util.CircularIterator;
import jmtapi.util.Streams;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public enum Letter{
  /*
   * Values derived from General MIDI's program numbers
   * (https://en.wikipedia.org/wiki/General_MIDI)
   * and the octave convention for scientific pitch notation
   * (https://en.wikipedia.org/wiki/Scientific_pitch_notation)
   * Ex. The E (E4) above middle C (C4):
   *   - new octaves start on C
   *   - the program number for the C in octave 4 is 48
   *   - increment that value by Key.E's offset value (4)
   *   - E4: 48 + 4 = 52
   */
  A(9), B(11), C(0), D(2), E(4), F(5), G(7);

  private final int offset;

  private Letter(int offset){ this.offset = offset; }

  public final int getOffset(){ return offset; }

  public static final Stream<Letter> stream(){
    return Letter.stream(Letter.A);
  }

  public static final Stream<Letter> stream(Letter startingLetter){
    return Streams.fromIterator(Letter.iterator(startingLetter));
  }

  public static final Iterator<Letter> iterator(){
    return Letter.iterator(Letter.A);
  }

  public static final Iterator<Letter> iterator(Letter startingLetter){
    return new CircularIterator<Letter>(Letter.values(), startingLetter);
  }

  public static final List<Letter> asList(){
    return Letter.asList(Letter.A);
  }

  public static final List<Letter> asList(Letter startingLetter){
    List<Letter> list = Arrays.asList(Letter.values());
    Collections.rotate(list, -startingLetter.ordinal());
    return list;
  }

  public static final boolean isValid(char letterChar){
    return (letterChar >= 'A' && letterChar <= 'G') || (letterChar >= 'a' && letterChar <= 'g'); 
  }

  public static final Letter fromChar(char letterChar){
    if(!Letter.isValid(letterChar)){
      throw new RuntimeException("Invalid letter character: " + letterChar);
    }
    return Letter.values()[Character.toUpperCase(letterChar) - 'A'];
  }
}
