
package com.github.andrewthehan.etude.theory;

public enum Accidental{
  TRIPLE_FLAT(-3, "bbb"),
  DOUBLE_FLAT(-2, "bb"),
  FLAT(-1, "b"),
  NONE(0, ""),
  NATURAL(0, "n"),
  SHARP(1, "#"),
  DOUBLE_SHARP(2, "x"),
  TRIPLE_SHARP(3, "#x");

  public static enum Policy{
    MAINTAIN_LETTER, PRIORITIZE_NATURAL, PRIORITIZE_SHARP, PRIORITIZE_FLAT
  }

  private final int offset;
  private final String symbol;

  private Accidental(int offset, String symbol){
    this.offset = offset;
    this.symbol = symbol;
  }

  public static final Accidental fromOffset(int offset){
    switch(offset){
      case -3: return TRIPLE_FLAT;
      case -2: return DOUBLE_FLAT;
      case -1: return FLAT;
      case 0: return NATURAL;
      case 1: return SHARP;
      case 2: return DOUBLE_SHARP;
      case 3: return TRIPLE_SHARP;
      default: throw new RuntimeException("Invalid accidental offset: " + offset);
    }
  }

  public final int getOffset(){
    return offset;
  }

  public static final Accidental fromString(String accidentalString){
    switch(accidentalString){
      case "bbb": return TRIPLE_FLAT;
      case "bb": return DOUBLE_FLAT;
      case "b": return FLAT;
      case "": return NONE;
      case "n": return NATURAL;
      case "#": return SHARP;
      case "x": return DOUBLE_SHARP;
      case "#x": return TRIPLE_SHARP;
      default: throw new RuntimeException("Invalid accidental string: " + accidentalString);
    }
  }

  @Override
  public String toString(){
    return symbol;
  }
}
