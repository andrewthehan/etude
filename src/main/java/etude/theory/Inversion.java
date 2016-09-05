
package etude.theory;

import java.util.Arrays;

public enum Inversion{
  ROOT, FIRST, SECOND, THIRD;

  public int getValue(){
    return ordinal();
  }
}
