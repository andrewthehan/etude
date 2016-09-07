
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

/*
 * Letter with the concept of accidental
 */
public final class Key{
  private final Letter letter;
  private final Accidental accidental;

  public Key(Letter letter){
    this(letter, Accidental.NONE);
  }

  public Key(Letter letter, Accidental accidental){
    this.letter = letter;
    this.accidental = accidental;
  }

  public final Key step(int amount){
    return step(amount, Policy.DEFAULT_PRIORITY);
  }

  public final Key step(int amount, ImmutablePrioritySet<Policy> policies){
    return Key.fromOffset(Math.floorMod(getOffset() + amount, MusicConstants.KEYS_IN_OCTAVE), policies);
  }

  public final Key apply(KeySignature keySignature){
    Key key = keySignature.getKey();

    int offset = key.getOffset();
    offset += Arrays
      .stream((keySignature.isMajor() ? Scale.Quality.MAJOR : Scale.Quality.NATURAL_MINOR).getStepPattern())
      .limit(
        Math.floorMod(
          letter.ordinal() - key.getLetter().ordinal(),
          Letter.values().length
        )
      )
      .sum();

    offset %= MusicConstants.KEYS_IN_OCTAVE;
    if(offset - letter.getOffset() > Accidental.TRIPLE_SHARP.getOffset()){
      offset -= MusicConstants.KEYS_IN_OCTAVE;
    }
    else if(offset - letter.getOffset() < Accidental.TRIPLE_FLAT.getOffset()){
      offset += MusicConstants.KEYS_IN_OCTAVE;
    }
    
    Accidental accidental = Accidental.fromOffset(offset - letter.getOffset());

    return new Key(letter, accidental);
  }

  public final Key none(){
    return new Key(letter, Accidental.NONE);
  }

  public final Key natural(){
    return new Key(letter, Accidental.NATURAL);
  }

  public final Key sharp(){
    return new Key(letter, Accidental.SHARP);
  }

  public final Key doubleSharp(){
    return new Key(letter, Accidental.DOUBLE_SHARP);
  }

  public final Key tripleSharp(){
    return new Key(letter, Accidental.TRIPLE_SHARP);
  }

  public final Key flat(){
    return new Key(letter, Accidental.FLAT);
  }

  public final Key doubleFlat(){
    return new Key(letter, Accidental.DOUBLE_FLAT);
  }

  public final Key tripleFlat(){
    return new Key(letter, Accidental.TRIPLE_FLAT);
  }

  public final boolean isNone(){
    return accidental == Accidental.NONE;
  }

  public final boolean isNatural(){
    return accidental == Accidental.NATURAL;
  }

  public final boolean isSharp(){
    return accidental == Accidental.SHARP;
  }

  public final boolean isDoubleSharp(){
    return accidental == Accidental.DOUBLE_SHARP;
  }

  public final boolean isTripleSharp(){
    return accidental == Accidental.TRIPLE_SHARP;
  }

  public final boolean isFlat(){
    return accidental == Accidental.FLAT;
  }

  public final boolean isDoubleFlat(){
    return accidental == Accidental.DOUBLE_FLAT;
  }

  public final boolean isTripleFlat(){
    return accidental == Accidental.TRIPLE_FLAT;
  }

  public final Key getEnharmonicEquivalent(Letter letter){
    int targetOffset = getOffset();
    int initialOffset = letter.getOffset();

    int accidentalOffset = targetOffset - initialOffset;
    if(accidentalOffset > Accidental.TRIPLE_SHARP.getOffset()){
      accidentalOffset -= MusicConstants.KEYS_IN_OCTAVE;
    }
    else if(accidentalOffset < Accidental.TRIPLE_FLAT.getOffset()){
      accidentalOffset += MusicConstants.KEYS_IN_OCTAVE;
    }

    Accidental accidental = null;
    try{
      accidental = Accidental.fromOffset(accidentalOffset);
    }
    catch(Exception e){
      return null;
    }

    return new Key(letter, accidental);
  }

  public final Key getEnharmonicEquivalent(ImmutablePrioritySet<Policy> policies){
    Iterator<Policy> it = policies.iterator();
    while(it.hasNext()){
      Policy policy = it.next();
      Optional<Key> optional = Letter
        .stream()
        .limit(7)
        .map(this::getEnharmonicEquivalent)
        .filter(Objects::nonNull)
        .filter(policy::test)
        .findFirst();
      if(optional.isPresent()){
        return optional.get();
      }
    }
    return null;
  }

  public static final boolean isEnharmonic(Key a, Key b){
    return Math.floorMod(a.getOffset(), MusicConstants.KEYS_IN_OCTAVE) == Math.floorMod(b.getOffset(), MusicConstants.KEYS_IN_OCTAVE);
  }

  public static final Key fromOffset(int offset){
    return Key.fromOffset(offset, Policy.DEFAULT_PRIORITY);
  }

  public static final Key fromOffset(int offset, ImmutablePrioritySet<Policy> policies){
    if(policies.isEmpty()){
      throw new EtudeException("Policies should not be empty");
    }

    Letter letter = null;
    Accidental accidental = Accidental.NONE;

    // determine key without taking into account policies
    // maintain order of cases for fall throughs to function correctly
    switch(Math.floorMod(offset, MusicConstants.KEYS_IN_OCTAVE)){
      case 11:
        letter = Letter.B;
        break;
      case 10:
        accidental = Accidental.SHARP;
        // fall through
      case 9:
        letter = Letter.A;
        break;
      case 8:
        accidental = Accidental.SHARP;
        // fall through
      case 7:
        letter = Letter.G;
        break;
      case 6:
        accidental = Accidental.SHARP;
        // fall through
      case 5:
        letter = Letter.F;
        break;
      case 4:
        letter = Letter.E;
        break;
      case 3:
        accidental = Accidental.SHARP;
        // fall through
      case 2:
        letter = Letter.D;
        break;
      case 1:
        accidental = Accidental.SHARP;
        // fall through
      case 0:
        letter = Letter.C;
        break;
      default:
        throw new AssertionError();
    }

    return new Key(letter, accidental).getEnharmonicEquivalent(policies);
  }

  public final int getOffset(){
    return Math.floorMod(letter.getOffset() + accidental.getOffset(), MusicConstants.KEYS_IN_OCTAVE);
  }

  public static final Key fromString(String keyString){
    if(keyString == null){
      throw new EtudeException("Invalid key string: " + keyString);
    }
    else if(keyString.trim().isEmpty()){
      throw new EtudeException("Invalid key string: " + keyString + " (blank)");
    }
    Letter letter = Letter.fromChar(keyString.charAt(0));
    Accidental accidental = keyString.length() == 1 ? Accidental.NONE : Accidental.fromString(keyString.substring(1));
    return new Key(letter, accidental);
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(letter);
    builder.append(accidental);
    return builder.toString();
  }

  @Override
  public int hashCode(){
    return letter.hashCode() ^ accidental.hashCode();
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof Key)){
      return false;
    }
    if(other == this){
      return true;
    }

    Key otherKey = (Key) other;
    return letter == otherKey.letter && accidental == otherKey.accidental;
  }

  public final Letter getLetter(){
    return letter;
  }

  public final Accidental getAccidental(){
    return accidental;
  }
}
