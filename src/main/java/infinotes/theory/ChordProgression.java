
package infinotes.theory;

import java.util.ArrayList;
import java.util.List;

public class ChordProgression{
	private final List<Element> chords;
	
	private ChordProgression(List<Element> chords){
		this.chords = chords;
	}
	
	public List<Element> getChords(){
		return chords;
	}
	
	public static class Element{
		private Degree degree;
		private Type type;
		private Inversion inversion;
		private Duration duration;
		
		private Element(Degree degree, Type type, Inversion inversion, Duration duration){
			this.degree = degree;
			this.type = type;
			this.inversion = inversion;
			this.duration = duration;
		}
		
		public static Element make(Degree degree, Type type, Duration duration){
			return new Element(degree, type, Inversion.ROOT, duration);
		}
		
		public static Element make(Degree degree, Type type, Inversion inversion, Duration duration){
			return new Element(degree, type, inversion, duration);
		}
		
		public Degree getDegree(){
			return degree;
		}
		
		public Type getType(){
			return type;
		}
		
		public Inversion getInversion(){
			return inversion;
		}
		
		public Duration getDuration(){
			return duration;
		}
		
		@Override
		public String toString(){
			StringBuilder builder = new StringBuilder();
			builder.append(degree);
			builder.append(" " + type);
			builder.append(" " + inversion);
			builder.append(" " + duration);
			return builder.toString();
		}
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private List<Element> chords;
		
		public Builder(){
			chords = new ArrayList<Element>();
		}
		
		public Builder add(Element element){
			chords.add(element);
			return this;
		}
		
		public Builder add(Degree degree, Type type, Duration duration){
			chords.add(Element.make(degree, type, duration));
			return this;
		}
		
		public Builder add(Degree degree, Type type, Inversion inversion, Duration duration){
			chords.add(Element.make(degree, type, inversion, duration));
			return this;
		}
		
		public Builder add(ChordProgression chordProgression){
			chords.addAll(chordProgression.getChords());
			return this;
		}
		
		public ChordProgression build(){
			return new ChordProgression(chords);
		}
	}
}