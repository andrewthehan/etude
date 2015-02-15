
package infinotes.theory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public enum Mode{
	IONIAN,
	DORIAN,
	PHRYGIAN,
	LYDIAN,
	MIXOLYDIAN,
	AEOLIAN,
	LOCRIAN,
	MAJOR,
	NATURAL_MINOR,
	HARMONIC_MINOR;
	//MELODIC_MINOR;
	
	private static final Map<Mode, int[]> PATTERNS = new HashMap<Mode, int[]>();
	
	static{
		/* PATTERNS.put(IONIAN, new int[]{2, 2, 1, 2, 2, 2, 1});
		PATTERNS.put(DORIAN, new int[]{2, 1, 2, 2, 2, 1, 2});
		PATTERNS.put(PHRYGIAN, new int[]{1, 2, 2, 2, 1, 2, 2});
		PATTERNS.put(LYDIAN, new int[]{2, 2, 2, 1, 2, 2, 1});
		PATTERNS.put(MIXOLYDIAN, new int[]{2, 2, 1, 2, 2, 1, 2});
		PATTERNS.put(AEOLIAN, new int[]{2, 1, 2, 2, 1, 2, 2});
		PATTERNS.put(LOCRIAN, new int[]{1, 2, 2, 1, 2, 2, 2});
		PATTERNS.put(MAJOR, new int[]{2, 2, 1, 2, 2, 2, 1});
		PATTERNS.put(NATURAL_MINOR, new int[]{2, 1, 2, 2, 1, 2, 2});
		PATTERNS.put(HARMONIC_MINOR, new int[]{2, 1, 2, 2, 1, 3, 1}); */
		PATTERNS.put(IONIAN, new int[]{0, 2, 4, 5, 7, 9, 11});
		PATTERNS.put(DORIAN, new int[]{0, 2, 3, 5, 7, 9, 10});
		PATTERNS.put(PHRYGIAN, new int[]{0, 1, 3, 5, 7, 8, 10});
		PATTERNS.put(LYDIAN, new int[]{0, 2, 4, 6, 7, 9, 11});
		PATTERNS.put(MIXOLYDIAN, new int[]{0, 2, 4, 5, 7, 9, 10});
		PATTERNS.put(AEOLIAN, new int[]{0, 2, 3, 5, 7, 8, 10});
		PATTERNS.put(LOCRIAN, new int[]{0, 1, 3, 5, 6, 8, 10});
		PATTERNS.put(MAJOR, new int[]{0, 2, 4, 5, 7, 9, 11});
		PATTERNS.put(NATURAL_MINOR, new int[]{0, 2, 3, 5, 7, 8, 10});
		PATTERNS.put(HARMONIC_MINOR, new int[]{0, 2, 3, 5, 7, 8, 11});
	}
	
	public int[] getPattern(){
		return PATTERNS.get(this);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		IntStream.of(getPattern()).forEach(i -> builder.append(i + " "));
		return builder.toString();
	}
}