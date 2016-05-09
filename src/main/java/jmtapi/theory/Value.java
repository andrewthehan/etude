
package jmtapi.theory;

import jmtapi.util.RegEx;

public enum Value{
  DOUBLE_WHOLE(2.0),
  WHOLE(1.0),
  HALF(0.5),
  QUARTER(0.25),
  EIGHTH(0.125),
  SIXTEENTH(0.0625),
  THIRTY_SECOND(0.03125),
  SIXTY_FOURTH(0.015625),
  HUNDRED_TWENTY_EIGHTH(0.0078125),
  TWO_HUNDRED_FIFTY_SIXTH(0.00390625);

  private final double duration;

  private Value(double duration){
    this.duration = duration;
  }

  public static final Value fromDuration(double duration){
    if(duration == 0){
      throw new RuntimeException("Invalid duration: " + duration + " (cannot be zero)");
    }
    double index = Math.log(duration) / Math.log(2);
    // if index is not an integer value
    if(index % 1 != 0){
      throw new RuntimeException("Invalid duration: " + duration + " (cannot be represented as a value)");
    }

    // 1 - index due to the order of enums
    return Value.values()[1 - (int) index];
  }

  public static final Value fromString(String valueString){
    if(valueString == null){
      throw new RuntimeException("Invalid value string: " + valueString);
    }
    else if(valueString.trim().isEmpty()){
      throw new RuntimeException("Invalid value string: " + valueString + " (blank)");
    }

    double duration = 0;
    if(valueString.matches("\\d{1,}\\/\\d{1,}")){
      String[] durationStrings = RegEx.extract("\\d{1,}\\/\\d{1,}", valueString).split("/");
      duration = Double.parseDouble(durationStrings[0]) / Double.parseDouble(durationStrings[1]);
    }
    else{
      try{
        duration = Double.parseDouble(valueString);
      }
      catch(Exception e){
        throw new RuntimeException("Invalid value string: " + valueString + " (does not match a valid form)");
      }
    }

    return Value.fromDuration(duration);
  }

  @Override
  public String toString(){
    return String.valueOf(duration);
  }

  public final double getDuration(){
    return duration;
  }
}
