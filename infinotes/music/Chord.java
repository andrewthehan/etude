
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public final class Chord implements Playable{
	private Note[] notes;
	
	private Chord(Note[] notes){
		this.notes = notes;
	}
	
	public static Chord make(Key key, Type type){
		return new Chord(asNotes(key, type, Inversion.ROOT, Octave.THREE));
	}
	
	public static Chord make(Key key, Type type, Inversion inversion){
		return new Chord(asNotes(key, type, inversion, Octave.THREE));
	}
	
	public static Chord make(Key key, Type type, Octave octave){
		return new Chord(asNotes(key, type, Inversion.ROOT, octave));
	}
	
	public static Chord make(Key key, Type type, Inversion inversion, Octave octave){
		return new Chord(asNotes(key, type, inversion, octave));
	}
	
	public static Note[] asNotes(Key key, Type type, Inversion inversion, Octave octave){
		// list of keys in root inversion
		Queue<Key> keys = new LinkedList<Key>();
		IntStream.of(type.getPattern()).forEach(i -> {
			keys.add(key.change(i));
		});
		
		// apply inversion regarding order of notes (not octaves)
		for(int i = 0; i < inversion.getValue(); i++){
			keys.add(keys.poll());
		}
		
		// apply octaves to the notes
		List<Note> notes = new ArrayList<Note>();
		Note rootNote = Note.make(keys.poll(), octave);
		notes.add(rootNote);
		while(!keys.isEmpty()){
			notes.add(rootNote.getNext(keys.poll()));
		}
		
		return notes.toArray(new Note[notes.size()]);
	}
	
	public Note[] getNotes(){
		return notes;
	}
	
	@Override
	public String playAs(Duration duration){
		StringBuilder builder = new StringBuilder();
		Arrays.asList(notes).forEach(n -> builder.append("+" + n));
		return builder.toString().substring(1) + duration;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		Arrays.asList(notes).forEach(i -> builder.append("+" + i));
		return builder.toString().substring(1);
	}

	public static Builder builder(){
		return new Builder();
	}
	
	protected static class Builder{
		private List<Note> notes;
		
		public Builder(){
			notes = new ArrayList<Note>();
		}
		
		public Builder add(Note note){
			notes.add(note);
			return this;
		}
		
		public Builder add(Note... notes){
			this.notes.addAll(Arrays.asList(notes));
			return this;
		}
		
		public Chord build(){
			return new Chord(notes.toArray(new Note[notes.size()]));
		}
	}
}