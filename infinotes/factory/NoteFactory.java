
package infinotes.factory;

import infinotes.theory.Chord;
import infinotes.theory.ChordProgression;
import infinotes.theory.Degree;
import infinotes.theory.Interval;
import infinotes.theory.Inversion;
import infinotes.theory.Key;
import infinotes.theory.KeySignature;
import infinotes.theory.Note;
import infinotes.theory.Octave;
import infinotes.theory.Playable;
import infinotes.theory.Ratio;
import infinotes.theory.TimeSignature;
import infinotes.theory.Type;
import infinotes.theory.Voice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NoteFactory{
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final Voice voice;
	
	private NoteFactory(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.voice = voice;
	}
	
	public static NoteFactory make(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		return new NoteFactory(keySignature, timeSignature, voice);
	}
	
	public Playable[] makeNotes(ChordProgression.Element chord, int numberOfNotes){
		List<Playable> notes = new ArrayList<Playable>();
		Degree degree = chord.getDegree();
		Type type = chord.getType();
		Inversion inversion = chord.getInversion();
		switch(voice.getStyle()){
			case MELODY:
				for(int i = 0; i < numberOfNotes; i++){
					Note note = shouldRest() ? Note.REST : getRandomNote();
					notes.add(note);
				}
				break;
			case HARMONY:
				Octave octave = (degree.getValue() > 2) ? Octave.THREE : Octave.FOUR;
				
				for(int i = 0; i < numberOfNotes; i++){
					Note[] chordNotes = Chord.make(Key.make(keySignature.getKey(), keySignature.getMode(), degree), type, inversion, octave).getNotes();
					
					// if the number of notes desired is greater than the number of keys in the chord, loop back to beginning
					Note note = (notes.size() < chordNotes.length)
						? chordNotes[notes.size()]
						: chordNotes[notes.size() % chordNotes.length].change(Interval.make(Ratio.PERFECT, 8));
					notes.add(note);
				}
				break;
		}
		return notes.toArray(new Playable[notes.size()]);
	}
	
	private boolean shouldRest(){
		return R.nextInt(6) == 0;
	}
	
	private Note getRandomNote(){
		return Note.make(Key.make(keySignature.getKey(), keySignature.getMode(), Degree.make(R.nextInt(7) + 1)), Octave.FIVE);
	}
}