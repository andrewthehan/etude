
package jmtapi.theory;

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

  public static final Iterator<Letter> iterator(){
    return Letter.iterator(Letter.A);
  }

  public static final Iterator<Letter> iterator(Letter startingLetter){
    return Letter.asList(startingLetter).iterator();
  }

  public static final List<Letter> asList(){
    return Letter.asList(Letter.A);
  }

  public static final List<Letter> asList(Letter startingLetter){
    List<Letter> list = Arrays.asList(Letter.values());
    Collections.rotate(list, -list.indexOf(startingLetter));
    return list;
  }

  public static final Letter fromChar(char letterChar){
    switch(letterChar){
      case 'A': case 'a': return A;
      case 'B': case 'b': return B;
      case 'C': case 'c': return C;
      case 'D': case 'd': return D;
      case 'E': case 'e': return E;
      case 'F': case 'f': return F;
      case 'G': case 'g': return G;
      default: throw new RuntimeException("Invalid letter character: " + letterChar);
    }
  }
}
