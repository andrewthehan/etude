
package jmtapi.theory;

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

  @Override
  public String toString(){
  	return symbol;
  }
}