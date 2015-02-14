
package infinotes.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Phrase{
	private final Element[] elements;
	
	private Phrase(Element[] elements){
		this.elements = elements;
	}
	
	public Phrase repetition(){
		return new Phrase(elements);
	}
	
	public Phrase sequence(Interval interval){
		// to store the sequenced phrase
		List<Element> copy = new ArrayList<Element>();
		
		// loop through all elements in this phrase
		Arrays.asList(elements).forEach(e -> {
			Playable playable = e.getPlayable();
			if(playable instanceof Chord){
				Chord chord = (Chord) playable;
				Chord.Builder builder = Chord.builder();
				Arrays.asList(chord.getNotes()).forEach(n -> builder.add(n.changeBy(interval)));
				copy.add(Element.make(builder.build(), e.getDuration()));
			}
			else if(playable instanceof Note){
				Note note = (Note) playable;
				copy.add(Element.make(note.changeBy(interval), e.getDuration()));
			}
		});
		return new Phrase(copy.toArray(new Element[copy.size()]));
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
		private List<Element> elements;
		
		public Builder(){
			elements = new ArrayList<Element>();
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
			return new Phrase(elements.toArray(new Element[elements.size()]));
		}
	}
}