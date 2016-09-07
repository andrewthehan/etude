
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.exception.EtudeException;
import com.github.andrewthehan.etude.util.RegEx;

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
      throw new EtudeException("Invalid duration: " + duration + " (cannot be zero)");
    }
    double index = Math.log(duration) / Math.log(2);
    // if index is not an integer value
    if(index % 1 != 0){
      throw new EtudeException("Invalid duration: " + duration + " (cannot be represented as a value)");
    }

    // 1 - index due to the order of enums
    return Value.values()[1 - (int) index];
  }

  public static final Value fromString(String valueString){
    if(valueString == null){
      throw new EtudeException("Invalid value string: " + valueString);
    }
    else if(valueString.trim().isEmpty()){
      throw new EtudeException("Invalid value string: " + valueString + " (blank)");
    }

    Value value;

    double duration = 0;
    if(valueString.matches("\\d+\\/\\d+")){
      String[] durationStrings = RegEx.extract("\\d+\\/\\d+", valueString).split("/");
      duration = Double.parseDouble(durationStrings[0]) / Double.parseDouble(durationStrings[1]);
      value = Value.fromDuration(duration);
    }
    else{
      try{
        value = Value.valueOf(valueString);
      }
      catch(Exception e){
        try{
          duration = Double.parseDouble(valueString);
          value = Value.fromDuration(duration);
        }
        catch(EtudeException ee){
          throw ee;
        }
        catch(Exception ee){
          throw new EtudeException("Invalid value string: " + valueString);
        }
      }
    }

    return value;
  }

  @Override
  public String toString(){
    return name();
  }

  public final double getDuration(){
    return duration;
  }
}
