
package infinotes;

import infinotes.theory.*;

import java.util.regex.Pattern;
import java.util.Arrays;

public class Infinotes{
	private static final Pattern WHITESPACE = Pattern.compile("\\s{1,}");

	public static void main(String[] args){
		Arrays.asList(parse("C4 D4 E4 F4 G4 A4 B4 C5")).forEach(System.out::println);
	}

	public static Note[] parse(String notes){
		return Arrays.asList(WHITESPACE.split(notes))
			.stream()
			.map(Note::fromString)
			.toArray(Note[]::new);
	}
}
