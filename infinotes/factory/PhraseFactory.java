
package infinotes.factory;

import infinotes.music.ChordProgression;
import infinotes.music.Duration;
import infinotes.music.KeySignature;
import infinotes.music.Phrase;
import infinotes.music.Playable;
import infinotes.music.TimeSignature;
import infinotes.music.Voice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PhraseFactory{
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final Voice voice;
	private NoteFactory noteFactory;
	private RhythmFactory rhythmFactory;
	
	private PhraseFactory(KeySignature keySignature, TimeSignature timeSignature, Voice voice, NoteFactory noteFactory, RhythmFactory rhythmFactory){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.voice = voice;
		this.noteFactory = noteFactory;
		this.rhythmFactory = rhythmFactory;
	}
	
	public static PhraseFactory make(KeySignature keySignature, TimeSignature timeSignature, Voice voice){
		return new PhraseFactory(keySignature, timeSignature, voice, 
			NoteFactory.make(keySignature, timeSignature, voice), RhythmFactory.make(keySignature, timeSignature, voice));
	}
	
	public Phrase makePhrase(ChordProgression chordProgression){
		Phrase.Builder builder = Phrase
			.builder()
			.setKeySignature(keySignature);
			
		Phrase current = null;
		
		for(ChordProgression.Element chord : chordProgression.getChords()){			
			// 1/8 chance of doing sequence
			if(current != null && R.nextInt(8) == 0){
				current = current.sequence(1);
			}
			else{
				Phrase.Builder builderCurrent = Phrase
					.builder()
					.setKeySignature(keySignature);
					
				Duration[] rhythm = rhythmFactory.makeRhythm(chord.getDuration());
				Playable[] notes = noteFactory.makeNotes(chord, rhythm.length);
				
				for(int i = 0; i < rhythm.length && i < notes.length; i++){
					builderCurrent.add(notes[i], rhythm[i]);
				}
				current = builderCurrent.build();
			}
			builder.add(current);
		}
			
		return builder.build();
	}
}