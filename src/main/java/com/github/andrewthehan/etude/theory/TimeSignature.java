
package com.github.andrewthehan.etude.theory;

import java.util.Objects;

public final class TimeSignature{
  public static final TimeSignature COMMON_TIME = new TimeSignature(4, Value.QUARTER);

  private final int beatsPerMeasure;
  private final Value oneBeat;

  public TimeSignature(int beatsPerMeasure, int oneBeat){
    this.beatsPerMeasure = beatsPerMeasure;
    this.oneBeat = new Value(Value.Type.fromBaseDuration(1.0 / oneBeat));
  }

  public TimeSignature(int beatsPerMeasure, Value oneBeat){
    this.beatsPerMeasure = beatsPerMeasure;
    this.oneBeat = oneBeat;
  }

  public final double getDurationOfMeasure(){
    return beatsPerMeasure * oneBeat.getDuration();
  }

  @Override
  public final int hashCode(){
    return Objects.hash(beatsPerMeasure, oneBeat);
  }

  @Override
  public final boolean equals(Object other){
    if(!(other instanceof TimeSignature)){
      return false;
    }
    if(other == this){
      return true;
    }

    TimeSignature otherTimeSignature = (TimeSignature) other;

    return Objects.deepEquals(
      new Object[]{
        beatsPerMeasure, oneBeat
      },
      new Object[]{
        otherTimeSignature.getBeatsPerMeasure(), otherTimeSignature.getOneBeat()
      }
    );
  }

  public final int getBeatsPerMeasure(){
    return beatsPerMeasure;
  }

  public final Value getOneBeat(){
    return oneBeat;
  }
}
