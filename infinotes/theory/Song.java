
package infinotes.theory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Song{
	private final KeySignature keySignature;
	private final TimeSignature timeSignature;
	private final int tempo;
	private final List<Voice> voices;
	private final Map<Voice, Phrase> phrases;
	
	private Song(KeySignature keySignature, TimeSignature timeSignature, int tempo, List<Voice> voices, Map<Voice, Phrase> phrases){
		this.keySignature = keySignature;
		this.timeSignature = timeSignature;
		this.tempo = tempo;
		this.voices = voices;
		this.phrases = phrases;
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
	
	public Map<Voice, Phrase> getPhrases(){
		return phrases;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Key Signature: " + keySignature);
		builder.append("\nTime Signature: " + timeSignature);
		builder.append("\nTempo: " + tempo);
		builder.append("\nVoice(s):");
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
		private Map<Voice, Phrase> phrases;
		
		public Builder(){
			voices = new ArrayList<Voice>();
			phrases = new HashMap<Voice, Phrase>();
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
		
		public Builder addVoice(Voice voice){
			voices.add(voice);
			return this;
		}
		
		public Builder addPhrase(Voice voice, Phrase phrase){
			if(phrases.get(voice) != null){
				throw new RuntimeException("Already added phrase for voice");
			}
			phrases.put(voice, phrase);
			return this;
		}
		
		public Song build(){
			if(keySignature == null || timeSignature == null || tempo == 0 || voices.isEmpty() || phrases.size() != voices.size()){
				throw new RuntimeException("Insufficient information");
			}
			
			return new Song(keySignature, timeSignature, tempo, voices, phrases);
		}
	}
}