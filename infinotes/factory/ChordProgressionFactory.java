
package infinotes.factory;

import infinotes.theory.Cadence;
import infinotes.theory.Chord;
import infinotes.theory.ChordProgression;
import infinotes.theory.Degree;
import infinotes.theory.Duration;
import infinotes.theory.Inversion;
import infinotes.theory.Key;
import infinotes.theory.KeySignature;
import infinotes.theory.Mode;
import infinotes.theory.Note;
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
		
		ChordProgression.Element current = null;
		
		// 2 for the cadence
		double total = (numberOfMeasures - 2) * timeSignature.getMeasureLength();
		
		// change later
		Duration oneMeasure = Duration.make(timeSignature.getMeasureLength());
		
		for(double soFar = 0; soFar < total; soFar += current.getDuration().getValue()){
			ChordProgression.Element toAdd;
			do{
				Degree degree = Degree.make(R.nextInt(7) + 1);
				Type type;
				switch(R.nextInt(7)){
					case 0: 
						type = Type.MAJOR;
						break;
					case 1:
						type = Type.MINOR;
						break;
					case 2:
						type = Type.DIMINISHED;
						break;
					case 3:
						type = Type.DOMINANT_SEVENTH;
						break;
					case 4:
						type = Type.DIMINISHED_SEVENTH;
						break;
					case 5:
						type = Type.SUSPENDED_SECOND;
						break;
					case 6:
						type = Type.SUSPENDED_FOURTH;
						break;
					default:
						type = null;
				}
				toAdd = ChordProgression.Element.make(degree, type, oneMeasure);
			}while(current != null && !sharesNotes(current, toAdd));
			current = toAdd;
			builder.add(current);
			// TODO add resolving chord if currently added was a secondary dominant and appropriately increment soFar
		}
		
		Cadence cadence;
		switch(R.nextInt(3)){
			case 0: 
				cadence = Cadence.AUTHENTIC;
				break;
			case 1:
				cadence = Cadence.PLAGAL;
				break;
			case 2:
				cadence = Cadence.HALF;
				break;
			default:
				cadence = null;
		}
		builder.add(cadence.as(oneMeasure, oneMeasure));
		
		return builder.build();
	}
	
	private static boolean sharesNotes(ChordProgression.Element e1, ChordProgression.Element e2){
		// what key signature is chosen does not really matter, just that it's the same for both chords
		KeySignature keySignature = KeySignature.make(Key.C, Mode.MAJOR);
		Chord c1 = Chord.make(keySignature, e1);
		Chord c2 = Chord.make(keySignature, e2);
		for(Note n1 : c1.getNotes()){
			for(Note n2 : c2.getNotes()){
				if(Key.isEnharmonic(n1.getKey(), n2.getKey())){
					return true;
				}
			}
		}
		return false;
	}
}