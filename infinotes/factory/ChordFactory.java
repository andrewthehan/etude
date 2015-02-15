
package infinotes.factory;

import infinotes.music.ChordProgression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChordFactory implements Iterator<ChordProgression.Element>{
	private Queue<ChordProgression.Element> chordsInQueue = new LinkedList<ChordProgression.Element>();
	
	private ChordFactory(){
	}
	
	public static ChordFactory make(){
		return new ChordFactory();
	}
	
	public boolean hasNext(){
		return !chordsInQueue.isEmpty();
	}
	
	public ChordProgression.Element next(){
		return chordsInQueue.poll();
	}
	
	public void toQueue(ChordProgression chordProgression){
		for(ChordProgression.Element chord : chordProgression.getChords()){
			chordsInQueue.add(chord);
		}
	}
}