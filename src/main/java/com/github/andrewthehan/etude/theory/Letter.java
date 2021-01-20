
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.CircularIterator;
import com.github.andrewthehan.etude.util.Exceptional;
import com.github.andrewthehan.etude.util.StreamUtil;

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
   *   - C4's program number is 48
   *   - increment that value by Letter.E's offset value (4)
   *   - E4: 48 + 4 == 52 == E4's program number
   */
  A(9), B(11), C(0), D(2), E(4), F(5), G(7);

  public static final int SIZE = Letter.values().length;

  private final int offset;

  private Letter(int offset){ this.offset = offset; }

  public final int getOffset(){ return offset; }

  public static final Stream<Letter> stream(){
    return Letter.stream(Direction.DEFAULT, Letter.A);
  }

  public static final Stream<Letter> stream(Direction direction){
    return Letter.stream(direction, Letter.A);
  }

  public static final Stream<Letter> stream(Letter startingLetter){
    return Letter.stream(Direction.DEFAULT, startingLetter);
  }

  public static final Stream<Letter> stream(Direction direction, Letter startingLetter){
    return StreamUtil.fromIterator(Letter.iterator(direction, startingLetter));
  }

  public static final Iterator<Letter> iterator(){
    return Letter.iterator(Direction.DEFAULT, Letter.A);
  }

  public static final Iterator<Letter> iterator(Direction direction){
    return Letter.iterator(direction, Letter.A);
  }

  public static final Iterator<Letter> iterator(Letter startingLetter){
    return Letter.iterator(Direction.DEFAULT, startingLetter);
  }

  public static final Iterator<Letter> iterator(Direction direction, Letter startingLetter){
    return CircularIterator.of(Letter.getLetters(direction, startingLetter));
  }

  public static final List<Letter> asList(){
    return Letter.asList(Direction.DEFAULT, Letter.A);
  }

  public static final List<Letter> asList(Direction direction){
    return Letter.asList(direction, Letter.A);
  }

  public static final List<Letter> asList(Letter startingLetter){
    return Letter.asList(Direction.DEFAULT, startingLetter);
  }

  public static final List<Letter> asList(Direction direction, Letter startingLetter){
    return Arrays.asList(Letter.getLetters(direction, startingLetter));
  }

  public static final Letter[] getLetters(){
    return getLetters(Direction.DEFAULT, Letter.A);
  }

  public static final Letter[] getLetters(Direction direction){
    return getLetters(direction, Letter.A);
  }

  public static final Letter[] getLetters(Letter startingLetter){
    return getLetters(Direction.DEFAULT, startingLetter);
  }

  public static final Letter[] getLetters(Direction direction, Letter startingLetter){
    List<Letter> list = Arrays.asList(Letter.values());
    if(direction == Direction.DESCENDING){
      Collections.reverse(list);
    }
    Collections.rotate(list, -list.indexOf(startingLetter));
    return list.toArray(new Letter[0]);
  }

  public static final boolean isValid(char letterChar){
    char upperCase = Character.toUpperCase(letterChar);
    return upperCase >= 'A' && upperCase <= 'G'; 
  }

  public static final Exceptional<Letter> fromChar(char letterChar){
    return Exceptional
      .of(letterChar)
      .filter(Letter::isValid, EtudeException.forInvalid(Letter.class, letterChar))
      .map(c -> Letter.values()[Character.toUpperCase(letterChar) - 'A']);
  }
}
