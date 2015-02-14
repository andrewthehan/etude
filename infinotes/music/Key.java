
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Key{
	// do not change order
	A_FLAT, A, A_SHARP,
	B_FLAT, B, B_SHARP,
	C_FLAT, C, C_SHARP,
	D_FLAT, D, D_SHARP, 
	E_FLAT, E, E_SHARP,
	F_FLAT, F, F_SHARP,
	G_FLAT, G, G_SHARP;
	
	public static final int UNIQUE_LETTER_COUNT = 7;
	
	public static final int UNIQUE_KEY_COUNT = 12;
	
	private static final List<Key> KEYS = new ArrayList<Key>(Arrays.asList(Key.values()));
	
	private static final Map<Key, String> TO_STRING_MAP = new HashMap<Key, String>();
	
	static{
		TO_STRING_MAP.put(A_FLAT, "Ab");
		TO_STRING_MAP.put(A, "A");
		TO_STRING_MAP.put(A_SHARP, "A#");
		TO_STRING_MAP.put(B_FLAT, "Bb");
		TO_STRING_MAP.put(B, "B");
		TO_STRING_MAP.put(B_SHARP, "B#");
		TO_STRING_MAP.put(C_FLAT, "Cb");
		TO_STRING_MAP.put(C, "C");
		TO_STRING_MAP.put(C_SHARP, "C#");
		TO_STRING_MAP.put(D_FLAT, "Db");
		TO_STRING_MAP.put(D, "D");
		TO_STRING_MAP.put(D_SHARP, "D#");
		TO_STRING_MAP.put(E_FLAT, "Eb");
		TO_STRING_MAP.put(E, "E");
		TO_STRING_MAP.put(E_SHARP, "E#");
		TO_STRING_MAP.put(F_FLAT, "Fb");
		TO_STRING_MAP.put(F, "F");
		TO_STRING_MAP.put(F_SHARP, "F#");
		TO_STRING_MAP.put(G_FLAT, "Gb");
		TO_STRING_MAP.put(G, "G");
		TO_STRING_MAP.put(G_SHARP, "G#");
	}
	
	public static Key make(Key key, Mode mode, Degree degree){
		Key nextKey = key;
		int amount = mode.getPattern()[(((degree.getValue() - 2) % Key.UNIQUE_LETTER_COUNT) + Key.UNIQUE_LETTER_COUNT) % Key.UNIQUE_LETTER_COUNT];
		amount = (amount == 12) ? 0 : amount;
		for(int i = 0; i < amount; i++){
			nextKey = nextKey.makeSharp();
		}
		return nextKey;
	}
	
	public Key makeSharp(){
		int index = KEYS.indexOf(this);
		Key toReturn;
		if(toString().contains("#")){
			index++;
		}
		do{
			index = (index + 1) % KEYS.size();
			toReturn = KEYS.get(index);
		}while(Key.isEnharmonic(this, toReturn));
		return toReturn;
	}
	
	public Key makeFlat(){
		int index = KEYS.indexOf(this);
		Key toReturn;
		if(toString().contains("b")){
			index--;
		}
		do{
			index = (index - 1) % KEYS.size();
			toReturn = KEYS.get(index);
		}while(Key.isEnharmonic(this, toReturn));
		return toReturn;
	}
	
	public Key change(int amount){
		amount %= UNIQUE_KEY_COUNT;
		Key toReturn;
		for(toReturn = this; amount > 0; amount--){
			toReturn = toReturn.makeSharp();
		}
		return toReturn;
	}
	
	public static boolean isEnharmonic(Key keyA, Key keyB){
		String a = keyA.toString();
		String b = keyB.toString();
		char letterA = a.charAt(0);
		char letterB = b.charAt(0);
		int charDifference = Math.abs(letterA - letterB);
		boolean isSameLetter = charDifference == 0;
		boolean isNextTo = charDifference == 1 || charDifference == 6;
		boolean isSpecial = ((letterA == 'B' && letterB == 'C') || (letterA == 'C' && letterB == 'B')
			|| (letterA == 'E' && letterB == 'F') || (letterA == 'F' && letterB == 'E'));
		if(isSameLetter){
			return a.equals(b);
		}
		else if(isNextTo){
			if((a.contains("#") && b.contains("b")) || (a.contains("b") && b.contains("#"))){
				return !isSpecial;
			}
			else if(a.matches(".[#b]") ^ b.matches(".[#b]")){
				if((letterA < letterB && (a.contains("#") ^ b.contains("b")))
					|| (letterA > letterB && (a.contains("b") ^ b.contains("#")))){
					return isSpecial;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString(){
		return TO_STRING_MAP.get(this);
	}
}