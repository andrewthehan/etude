
package infinotes.theory;

import java.util.HashMap;
import java.util.Map;

public enum Cadence{
	AUTHENTIC,
	PLAGAL,
	HALF,
	DECEPTIVE;
	
	private static final Map<Cadence, ChordProgression.Element[]> CHORDS = new HashMap<Cadence, ChordProgression.Element[]>();
	
	static{
		CHORDS.put(AUTHENTIC, new ChordProgression.Element[]{
			ChordProgression.Element.make(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, null),
			ChordProgression.Element.make(Degree.TONIC, Type.MAJOR, Inversion.ROOT, null)
		});
		
		CHORDS.put(PLAGAL, new ChordProgression.Element[]{
			ChordProgression.Element.make(Degree.SUBDOMINANT, Type.MAJOR, Inversion.ROOT, null),
			ChordProgression.Element.make(Degree.TONIC, Type.MAJOR, Inversion.ROOT, null)
		});
			
		CHORDS.put(HALF, new ChordProgression.Element[]{
			ChordProgression.Element.make(Degree.TONIC, Type.MAJOR, Inversion.ROOT, null),
			ChordProgression.Element.make(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, null)
		});
			
		CHORDS.put(DECEPTIVE, new ChordProgression.Element[]{
			ChordProgression.Element.make(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, null),
			ChordProgression.Element.make(Degree.SUBMEDIANT, Type.MINOR, Inversion.ROOT, null)
		});
	}
	
	public ChordProgression as(Duration... durations){
		ChordProgression.Element[] chords = CHORDS.get(this);
		
		if(chords.length != durations.length){
			throw new RuntimeException("Invalid number of durations");
		}
		
		ChordProgression.Builder builder = ChordProgression.builder();
		for(int i = 0; i < chords.length && i < durations.length; i++){
			builder.add(chords[i].getDegree(), chords[i].getType(), chords[i].getInversion(), durations[i]);
		}
		return builder.build();
	}
}