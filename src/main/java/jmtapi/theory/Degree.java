
package jmtapi.theory;

import java.util.Arrays;

public enum Degree{
  TONIC, SUPERTONIC, MEDIANT, SUBDOMINANT, DOMINANT, SUBMEDIANT, LEADING_TONE;

  public final static Degree fromValue(int value){
    if(value < 1 || value > Degree.values().length){
      throw new RuntimeException("Invalid value: " + value);
    }
    return Arrays.asList(Degree.values()).get(value - 1);
  }

  public final int getValue(){
    return Arrays.asList(Degree.values()).indexOf(this) + 1;
  }
}
