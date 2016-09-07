
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface Policy extends Predicate<Key>{
	public static final Policy NONE_OR_NATURAL = k -> k.getAccidental().getOffset() == Accidental.NONE.getOffset();
  public static final Policy SHARP = matchAccidental(Accidental.SHARP);
  public static final Policy DOUBLE_SHARP = matchAccidental(Accidental.DOUBLE_SHARP);
  public static final Policy TRIPLE_SHARP = matchAccidental(Accidental.TRIPLE_SHARP);
	public static final Policy SHARPS = k -> k.getAccidental().getOffset() > Accidental.NONE.getOffset();
  public static final Policy FLAT = matchAccidental(Accidental.FLAT);
  public static final Policy DOUBLE_FLAT = matchAccidental(Accidental.DOUBLE_FLAT);
  public static final Policy TRIPLE_FLAT = matchAccidental(Accidental.TRIPLE_FLAT);
	public static final Policy FLATS = k -> k.getAccidental().getOffset() < Accidental.NONE.getOffset();

	public static ImmutablePrioritySet<Policy> DEFAULT_PRIORITY = Policy.prioritize(
    NONE_OR_NATURAL,
    SHARP,
    FLAT
  );

	public static ImmutablePrioritySet<Policy> prioritize(Policy... policies){
	  return ImmutablePrioritySet.of(policies);
	}

  public static Policy matchLetter(Letter letter){
    return k -> k.getLetter() == letter;
  }

  public static Policy matchAccidental(Accidental accidental){
    return k -> k.getAccidental() == accidental;
  }

  public static Policy matchKeySignature(KeySignature keySignature){
    Key[] keys = Letter
      .stream()
      .limit(7)
      .map(Key::new)
      .map(k -> k.apply(keySignature))
      .toArray(Key[]::new);
    return k -> keys[k.getLetter().ordinal()].equals(k);
  }
}