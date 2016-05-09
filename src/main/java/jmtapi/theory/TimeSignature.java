
package jmtapi.theory;

public final class TimeSignature{
  public static final TimeSignature COMMON_TIME = new TimeSignature(4, Value.QUARTER);

  private final int beatsPerMeasure;
  private final Value oneBeat;

  public TimeSignature(int beatsPerMeasure, int oneBeat){
    this.beatsPerMeasure = beatsPerMeasure;
    this.oneBeat = Value.fromDuration(1.0 / oneBeat);
  }

  public TimeSignature(int beatsPerMeasure, Value oneBeat){
    this.beatsPerMeasure = beatsPerMeasure;
    this.oneBeat = oneBeat;
  }

  public final double getDurationOfMeasure(){
    return beatsPerMeasure * oneBeat.getDuration();
  }

  public final int getBeatsPerMeasure(){
    return beatsPerMeasure;
  }

  public final Value getOneBeat(){
    return oneBeat;
  }
}
