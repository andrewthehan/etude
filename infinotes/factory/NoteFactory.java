
package infinotes.factory;

import infinotes.music.Chord;
import infinotes.music.ChordProgression;
import infinotes.music.Degree;
import infinotes.music.Duration;
import infinotes.music.Inversion;
import infinotes.music.Key;
import infinotes.music.KeySignature;
import infinotes.music.Note;
import infinotes.music.Octave;
import infinotes.music.Playable;
import infinotes.music.TimeSignature;
import infinotes.music.Type;
import infinotes.music.Voice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NoteFactory{
	private static final Random R = new Random();
	private KeySignature keySignature;
	private TimeSignature timeSignature;
	private Voice voice;
	
	private NoteFactory(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.voice = voice;
	}
	
	public static NoteFactory make(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		return new NoteFactory(keySignature, timeSignature, voice);
	}
	
	public Playable[] makeNotes(ChordProgression.Element chord, Duration[] rhythm){
		List<Playable> notes = new ArrayList<Playable>();
		Degree degree = chord.getDegree();
		Type type = chord.getType();
		Inversion inversion = chord.getInversion();
		switch(voice.getStyle()){
			case MELODY:
				Arrays.asList(rhythm).forEach(d -> {
					Note note;
					switch(R.nextInt(6)){
						case 0: 
							note = Note.REST;
							break;
						default:
							note = Note.make(Key.make(keySignature.getKey(), keySignature.getMode(), Degree.make(R.nextInt(7) + 1)), Octave.FIVE);
					}
					notes.add(note);
				});
				break;
			case HARMONY:
				Octave octave = (degree.getValue() > 2) ? Octave.THREE : Octave.FOUR;
				
				List<Integer> temp = new ArrayList<Integer>();
				
				Arrays.asList(rhythm).forEach(d -> {
					Note[] chordNotes = Chord.make(Key.make(keySignature.getKey(), keySignature.getMode(), degree), type, inversion, octave).getNotes();
					notes.add(chordNotes[temp.size()]);
					temp.add(0);
				});
				break;
		}
		return notes.toArray(new Playable[notes.size()]);
	}
}