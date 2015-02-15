
package infinotes.theory;

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
				if(!isPerfect(distance)){
					throw new RuntimeException("Invalid interval");
				}
				break;
			case MAJOR: case MINOR:
				if(isPerfect(distance)){
					throw new RuntimeException("Invalid interval");
				}
				break;
		}
		return new Interval(ratio, distance);
	}
	
	public int getHalfStepAmount(){
		int amount = Mode.IONIAN.getPattern()[Math.floorMod(distance - 1, Key.UNIQUE_LETTER_COUNT)];
		switch(ratio){
			case PERFECT: case MAJOR:
				break;
			case MINOR:
				amount--;
				break;
			case DIMINISHED:
				amount -= isPerfect(distance) ? 1 : 2;
				break;
			case AUGMENTED:
				amount++;
				break;
		}
		return amount;
	}
	
	private static boolean isPerfect(int distance){
		int mod = distance % 7;
		return mod == 1 || mod == 4 ||  mod == 5;
	}
	
	public Ratio getType(){
		return ratio;
	}
	
	public int getDistance(){
		return distance;
	}
}