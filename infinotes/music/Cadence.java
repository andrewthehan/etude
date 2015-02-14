
package infinotes.music;

public final class Cadence{
	public static final ChordProgression AUTHENTIC;
	public static final ChordProgression PLAGAL;
	public static final ChordProgression HALF;
	public static final ChordProgression DECEPTIVE;
	
	static{
		AUTHENTIC = ChordProgression
			.builder()
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT)
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT)
			.build();
			
		PLAGAL = ChordProgression
			.builder()
			.put(Degree.SUBDOMINANT, Type.MAJOR, Inversion.ROOT)
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT)
			.build();
			
		HALF = ChordProgression
			.builder()
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT)
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT)
			.build();
			
		DECEPTIVE = ChordProgression
			.builder()
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT)
			.put(Degree.SUBMEDIANT, Type.MINOR, Inversion.ROOT)
			.build();
	}
}