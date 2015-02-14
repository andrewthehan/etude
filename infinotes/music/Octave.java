
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Octave{
	// each octave covers C D E F G A B
	// do not change order
	ZERO,
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN;
	
	private static final List<Octave> VALUES = new ArrayList<Octave>(Arrays.asList(Octave.values()));

	public int getValue(){
		return VALUES.indexOf(this);
	}
	
	public Octave change(int amount){
		return VALUES.get(getValue() + amount);
	}
	
	@Override
	public String toString(){
		return String.valueOf(getValue());
	}
}