
package infinotes.theory;

public enum Accidental{
	NONE(0), NATURAL(0), SHARP(1), DOUBLE_SHARP(2), FLAT(-1), DOUBLE_FLAT(-2);

	public static enum Policy{
		MAINTAIN_LETTER, PRIORITIZE_NATURAL, PRIORITIZE_SHARP, PRIORITIZE_FLAT
	}

	private final int offset;

	private Accidental(int offset){
		this.offset = offset;
	}

	public static final Accidental fromOffset(int offset){
		switch(offset){
			case -2: return DOUBLE_FLAT;
			case -1: return FLAT;
			case 0: return NATURAL;
			case 1: return SHARP;
			case 2: return DOUBLE_SHARP;
			default: throw new RuntimeException("Invalid accidental offset: " + offset);
		}
	}

	public final int getOffset(){
		return offset;
	}

	public static final Accidental fromString(String accidentalString){
		switch(accidentalString){
			case "": return NONE;
			case "n": return NATURAL;
			case "#": return SHARP;
			case "x": return DOUBLE_SHARP;
			case "b": return FLAT;
			case "bb": return DOUBLE_FLAT;
			default: throw new RuntimeException("Invalid accidental string: " + accidentalString);
		}
	}

	@Override
	public String toString(){
		switch(this){
			case NONE: return "";
			case NATURAL: return "n";
			case SHARP: return "#";
			case DOUBLE_SHARP: return "x";
			case FLAT: return "b";
			case DOUBLE_FLAT: return "bb";
			default: assert false; return null;
		}
	}
}
