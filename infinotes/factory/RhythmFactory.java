
package infinotes.factory;

import infinotes.music.Duration;
import infinotes.music.KeySignature;
import infinotes.music.TimeSignature;
import infinotes.music.Voice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RhythmFactory{
	private static final Random R = new Random();
	private KeySignature keySignature;
	private TimeSignature timeSignature;
	private Voice voice;
	
	private RhythmFactory(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.voice = voice;
	}
	
	public static RhythmFactory make(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		return new RhythmFactory(keySignature, timeSignature, voice);
	}
	
	public Duration[] makeRhythm(double length){
		List<Duration> rhythm = new ArrayList<Duration>();
		switch(voice.getStyle()){
			case MELODY:
				for(double soFar = 0; soFar < length; ){
					Duration duration;
					do{
						switch(R.nextInt(3)){
							case 0: 
								duration = Duration.HALF;
								break;
							case 1:
								duration = Duration.QUARTER;
								break;
							case 2:
								duration = Duration.EIGHTH;
								break;
							default:
								duration = null;
						}
					}while(soFar + duration.getValue() > length);
					rhythm.add(duration);
					soFar += duration.getValue();
				}
				break;
			case HARMONY:
				// assuming length is always whole note (1.0)
				rhythm.add(Duration.QUARTER);
				rhythm.add(Duration.QUARTER);
				rhythm.add(Duration.QUARTER);
				rhythm.add(Duration.QUARTER);
				break;
		}
		return rhythm.toArray(new Duration[rhythm.size()]);
	}
}