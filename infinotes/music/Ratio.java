
package infinotes.music;

import java.util.HashMap;
import java.util.Map;

public enum Ratio{
	PERFECT,
	MAJOR,
	MINOR,
	DIMINISHED,
	AUGMENTED;
	
	private static final Map<Ratio, String> TO_STRING_MAP = new HashMap<Ratio, String>();
	
	static{
		TO_STRING_MAP.put(PERFECT, "P");
		TO_STRING_MAP.put(MAJOR, "M");
		TO_STRING_MAP.put(MINOR, "m");
		TO_STRING_MAP.put(DIMINISHED, "d");
		TO_STRING_MAP.put(AUGMENTED, "A");
	}
	
	@Override
	public String toString(){
		return TO_STRING_MAP.get(this);
	}
}