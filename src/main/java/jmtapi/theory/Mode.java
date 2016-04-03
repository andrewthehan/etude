
package jmtapi.theory;

public enum Mode{
	IONIAN(new int[]{2, 2, 1, 2, 2, 2, 1}),
	DORIAN(new int[]{2, 1, 2, 2, 2, 1, 2}),
	PHRYGIAN(new int[]{1, 2, 2, 2, 1, 2, 2}),
	LYDIAN(new int[]{2, 2, 2, 1, 2, 2, 1}),
	MIXOLYDIAN(new int[]{2, 2, 1, 2, 2, 1, 2}),
	AEOLIAN(new int[]{2, 1, 2, 2, 1, 2, 2}),
	LOCRIAN(new int[]{1, 2, 2, 1, 2, 2, 2}),
	MAJOR(new int[]{2, 2, 1, 2, 2, 2, 1}),
	NATURAL_MINOR(new int[]{2, 1, 2, 2, 1, 2, 2}),
	HARMONIC_MINOR(new int[]{2, 1, 2, 2, 1, 3, 1});
	//MELODIC_MINOR(new int[]{2, 1, 2, 2, 2, 2, 1});

	private final int[] stepPattern;

	private Mode(int[] stepPattern){
		this.stepPattern = stepPattern;
	}

	public final int[] getStepPattern(){
		return stepPattern;
	}
}
