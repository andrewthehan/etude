
package infinotes.music;

import infinotes.music.Degree;
import infinotes.music.Type;

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
		
		private Element(Degree degree, Type type, Inversion inversion){
			this.degree = degree;
			this.type = type;
			this.inversion = inversion;
		}
		
		public static Element make(Degree degree, Type type, Inversion inversion){
			return new Element(degree, type, inversion);
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
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private List<Element> chords;
		
		public Builder(){
			chords = new ArrayList<Element>();
		}
		
		public Builder put(Degree degree, Type type){
			chords.add(Element.make(degree, type, Inversion.ROOT));
			return this;
		}
		
		public Builder put(Degree degree, Type type, Inversion inversion){
			chords.add(Element.make(degree, type, inversion));
			return this;
		}
		
		public ChordProgression build(){
			return new ChordProgression(chords);
		}
	}
}