
package com.github.andrewthehan.etude.theory;

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
      throw new RuntimeException("Unable to apply crescendo on " + this);
    }
    return Dynamic.values()[index];
  }

  public Dynamic diminuendo(){
    int index = ordinal() - 1;
    if(index < 0){
      throw new RuntimeException("Unable to apply diminuendo on " + this);
    }
    return Dynamic.values()[index];
  }

  @Override
  public String toString(){
  	return symbol;
  }
}