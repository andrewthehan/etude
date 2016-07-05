  
package jmtapi.theory;

public final class Interval{
  public enum Quality{
    PERFECT("P"), MAJOR("M"), MINOR("m"), DIMINISHED("d"), DOUBLY_DIMINISHED("dd"), AUGMENTED("A"), DOUBLY_AUGMENTED("AA");

    private final String symbol;

    private Quality(String symbol){
      this.symbol = symbol;
    }

    @Override
    public String toString(){
      return symbol;
    }
  }

  private final Quality quality;
  private final int number;

  public Interval(Quality quality, int number){
    if(number <= 0){
      throw new RuntimeException("Invalid interval: " + quality + number + " (number must be a positive integer)");
    }
    switch(quality){
      case PERFECT:
        if(!Interval.isPerfect(number)){
          throw new RuntimeException("Invalid interval: " + quality + number + " (number cannot have a perfect quality)");
        }
        break;
      case MAJOR: case MINOR:
        if(Interval.isPerfect(number)){
          throw new RuntimeException("Invalid interval: " + quality + number + " (number cannot have major or minor quality)");
        }
        break;
      case DIMINISHED: case DOUBLY_DIMINISHED: case AUGMENTED: case DOUBLY_AUGMENTED:
        break;
    }
    this.quality = quality;
    this.number = number;
  }

  public static final Interval between(Pitch a, Pitch b){
    Letter letterA = a.getKey().getLetter();
    Letter letterB = b.getKey().getLetter();

    if(a.isHigherThan(b) && a.getOctave() == b.getOctave() && letterA.getOffset() > letterB.getOffset()){
      throw new RuntimeException("Cannot create interval with negative number");
    }

    int number = 1 + Letter.asList(letterA).indexOf(letterB);

    int offset = b.getProgramNumber() - a.getProgramNumber();
    for(int i = 0; i < number - 1; ++i){
      offset -= Mode.MAJOR.getStepPattern()[i];
    }

    Quality quality;
    switch(offset){
      case -3:
        if(Interval.isPerfect(number)){
          throw new RuntimeException("Cannot create interval for pitches: " + a + " -> " + b);
        }
        quality = Quality.DOUBLY_DIMINISHED;
        break;
      case -2:
        quality = Interval.isPerfect(number) ? Quality.DOUBLY_DIMINISHED : Quality.DIMINISHED;
        break;
      case -1:
        quality = Interval.isPerfect(number) ? Quality.DIMINISHED : Quality.MINOR;
        break;
      case 0:
        quality = Interval.isPerfect(number) ? Quality.PERFECT : Quality.MAJOR;
        break;
      case 1:
        quality = Quality.AUGMENTED;
        break;
      case 2:
        quality = Quality.DOUBLY_AUGMENTED;
        break;
      default:
        throw new RuntimeException("Cannot create interval for pitches: " + a + " -> " + b);
    }

    return new Interval(quality, number);
  }

  public final int getOffset(){
    // initialize offset to take into account octave
    int offset = (number - 1) / MusicConstants.UNIQUE_LETTER_COUNT * MusicConstants.KEYS_IN_OCTAVE;

    // take into account normalized number (within the range of an octave)
    for(int i = 0; i < (number - 1) % MusicConstants.UNIQUE_LETTER_COUNT; ++i){
      offset += Mode.MAJOR.getStepPattern()[i];
    }

    // take into account quality
    switch(quality){
      case PERFECT: case MAJOR:
        break;
      case MINOR:
        --offset;
        break;
      case DIMINISHED:
        offset -= Interval.isPerfect(number) ? 1 : 2;
        break;
      case DOUBLY_DIMINISHED:
        offset -= Interval.isPerfect(number) ? 2 : 3;
        break;
      case AUGMENTED:
        ++offset;
        break;
      case DOUBLY_AUGMENTED:
        offset += 2;
        break;
    }
    return offset;
  }

  public static final boolean isPerfect(int number){
    int normalized = number % 7;
    return normalized == 1 || normalized == 4 || normalized == 5;
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(quality);
    builder.append(number);
    return builder.toString();
  }

  public final Quality getQuality(){
    return quality;
  }

  public final int getNumber(){
    return number;
  }
}
