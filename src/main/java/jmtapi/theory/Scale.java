
package jmtapi.theory;

public final class Scale{
  private final KeySignature keySignature;

  public Scale(KeySignature keySignature){
    this.keySignature = keySignature;
  }

  public final Pitch[] getPitches(){
    return null;
  }

  @Override
  public String toString(){
    return keySignature.toString();
  }
}