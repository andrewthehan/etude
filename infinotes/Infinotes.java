
package infinotes;

import infinotes.factory.ChordProgressionFactory;
import infinotes.factory.PhraseFactory;
import infinotes.theory.ChordProgression;
import infinotes.theory.Duration;
import infinotes.theory.Instrument;
import infinotes.theory.Key;
import infinotes.theory.KeySignature;
import infinotes.theory.Mode;
import infinotes.theory.Phrase;
import infinotes.theory.Song;
import infinotes.theory.Style;
import infinotes.theory.TimeSignature;
import infinotes.theory.Voice;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Infinotes{
	public static void main(String[] args){
		System.out.print("Generating a song with 16 measures...");
		Song song = Infinotes.generate();
		System.out.println("Complete!");
		
		System.out.print("Playing...");
		new Player().play(songToPattern(song));
		System.out.println("Complete!");
	}
	
	private Infinotes(){
	}
	
	public static Song generate(){
		// TODO create algorithmically
		KeySignature keySignature = KeySignature.make(Key.C, Mode.MAJOR);
		TimeSignature timeSignature = TimeSignature.COMMON_TIME;
		int tempo = 120;
		
		List<Voice> voices = new ArrayList<Voice>();
		voices.add(Voice.make(Instrument.PIANO, Style.MELODY));
		voices.add(Voice.make(Instrument.PIANO, Style.HARMONY));
		
		int measuresLeft = 16;
			
		Map<Voice, Phrase.Builder> phrases = new HashMap<Voice, Phrase.Builder>();
		voices.forEach(v -> phrases.put(v, Phrase.builder().setKeySignature(keySignature)));
		
		ChordProgressionFactory chordProgressionFactory = ChordProgressionFactory.make(keySignature, timeSignature);
		
		while(measuresLeft > 0){
			ChordProgression chordProgression = chordProgressionFactory.makeChordProgression(8);
			measuresLeft -= 8;
		
			voices.forEach(v -> {
				PhraseFactory phraseFactory = PhraseFactory.make(keySignature, timeSignature, v);
				phrases.get(v).add(phraseFactory.makePhrase(chordProgression));
			});
		}
	
		Song.Builder builder = Song
			.builder()
			.setKeySignature(keySignature)
			.setTimeSignature(timeSignature)
			.setTempo(tempo);
		phrases.forEach((v, pb) -> {
			builder
				.addVoice(v)
				.addPhrase(v, pb.build());
		});
		return builder.build();
	}

	public static Pattern songToPattern(Song song){
		Pattern pattern = new Pattern();
		//pattern.add("K" + song.getKeySignature().toString().substring(0, song.getKeySignature().toString().length() - 2));
		pattern.add("T" + song.getTempo());
		Map<Voice, Phrase> phrases = song.getPhrases();
		song.getVoices().forEach(v -> {
			pattern.add("\n");
			pattern.add("V" + song.getVoices().indexOf(v));
			pattern.add("I[" + v.getInstrument() + "]");
			pattern.add(phrases.get(v).toString());
		});
		return pattern;
	}
}