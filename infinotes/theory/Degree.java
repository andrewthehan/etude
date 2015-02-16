
package infinotes.theory;

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
	
	public static Degree make(KeySignature keySignature, Key key){
		Key root = keySignature.getKey();
		int[] intervals = keySignature.getMode().getPattern();
		
		// check if key is a naturally occurring key in mode
		for(int i = 0; i < intervals.length; i++){
			if(Key.isEnharmonic(root.change(intervals[i]), key)){
				return make(i + 1);
			}
		}
		
		// check for sharps and flat, lower degree has priority (ex. B/Cb/C#/Db are both tonic in C Major)
		for(int i = 0; i < intervals.length; i++){
			Key current = root.change(intervals[i]);
			if(Key.isEnharmonic(current.makeFlat(), key)
				|| Key.isEnharmonic(current.makeSharp(), key)){
				return make(i + 1);
			}
		}
		
		throw new RuntimeException("Invalid Degree");
	}
	
	public Degree change(int value){
		// values are between 1 and 7 inclusive
		return make(Math.floorMod(getValue() + value - 1, VALUES.size()) + 1);
	}
	
	public int getValue(){
		return VALUES.indexOf(this) + 1;
	}
}