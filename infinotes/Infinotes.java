
package infinotes;

import infinotes.factory.ChordFactory;
import infinotes.factory.PhraseFactory;
import infinotes.music.ChordProgression;
import infinotes.music.Degree;
import infinotes.music.Instrument;
import infinotes.music.Interval;
import infinotes.music.Inversion;
import infinotes.music.Key;
import infinotes.music.KeySignature;
import infinotes.music.Mode;
import infinotes.music.Phrase;
import infinotes.music.Ratio;
import infinotes.music.Style;
import infinotes.music.TimeSignature;
import infinotes.music.Type;
import infinotes.music.Voice;
import org.jfugue.Pattern;
import org.jfugue.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Infinotes{
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final int tempo;
	private final List<Voice> voices;
	private final List<ChordProgression> chordProgressions;
	private final ChordFactory chordFactory;
	
	public static void main(String[] args){
		ChordProgression progression1 = ChordProgression
			.builder()
			.put(Degree.TONIC, Type.MAJOR)
			.put(Degree.SUBDOMINANT, Type.MAJOR)
			.put(Degree.DOMINANT, Type.MAJOR)
			.put(Degree.TONIC, Type.MAJOR)
			.put(Degree.SUBMEDIANT, Type.MINOR)
			.put(Degree.SUBDOMINANT, Type.MAJOR)
			.put(Degree.SUBDOMINANT, Type.MINOR, Inversion.FIRST)
			.put(Degree.TONIC, Type.MAJOR)
			.build();
			
		ChordProgression progression2 = ChordProgression
			.builder()
			.put(Degree.TONIC, Type.MAJOR)
			.put(Degree.TONIC, Type.MAJOR, Inversion.FIRST)
			.put(Degree.SUBDOMINANT, Type.MINOR)
			.put(Degree.TONIC, Type.MAJOR)
			.build();
			
		ChordProgression progression3 = ChordProgression
			.builder()
			.put(Degree.SUBDOMINANT, Type.MAJOR_SEVENTH)
			.put(Degree.DOMINANT, Type.MAJOR)
			.put(Degree.MEDIANT, Type.MINOR_SEVENTH)
			.put(Degree.SUBMEDIANT, Type.MINOR)
			.put(Degree.SUPERTONIC, Type.MINOR_SEVENTH)
			.put(Degree.DOMINANT, Type.MAJOR)
			.put(Degree.TONIC, Type.MAJOR)
			.put(Degree.TONIC, Type.MAJOR)
			.build();
			
		Infinotes infinotes = Infinotes
			.builder()
			.setKeySignature(KeySignature.make(Key.C, Mode.MAJOR))
			.setTimeSignature(TimeSignature.COMMON_TIME)
			.setTempo(120)
			.addVoice(Instrument.PIANO, Style.MELODY)
			.addVoice(Instrument.PIANO, Style.HARMONY)
			/* .addVoice(Instrument.VIOLIN, Style.MELODY)
			.addVoice(Instrument.GUITAR, Style.MELODY)
			.addVoice(Instrument.STRING_ENSEMBLE_1, Style.HARMONY)
			.addVoice(Instrument.PIANO, Style.HARMONY) */
			//.register(progression1)
			.register(progression2)
			//.register(progression3)
			//.register(Cadence.AUTHENTIC)
			.build();
			
		infinotes.play();
	}
	
	private Infinotes(KeySignature keySignature, TimeSignature timeSignature, int tempo, List<Voice> voices, List<ChordProgression> chordProgressions){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.tempo = tempo;
		this.voices = voices;
		this.chordProgressions = chordProgressions;
		chordFactory = ChordFactory.make();
	}
	
	public void play(){
		/* scale
		Note note = Note.make(Key.C, Octave.FIVE);
		pattern.add(note.playAs(Duration.QUARTER));
		java.util.stream.IntStream.of(Mode.IONIAN.getPattern()).forEach(i -> {
			pattern.add(note.getNext(note.getKey().change(i)).playAs(Duration.QUARTER));
		}); */
		List<List<Phrase>> phrasesForVoice = new ArrayList<List<Phrase>>();
		
		ChordProgression chordProgression = chordProgressions.get(R.nextInt(chordProgressions.size()));
		
		voices.forEach(v -> {
			List<Phrase> phrases = new ArrayList<Phrase>();
			
			chordFactory.toQueue(chordProgression);
		
			PhraseFactory phraseFactory = PhraseFactory.make(keySignature, timeSignature, v);
			
			switch(v.getStyle()){
				case MELODY:
					chordFactory.forEachRemaining(c -> {
						// 1/8 chance of doing sequence
						if(!phrases.isEmpty() && R.nextInt(8) == 0){
							phrases.add(phrases.get(phrases.size() - 1).sequence(Interval.make(Ratio.MAJOR, 2)));
						}
						else{
							phrases.add(phraseFactory.makePhrase(c));
						}
					});
					break;
				case HARMONY:
					chordFactory.forEachRemaining(c -> {
						phrases.add(phraseFactory.makePhrase(c));
					});
					break;
			}
			
			phrasesForVoice.add(phrases);
		});
		
		Player player = new Player();
		Pattern pattern = new Pattern();
		pattern.add("K" + keySignature.toString().substring(0, 4));
		pattern.add("T" + tempo);
		voices.forEach(v -> {
			pattern.add("\n");
			pattern.add("V" + voices.indexOf(v));
			pattern.add("I[" + v.getInstrument() + "]");
			phrasesForVoice.get(voices.indexOf(v)).forEach(p -> pattern.add(p.toString()));
		});
		System.out.println(pattern);
		player.play(pattern);
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	protected static class Builder{
		private KeySignature keySignature;
		private TimeSignature timeSignature;
		private int tempo;
		private List<Voice> voices;
		private List<ChordProgression> chordProgressions;
		
		public Builder(){
			voices = new ArrayList<Voice>();
			chordProgressions = new ArrayList<ChordProgression>();
		}
		
		public Builder setKeySignature(KeySignature keySignature){
			switch(keySignature.getMode()){
				case IONIAN:
				case MAJOR:
					break;
				case AEOLIAN:
				case NATURAL_MINOR:
					break;
				default:
					throw new RuntimeException("Mode not supported");
			}
			this.keySignature = keySignature;
			return this;
		}
		
		public Builder setTimeSignature(TimeSignature timeSignature){
			this.timeSignature = timeSignature;
			return this;
		}
		
		public Builder setTempo(int tempo){
			this.tempo = tempo;
			return this;
		}
		
		public Builder addVoice(Instrument instrument, Style style){
			voices.add(Voice.make(instrument, style));
			return this;
		}
		
		public Builder register(ChordProgression chordProgression){
			chordProgressions.add(chordProgression);
			return this;
		}
		
		public Infinotes build(){
			if(keySignature == null || timeSignature == null || tempo == 0 || voices.isEmpty()){
				throw new RuntimeException("Insufficient information");
			}
			return new Infinotes(keySignature, timeSignature, tempo, voices, chordProgressions);
		}
	}
}