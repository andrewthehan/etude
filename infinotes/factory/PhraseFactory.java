
package infinotes.factory;

import infinotes.factory.NoteFactory;
import infinotes.factory.RhythmFactory;
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
	
	public Phrase makePhrase(ChordProgression.Element chord){
		Phrase.Builder builder = Phrase
			.builder()
			.setKeySignature(keySignature);
		
		Duration[] rhythm = rhythmFactory.makeRhythm(timeSignature.getBeatCount() * timeSignature.getBeatValue().getValue());
		Playable[] notes = noteFactory.makeNotes(chord, rhythm);
		
		for(int i = 0; i < rhythm.length && i < notes.length; i++){
			builder.add(notes[i], rhythm[i]);
		}
		
		return builder.build();
	}
}