
package infinotes.music;

import java.util.HashMap;
import java.util.Map;

public enum Type{
	MAJOR,
	MINOR,
	AUGMENTED,
	DIMINISHED,
	MAJOR_SEVENTH,
	MINOR_SEVENTH,
	DOMINANT_SEVENTH,
	DIMINISHED_SEVENTH,
	SUSPENDED_SECOND,
	SUSPENDED_FOURTH;
	
	private static final Map<Type, int[]> patterns = new HashMap<Type, int[]>();
	
	private static final Map<Type, String> toStringMap = new HashMap<Type, String>();
	
	static{
		patterns.put(MAJOR, new int[]{0, 4, 7, 12});
		patterns.put(MINOR, new int[]{0, 3, 7, 12});
		patterns.put(AUGMENTED, new int[]{0, 4, 8, 12});
		patterns.put(DIMINISHED, new int[]{0, 3, 6, 12});
		patterns.put(MAJOR_SEVENTH, new int[]{0, 4, 7, 11});
		patterns.put(MINOR_SEVENTH, new int[]{0, 3, 7, 10});
		patterns.put(DOMINANT_SEVENTH, new int[]{0, 4, 7, 10});
		patterns.put(DIMINISHED_SEVENTH, new int[]{0, 3, 6, 9});
		patterns.put(SUSPENDED_SECOND, new int[]{0, 2, 7, 12});
		patterns.put(SUSPENDED_FOURTH, new int[]{0, 5, 7, 12});
		
		toStringMap.put(MAJOR, "maj");
		toStringMap.put(MINOR, "min");
		toStringMap.put(AUGMENTED, "aug");
		toStringMap.put(DIMINISHED, "dim");
		toStringMap.put(MAJOR_SEVENTH, "maj7");
		toStringMap.put(MINOR_SEVENTH, "min7");
		toStringMap.put(DOMINANT_SEVENTH, "dom7");
		toStringMap.put(DIMINISHED_SEVENTH, "dim7");
		toStringMap.put(SUSPENDED_SECOND, "sus2");
		toStringMap.put(SUSPENDED_FOURTH, "sus4");
	}
	
	public int[] getPattern(){
		return patterns.get(this);
	}
	
	@Override
	public String toString(){
		return toStringMap.get(this);
	}
}