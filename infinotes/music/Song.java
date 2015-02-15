
package infinotes.music;

import infinotes.factory.ChordProgressionFactory;
import infinotes.factory.PhraseFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Song{
	private static final Random R = new Random();
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final int tempo;
	private final List<Voice> voices;
	
	private Song(KeySignature keySignature, TimeSignature timeSignature, int tempo, List<Voice> voices){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.tempo = tempo;
		this.voices = voices;
	}
	
	public Map<Voice, List<Phrase>> makeSong(int numberOfMeasures){
		/*scale
		Pattern la = new Pattern();
		infinotes.music.Note note = infinotes.music.Note.make(Key.C, infinotes.music.Octave.FIVE);
		java.util.stream.IntStream.of(Mode.IONIAN.getPattern()).forEach(i -> {
			la.add(note.getNext(note.getKey().change(i)).playAs(infinotes.music.Duration.QUARTER));
		});
		new Player().play(la);
		*/
		
		int measuresLeft = numberOfMeasures;
		
		Map<Voice, List<Phrase>> phrasesForVoice = new HashMap<Voice, List<Phrase>>();
		voices.forEach(v -> phrasesForVoice.put(v, new ArrayList<Phrase>()));
		
		ChordProgressionFactory chordProgressionFactory = ChordProgressionFactory.make(keySignature, timeSignature);
		
		while(measuresLeft > 0){
			ChordProgression chordProgression = chordProgressionFactory.makeChordProgression(8);
			measuresLeft -= 8;
		
			voices.forEach(v -> {
				PhraseFactory phraseFactory = PhraseFactory.make(keySignature, timeSignature, v);
				phrasesForVoice.get(v).add(phraseFactory.makePhrase(chordProgression));
			});
		}
		
		return phrasesForVoice;
	}
	
	public KeySignature getKeySignature(){
		return keySignature;
	}
	
	public TimeSignature getTimeSignature(){
		return timeSignature;
	}
	
	public int getTempo(){
		return tempo;
	}
	
	public List<Voice> getVoices(){
		return voices;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Key Signature: " + keySignature + "\n");
		builder.append("Time Signature: " + timeSignature + "\n");
		builder.append("Tempo: " + tempo + "\n");
		builder.append("Voice(s):");
		voices.forEach(v -> builder.append("\n    " + v));
		return builder.toString(); 
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private KeySignature keySignature;
		private TimeSignature timeSignature;
		private int tempo;
		private List<Voice> voices;
		
		public Builder(){
			voices = new ArrayList<Voice>();
		}
		
		public Builder setKeySignature(KeySignature keySignature){
			switch(keySignature.getMode()){
				case IONIAN: case MAJOR:
					break;
				case AEOLIAN: case NATURAL_MINOR:
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
		
		public Song build(){
			if(keySignature == null || timeSignature == null || tempo == 0 || voices.isEmpty()){
				throw new RuntimeException("Insufficient information");
			}
			return new Song(keySignature, timeSignature, tempo, voices);
		}
	}
}