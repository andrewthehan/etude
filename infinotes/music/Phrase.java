
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Phrase{
	private final KeySignature keySignature;
	private final Element[] elements;
	
	private Phrase(KeySignature keySignature, Element[] elements){
		this.keySignature = keySignature;
		this.elements = elements;
	}
	
	public Phrase repetition(){
		return new Phrase(keySignature, elements);
	}
	
	public Phrase sequence(int amount){
		// to store the sequenced phrase
		List<Element> sequenced = new ArrayList<Element>();
		
		// loop through all elements in this phrase
		Arrays.asList(elements).forEach(e -> {
			Playable playable = e.getPlayable();
			if(playable instanceof Chord){
				Chord chord = (Chord) playable;
				Chord.Builder builder = Chord.builder();
				Arrays.asList(chord.getNotes()).forEach(n -> {
					Degree newDegree = Degree.make(keySignature, n.getKey()).change(amount);
					Key newKey = Key.make(keySignature.getKey(), keySignature.getMode(), newDegree);
					builder.add(n.getNext(newKey));
				});
				sequenced.add(Element.make(builder.build(), e.getDuration()));
			}
			else if(playable instanceof Note){
				Note note = (Note) playable;
				if(note == Note.REST){
					sequenced.add(e);
				}
				else{
					Degree newDegree = Degree.make(keySignature, note.getKey()).change(amount);
					Key newKey = Key.make(keySignature.getKey(), keySignature.getMode(), newDegree);
					sequenced.add(Element.make(note.getNext(newKey), e.getDuration()));
				}
			}
		});
		return new Phrase(keySignature, sequenced.toArray(new Element[sequenced.size()]));
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		Arrays.asList(elements).forEach(i -> builder.append(" " + i));
		return builder.toString().substring(1);
	}
	
	public Element[] getElements(){
		return elements;
	}
	
	protected static class Element{
		private final Playable playable;
		private final Duration duration;
		
		private Element(Playable playable, Duration duration){
			this.playable = playable;
			this.duration = duration;
		}
		
		public static Element make(Playable playable, Duration duration){
			return new Element(playable, duration);
		}
		
		public Playable getPlayable(){
			return playable;
		}
		
		public Duration getDuration(){
			return duration;
		}
		
		@Override
		public String toString(){
			return playable.toString() + duration.toString();
		}
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private KeySignature keySignature;
		private List<Element> elements;
		
		public Builder(){
			elements = new ArrayList<Element>();
		}
		
		public Builder setKeySignature(KeySignature keySignature){
			this.keySignature = keySignature;
			return this;
		}
		
		public Builder add(Playable playable, Duration duration){
			elements.add(new Element(playable, duration));
			return this;
		}
		
		public Builder add(Element element){
			elements.add(element);
			return this;
		}
		
		public Builder add(Element... elements){
			this.elements.addAll(Arrays.asList(elements));
			return this;
		}
		
		public Builder add(List<Element> elements){
			this.elements.addAll(elements);
			return this;
		}
		
		public Builder add(Phrase phrase){
			elements.addAll(Arrays.asList(phrase.getElements()));
			return this;
		}
		
		public Phrase build(){
			if(keySignature == null){
				throw new RuntimeException("Insufficient information");
			}
			return new Phrase(keySignature, elements.toArray(new Element[elements.size()]));
		}
	}
}