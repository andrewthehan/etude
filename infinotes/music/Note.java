
package infinotes.music;

public class Note implements Playable{
	public static final Note REST = Note.make(null, null);
	private final Key key;
	private final Octave octave;
	
	private Note(Key key, Octave octave){
		this.key = key;
		this.octave = octave;
	}
	
	public static Note make(Key key, Octave octave){
		return new Note(key, octave);
	}
	
	public Note change(int amount){
		if(this == REST){
			return REST;
		}
		Key nextKey = key;
		for(int i = 0; i < amount; i++){
			nextKey = nextKey.makeSharp();
		}
		return getNext(nextKey);
	}
	
	public Note change(Interval interval){
		return change(interval.getHalfStepAmount());
	}
	
	public Note getNext(Key key){
		// this.key vs key
		Note nextNote = Note.make(key, octave);
		if(!comesBefore(nextNote)){
			nextNote = Note.make(key, octave.change(1));
		}
		return nextNote;
	}
	
	public Note getPrevious(Key key){
		// this.key vs key
		Note nextNote = Note.make(key, octave);
		if(!comesAfter(nextNote)){
			nextNote = Note.make(key, octave.change(-1));
		}
		return nextNote;
	}
	
	public boolean comesBefore(Note note){
		return getNoteValue() < note.getNoteValue();
	}
	
	public boolean comesAfter(Note note){
		return getNoteValue() > note.getNoteValue();
	}

	public int getNoteValue(){
		String keyString = key.toString();
		char keyChar = keyString.charAt(0);
		int distance = keyChar - 'C';
		int keyValue = Mode.IONIAN.getPattern()[Math.floorMod(distance, Key.UNIQUE_LETTER_COUNT)];
		int octaveValue = octave.getValue();
		if(keyString.contains("#")){
			keyValue++;
			if(keyChar == 'B'){
				octaveValue++;
			}
		}
		else if(keyString.contains("b")){
			keyValue--;
			if(keyChar == 'C'){
				octaveValue--;
			}
		}
		keyValue = Math.floorMod(keyValue, Key.UNIQUE_KEY_COUNT);
		return octaveValue * Key.UNIQUE_KEY_COUNT + keyValue;
	}
	
	@Override
	public String playAs(Duration duration){
		return toString() + duration;
	}
	
	@Override
	public String toString(){
		if(this == REST){
			return "R";
		}
		return key.toString() + octave.toString();
	}
	
	public Key getKey(){
		return key;
	}
	
	public Octave getOctave(){
		return octave;
	}
}