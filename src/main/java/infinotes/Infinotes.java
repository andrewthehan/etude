
package infinotes;

import infinotes.theory.*;

import java.util.regex.Pattern;
import java.util.Arrays;

public class Infinotes{
	private static final Pattern WHITESPACE = Pattern.compile("\\s{1,}");

	public static void main(String[] args){
		Arrays.stream(parse("C4 D4 E4 F4 G4 A4 B4 C5")).forEach(System.out::println);

		KeySignature ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
		Note note = new Note(ks.getKey(), 4);
		Letter
			.iterator(ks.getKey().getLetter())
			.forEachRemaining(l -> System.out.println(note.getHigherNote(new Key(l)).apply(ks)));
	}

	public static Note[] parse(String notes){
		return Arrays.stream(WHITESPACE.split(notes))
			.map(Note::fromString)
			.toArray(Note[]::new);
	}
}
