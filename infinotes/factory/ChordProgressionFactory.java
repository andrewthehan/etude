
package infinotes.factory;

import infinotes.theory.ChordProgression;
import infinotes.theory.Degree;
import infinotes.theory.Duration;
import infinotes.theory.KeySignature;
import infinotes.theory.TimeSignature;
import infinotes.theory.Type;

import java.util.Random;

public class ChordProgressionFactory{	
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	
	private ChordProgressionFactory(KeySignature keySignature, TimeSignature timeSignature){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
	}
	
	public static ChordProgressionFactory make(KeySignature keySignature, TimeSignature timeSignature){
		return new ChordProgressionFactory(keySignature, timeSignature);
	}
	
	public ChordProgression makeChordProgression(int numberOfMeasures){
		ChordProgression.Builder builder = ChordProgression.builder();
		
		Duration oneMeasure = Duration.make(timeSignature.getMeasureLength());
		
		builder.put(Degree.SUBDOMINANT, Type.MAJOR_SEVENTH, oneMeasure);
		builder.put(Degree.DOMINANT, Type.MAJOR, oneMeasure);
		builder.put(Degree.MEDIANT, Type.MINOR_SEVENTH, oneMeasure);
		builder.put(Degree.SUBMEDIANT, Type.MINOR, oneMeasure);
		builder.put(Degree.SUPERTONIC, Type.MINOR_SEVENTH, oneMeasure);
		builder.put(Degree.DOMINANT, Type.MAJOR, oneMeasure);
		builder.put(Degree.TONIC, Type.MAJOR, oneMeasure);
		builder.put(Degree.TONIC, Type.MAJOR, oneMeasure);
		
		return builder.build();
	}
}