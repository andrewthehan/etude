
package infinotes.factory;

import infinotes.theory.Duration;
import infinotes.theory.KeySignature;
import infinotes.theory.TimeSignature;
import infinotes.theory.Voice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RhythmFactory{
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final Voice voice;
	
	private RhythmFactory(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.voice = voice;
	}
	
	public static RhythmFactory make(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		return new RhythmFactory(keySignature, timeSignature, voice);
	}
	
	public Duration[] makeRhythm(Duration duration){
		List<Duration> rhythm = new ArrayList<Duration>();
		switch(voice.getStyle()){
			case MELODY:
				Duration durationToAdd;
				for(double soFar = 0; soFar < duration.getValue(); soFar += durationToAdd.getValue()){
					do{
						switch(R.nextInt(3)){
							case 0: 
								durationToAdd = Duration.HALF;
								break;
							case 1:
								durationToAdd = Duration.QUARTER;
								break;
							case 2:
								durationToAdd = Duration.EIGHTH;
								break;
							default:
								durationToAdd = null;
						}
					}while(soFar + durationToAdd.getValue() > duration.getValue());
					rhythm.add(durationToAdd);
				}
				break;
			case HARMONY:
				for(int i = 0; i < timeSignature.getBeatCount(); i++){
					rhythm.add(timeSignature.getBeatDuration());
				}
				break;
		}
		return rhythm.toArray(new Duration[rhythm.size()]);
	}
}