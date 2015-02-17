
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
	
	public static ChordChain make(double oneWeight, double twoWeight, double threeWeight){
		double sum = oneWeight + twoWeight + threeWeight;
		if(sum != 1.0){
			throw new RuntimeException("Invalid Chord Chain values: "
				+ oneWeight + " + " + twoWeight + " + " + threeWeight
				+ " = " + sum + " != 1.0");
		}
		return new ChordChain(ChordChain.makeChain(oneWeight, twoWeight, threeWeight));
	}
	
	private static Map<String, Element> makeChain(double oneWeight, double twoWeight, double threeWeight){
		Map<String, Element> chords = new HashMap<String, Element>();
		
		// generate all possible chords
		for(Degree degree : Degree.class.getEnumConstants()){
			for(Type type : Type.class.getEnumConstants()){
				chords.put(degree.toString() + type.toString(), Element.make(degree, type));
			}
		}
		
		// for all chords, create chain
		for(Map.Entry<String, Element> entry1 : chords.entrySet()){
			Element e1 = entry1.getValue();
			// number of shared notes
			List<Element> one = new ArrayList<Element>();
			List<Element> two = new ArrayList<Element>();
			List<Element> three = new ArrayList<Element>();
			for(Map.Entry<String, Element> entry2 : chords.entrySet()){
				Element e2 = entry2.getValue();
				// not the same chord
				if(e1 != e2){
					Key[] shared = getSharedKeys(e1, e2);
					switch(shared.length){
						case 0:
							break;
						case 1:
							one.add(e2);
							break;
						case 2:
							two.add(e2);
							break;
						case 3:
							three.add(e2);
							break;
						default:
							assert false;
					}
				}
			}
			// add elements w/ probabilities
			double oneChance = oneWeight / one.size();
			double twoChance = twoWeight / two.size();
			double threeChance = threeWeight / three.size();
			one.forEach(e -> e1.add(e, oneChance));
			two.forEach(e -> e1.add(e, twoChance));
			three.forEach(e -> e1.add(e, threeChance));
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
			throw new RuntimeException("Bug");
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