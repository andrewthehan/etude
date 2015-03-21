
package infinotes.util;

import infinotes.theory.Chord;
import infinotes.theory.ChordProgression;
import infinotes.theory.Degree;
import infinotes.theory.Inversion;
import infinotes.theory.Key;
import infinotes.theory.KeySignature;
import infinotes.theory.Mode;
import infinotes.theory.Note;
import infinotes.theory.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChordChain{
	private final Map<String, Element> chords;
	
	private ChordChain(Map<String, Element> chords){
		this.chords = chords;
	}
	
	public static ChordChain make(double... weights){
		return new ChordChain(ChordChain.makeChain(weights));
	}
	
	private static Map<String, Element> makeChain(double... weights){
		double sum = 0;
		for(double w : weights){
			sum += w;
		}
		if(sum != 1.0){
			throw new RuntimeException("Invalid Chord Chain values: " + sum + " != 1.0");
		}
		Map<String, Element> chords = new HashMap<String, Element>();
		
		// generate all possible chords
		for(Degree degree : Degree.class.getEnumConstants()){
			for(Type type : Type.class.getEnumConstants()){
				chords.put(degree.toString() + type.toString(), Element.make(degree, type));
			}
		}
		// for each chord, create set of adjacency list
		for(Map.Entry<String, Element> entry1 : chords.entrySet()){
			Element e1 = entry1.getValue();
			// one adjacency list for each number of shared notes (1 shared note list, 2 shared note list, etc.)
			Map<Integer, List<Element>> adjacencyList = new HashMap<Integer, List<Element>>();
			for(Map.Entry<String, Element> entry2 : chords.entrySet()){
				Element e2 = entry2.getValue();
				// not the same chord
				if(e1 != e2){
					Key[] shared = getSharedKeys(e1, e2);
					List<Element> list = adjacencyList.get(shared.length);
					// list doesn't exist already
					if(list == null){
						list = new ArrayList<Element>();
						adjacencyList.put(shared.length, list);
					}
					list.add(e2);
				}
			}
			if(adjacencyList.size() != weights.length){
				new RuntimeException("Invalid number of weights to create chord chain.");
			}
			adjacencyList.forEach((i, list) -> {
				double weight = weights[i] / list.size();
				list.forEach(e -> e1.add(e, weight));
			});
		}
		return chords;
	}
	
	public Element next(Element element){
		return get(element).nextElement();
	}
	
	public Element get(Element element){
		return chords.get(element.getDegree().toString() + element.getType().toString());
	}
	
	public static Key[] getSharedKeys(Element e1, Element e2){
		List<Key> sharedKeys = new ArrayList<Key>();
		
		// what key signature is chosen does not really matter, just that it's the same for both chords
		KeySignature keySignature = KeySignature.make(Key.C, Mode.MAJOR);
		Chord c1 = Chord.make(keySignature, convert(e1));
		Chord c2 = Chord.make(keySignature, convert(e2));
		for(Note n1 : c1.getNotes()){
			for(Note n2 : c2.getNotes()){
				if(Key.isEnharmonic(n1.getKey(), n2.getKey())){
					sharedKeys.add(n1.getKey());
				}
			}
		}
		return sharedKeys.toArray(new Key[sharedKeys.size()]);
	}
	
	public static Element convert(ChordProgression.Element element){
		return Element.make(element.getDegree(), element.getType());
	}
	
	private static ChordProgression.Element convert(Element element){
		return ChordProgression.Element.make(element.getDegree(), element.getType(), null);
	}
	
	public static class Element{
		private static final Random R = new Random();
		private final Degree degree;
		private final Type type;
		private Map<Element, Double> elements;
		
		private Element(Degree degree, Type type){
			this.degree = degree;
			this.type = type;
			elements = new HashMap<Element, Double>();
		}
		
		public static Element make(Degree degree, Type type){
			return new Element(degree, type);
		}
		
		public void add(Element element, double chance){
			elements.put(element, chance);
		}
		
		public boolean canGoTo(Element element){
			return elements.containsKey(element);
		}
		
		public Element nextElement(){			
			double random = R.nextDouble();
			double soFar = 0.0;
			for(Map.Entry<Element, Double> element : elements.entrySet()){
				soFar += element.getValue();
				if(soFar >= random){
					return element.getKey();
				}
			}
			// should never happen
			throw new RuntimeException("Bug: sum of events probabilities " + soFar + " != 1.0");
		}
		
		public Degree getDegree(){
			return degree;
		}
		
		public Type getType(){
			return type;
		}
		
		@Override
		public String toString(){
			StringBuilder builder = new StringBuilder();
			builder.append(degree);
			builder.append(" " + type);
			return builder.toString();
		}
	}
}