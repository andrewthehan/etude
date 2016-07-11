
package jmtapi.theory;

import java.util.Arrays;

public final class Scale{
  private final KeySignature keySignature;

  public Scale(KeySignature keySignature){
    this.keySignature = keySignature;
  }

  public final Key[] getKeys(){
    return Arrays
      .stream(Degree.values())
      .map(keySignature::keyOf)
      .toArray(Key[]::new);
  }

  @Override
  public String toString(){
    return keySignature.toString();
  }
}