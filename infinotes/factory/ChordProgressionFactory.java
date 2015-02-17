
package infinotes.factory;

import infinotes.theory.Cadence;
import infinotes.theory.Chord;
import infinotes.theory.ChordProgression;
import infinotes.theory.Degree;
import infinotes.theory.Duration;
import infinotes.theory.Inversion;
import infinotes.theory.KeySignature;
import infinotes.theory.TimeSignature;
import infinotes.theory.Type;
import infinotes.util.ChordChain;

import java.util.Random;

public class ChordProgressionFactory{	
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final ChordChain chain;
	
	private ChordProgressionFactory(KeySignature keySignature, TimeSignature timeSignature, ChordChain chain){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.chain = chain;
	}
	
	public static ChordProgressionFactory make(KeySignature keySignature, TimeSignature timeSignature){
		return new ChordProgressionFactory(keySignature, timeSignature, ChordChain.make(.45, .35, .20));
	}
	
	public static ChordProgressionFactory make(KeySignature keySignature, TimeSignature timeSignature, ChordChain chain){
		return new ChordProgressionFactory(keySignature, timeSignature, chain);
	}
	
	public ChordProgression makeChordProgression(int numberOfMeasures){
		ChordProgression.Builder builder = ChordProgression.builder();
		
		ChordProgression.Element current = null;
		
		// 2 for the cadence
		double total = (numberOfMeasures - 2) * timeSignature.getMeasureLength();
		
		// change later
		Duration oneMeasure = Duration.make(timeSignature.getMeasureLength());
		
		for(double soFar = 0; soFar < total; soFar += current.getDuration().getValue()){
			ChordChain.Element toAdd = (current == null)
				? chain.get(ChordChain.Element.make(Degree.TONIC, Type.MAJOR))
				: chain.next(chain.convert(current));
			Inversion inversion = Inversion.make(R.nextInt(4));
			current = ChordProgression.Element.make(toAdd.getDegree(), toAdd.getType(), inversion, oneMeasure);
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
}