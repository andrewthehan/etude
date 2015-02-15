
package infinotes.music;

public final class Cadence{
	public static final ChordProgression AUTHENTIC;
	public static final ChordProgression PLAGAL;
	public static final ChordProgression HALF;
	public static final ChordProgression DECEPTIVE;
	
	static{
		AUTHENTIC = ChordProgression
			.builder()
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.build();
			
		PLAGAL = ChordProgression
			.builder()
			.put(Degree.SUBDOMINANT, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.build();
			
		HALF = ChordProgression
			.builder()
			.put(Degree.TONIC, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.build();
			
		DECEPTIVE = ChordProgression
			.builder()
			.put(Degree.DOMINANT, Type.MAJOR, Inversion.ROOT, Duration.WHOLE)
			.put(Degree.SUBMEDIANT, Type.MINOR, Inversion.ROOT, Duration.WHOLE)
			.build();
	}
}