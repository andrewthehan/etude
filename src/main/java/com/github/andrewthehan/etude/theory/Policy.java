
package com.github.andrewthehan.etude.theory;

import java.util.function.Predicate;

import com.github.andrewthehan.etude.util.ImmutablePrioritySet;

@FunctionalInterface
public interface Policy extends Predicate<Key> {
  public static final Policy NONE = matchAccidental(null);
  public static final Policy NATURAL = matchAccidental(Accidental.NATURAL);
  public static final Policy NONE_OR_NATURAL = k -> NONE.test(k) || NATURAL.test(k);
  public static final Policy SHARP = matchAccidental(Accidental.SHARP);
  public static final Policy DOUBLE_SHARP = matchAccidental(Accidental.DOUBLE_SHARP);
  public static final Policy TRIPLE_SHARP = matchAccidental(Accidental.TRIPLE_SHARP);
  public static final Policy SHARPS = k -> SHARP.test(k) || DOUBLE_SHARP.test(k) || TRIPLE_SHARP.test(k);
  public static final Policy FLAT = matchAccidental(Accidental.FLAT);
  public static final Policy DOUBLE_FLAT = matchAccidental(Accidental.DOUBLE_FLAT);
  public static final Policy TRIPLE_FLAT = matchAccidental(Accidental.TRIPLE_FLAT);
  public static final Policy FLATS = k -> FLAT.test(k) || DOUBLE_FLAT.test(k) || TRIPLE_FLAT.test(k);

  public static final ImmutablePrioritySet<Policy> DEFAULT_PRIORITY = Policy.prioritize(NONE, SHARP, FLAT);

  public static ImmutablePrioritySet<Policy> prioritize(Policy... policies) {
    return ImmutablePrioritySet.of(policies);
  }

  public static Policy matchLetter(Letter letter) {
    return k -> k.getLetter() == letter;
  }

  public static Policy matchAccidental(Accidental accidental) {
    return k -> k.getAccidental().orElse(null) == accidental;
  }

  public static Policy matchKeySignature(KeySignature keySignature) {
    Key[] keys = Letter.stream().limit(Letter.SIZE).map(Key::new).map(k -> k.apply(keySignature)).toArray(Key[]::new);
    return k -> keys[k.getLetter().ordinal()].equals(k);
  }
}