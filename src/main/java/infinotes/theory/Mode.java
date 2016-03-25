
package infinotes.theory;

public enum Mode{
	IONIAN(2, 2, 1, 2, 2, 2, 1),
	DORIAN(2, 1, 2, 2, 2, 1, 2),
	PHRYGIAN(1, 2, 2, 2, 1, 2, 2),
	LYDIAN(2, 2, 2, 1, 2, 2, 1),
	MIXOLYDIAN(2, 2, 1, 2, 2, 1, 2),
	AEOLIAN(2, 1, 2, 2, 1, 2, 2),
	LOCRIAN(1, 2, 2, 1, 2, 2, 2),
	MAJOR(2, 2, 1, 2, 2, 2, 1),
	NATURAL_MINOR(2, 1, 2, 2, 1, 2, 2),
	HARMONIC_MINOR(2, 1, 2, 2, 1, 3, 1);
	//MELODIC_MINOR(2, 1, 2, 2, 2, 2, 1);

	private final int[] stepPattern;

	private Mode(int... stepPattern){
		this.stepPattern = stepPattern;
	}

	public final int[] getStepPattern(){
		return stepPattern;
	}
}
