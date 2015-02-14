
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Degree{
	TONIC,
	SUPERTONIC,
	MEDIANT,
	SUBDOMINANT,
	DOMINANT,
	SUBMEDIANT,
	LEADING_TONE;
	
	private static final List<Degree> VALUES = new ArrayList<Degree>(Arrays.asList(Degree.values()));
	
	public static Degree make(int value){
		if(value > VALUES.size() || value < 1){
			throw new RuntimeException("Invalid degree");
		}
		return VALUES.get(value - 1);
	}
	
	public int getValue(){
		return VALUES.indexOf(this) + 1;
	}
	
	@Override
	public String toString(){
		return String.valueOf(getValue());
	}
}