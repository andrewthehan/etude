
package infinotes.music;

public class Interval{
	private Ratio ratio;
	private int distance;
	
	private Interval(Ratio ratio, int distance){
		this.ratio = ratio;
		this.distance = distance;
	}
	
	public static Interval make(Ratio ratio, int distance){
		if(distance <= 0){
			throw new RuntimeException("Invalid interval");
		}
		switch(ratio){
			case PERFECT:
				switch(distance){
					case 1:	case 4:	case 5:
						break;
					default:
						throw new RuntimeException("Invalid interval");
				}
				break;
			case MAJOR:	case MINOR:
				switch(distance){
					case 2:	case 3:	case 6:	case 7:
						break;
					default:
						throw new RuntimeException("Invalid interval");
				}
				break;
		}
		return new Interval(ratio, distance);
	}
	
	public int getHalfStepAmount(){
		int amount = Mode.IONIAN.getPattern()[(((distance - 2) % Key.UNIQUE_LETTER_COUNT) + Key.UNIQUE_LETTER_COUNT) % Key.UNIQUE_LETTER_COUNT];
		switch(ratio){
			case PERFECT: case MAJOR:
				break;
			case MINOR:
				amount--;
				break;
			case DIMINISHED:
				switch(distance){
					case 1: case 4: case 5:
						amount--;
						break;
					case 2: case 3: case 6: case 7:
						amount -= 2;
						break;
				}
				break;
			case AUGMENTED:
				amount++;
				break;
		}
		return amount;
	}
	
	public Ratio getType(){
		return ratio;
	}
	
	public int getDistance(){
		return distance;
	}
}