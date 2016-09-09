
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;

public enum Dynamic{
  PIANISSISSIMO("ppp"),
  PIANISSIMO("pp"),
  PIANO("p"),
  MEZZO_PIANO("mp"),
  MEZZO_FORTE("mf"),
  FORTE("f"),
  FORTISSIMO("ff"),
  FORTISSISSIMO("fff");

  private final String symbol;

  private Dynamic(String symbol){
    this.symbol = symbol;
  }

  public Dynamic crescendo(){
    int index = ordinal() + 1;
    if(index >= Dynamic.values().length){
      throw new EtudeException("Unable to apply crescendo on " + this);
    }
    return Dynamic.values()[index];
  }

  public Dynamic diminuendo(){
    int index = ordinal() - 1;
    if(index < 0){
      throw new EtudeException("Unable to apply diminuendo on " + this);
    }
    return Dynamic.values()[index];
  }

  public static Dynamic fromString(String dynamicString){
    switch(dynamicString){
      case "ppp": return PIANISSISSIMO;
      case "pp": return PIANISSIMO;
      case "p": return PIANO;
      case "mp": return MEZZO_PIANO;
      case "mf": return MEZZO_FORTE;
      case "f": return FORTE;
      case "ff": return FORTISSIMO;
      case "fff": return FORTISSISSIMO;
      default: throw new EtudeException("Invalid dynamic string: " + dynamicString);
    }
  }

  @Override
  public String toString(){
    return symbol;
  }
}