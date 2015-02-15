
package infinotes.theory;

import java.util.HashMap;
import java.util.Map;

public enum Duration{
	WHOLE,
	HALF,
	QUARTER,
	EIGHTH,
	SIXTEENTH,
	THIRTY_SECOND,
	SIXTY_FOURTH,
	HUNDRED_TWENTY_EIGHTH;
	
	private static final Map<Duration, Double> VALUES = new HashMap<Duration, Double>();
	
	private static final Map<Duration, String> TO_STRING_MAP = new HashMap<Duration, String>();
	
	//private boolean isDotted = false;
	
	static{
		VALUES.put(WHOLE, 1.0);
		VALUES.put(HALF, 0.5);
		VALUES.put(QUARTER, 0.25);
		VALUES.put(EIGHTH, 0.125);
		VALUES.put(SIXTEENTH, 0.0625);
		VALUES.put(THIRTY_SECOND, 0.03125);
		VALUES.put(SIXTY_FOURTH, 0.015625);
		VALUES.put(HUNDRED_TWENTY_EIGHTH, 0.0078125);
		
		TO_STRING_MAP.put(WHOLE, "w");
		TO_STRING_MAP.put(HALF, "h");
		TO_STRING_MAP.put(QUARTER, "q");
		TO_STRING_MAP.put(EIGHTH, "i");
		TO_STRING_MAP.put(SIXTEENTH, "s");
		TO_STRING_MAP.put(THIRTY_SECOND, "t");
		TO_STRING_MAP.put(SIXTY_FOURTH, "x");
		TO_STRING_MAP.put(HUNDRED_TWENTY_EIGHTH, "o");
	}
	
	public static Duration make(double value){
		return VALUES.entrySet().stream().filter(kv -> kv.getValue() == value).findFirst().get().getKey();
	}
	
	/* public boolean isDotted(){
		return isDotted;
	}
	
	public void setDotted(boolean isDotted){
		this.isDotted = isDotted;
	} */
	
	public double getValue(){
		return VALUES.get(this);// * (isDotted ? 1.5 : 1);
	}
	
	@Override
	public String toString(){
		return TO_STRING_MAP.get(this);// + (isDotted ? "." : "");
	}
}