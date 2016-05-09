
package jmtapi.theory;

import java.util.Arrays;

public enum Inversion{
  ROOT, FIRST, SECOND, THIRD;

  public int getValue(){
    return Arrays.asList(Inversion.values()).indexOf(this);
  }
}
