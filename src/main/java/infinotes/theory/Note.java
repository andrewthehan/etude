
package infinotes.theory;

/*
* Pitch with the concept of value
*/
public final class Note{
	private final Pitch pitch;
	private final Value value;

	public Note(Pitch pitch, Value value){
		this.pitch = pitch;
		this.value = value;
	}

	public static final Note fromString(String noteString){
		if(noteString == null){
			throw new RuntimeException("Invalid note string: " + noteString);
		}
		else if(noteString.trim().isEmpty()){
			throw new RuntimeException("Invalid note string: " + noteString + " (blank)");
		}

		String[] split = noteString.split("\\[");
		if(split.length < 2 || split[0].trim().isEmpty() || split[1].trim().isEmpty()){
			throw new RuntimeException("Invalid note string: " + noteString + " (missing information)");
		}
		else if(split.length > 2){
			throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
		}

		Pitch pitch = Pitch.fromString(split[0]);

		if(!split[1].contains("]")){
			throw new RuntimeException("Invalid note string: " + noteString + " (missing closing bracket)");
		}
		else if(!split[1].endsWith("]")){
			throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
		}

		Value value = Value.fromString(split[1].substring(0, split[1].length() - 1));
		return new Note(pitch, value);
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(pitch);
		builder.append("[");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	public final Pitch getPitch(){
		return pitch;
	}

	public final Value getValue(){
		return value;
	}
}
