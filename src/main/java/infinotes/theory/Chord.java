
package infinotes.theory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Chord{
	public enum Quality{
		MAJOR(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MAJOR, 3),
			new Interval(Interval.Quality.PERFECT, 5)
		}, "maj"),
		MINOR(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.PERFECT, 5)
		}, "min"),
		DIMINISHED(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.DIMINISHED, 5)
		}, "dim"),
		AUGMENTED(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MAJOR, 3),
			new Interval(Interval.Quality.AUGMENTED, 5)
		}, "aug"),
		MAJOR_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MAJOR, 3),
			new Interval(Interval.Quality.PERFECT, 5),
			new Interval(Interval.Quality.MAJOR, 7)
		}, "maj7"),
		MINOR_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.PERFECT, 5),
			new Interval(Interval.Quality.MINOR, 7)
		}, "min7"),
		DOMINANT_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MAJOR, 3),
			new Interval(Interval.Quality.PERFECT, 5),
			new Interval(Interval.Quality.MINOR, 7)
		}, "7"),
		DIMINISHED_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.DIMINISHED, 5),
			new Interval(Interval.Quality.DIMINISHED, 7)
		}, "dim7"),
		HALF_DIMINISHED_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.DIMINISHED, 5),
			new Interval(Interval.Quality.MINOR, 7)
		}, "mMaj7"),
		MINOR_MAJOR_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MINOR, 3),
			new Interval(Interval.Quality.PERFECT, 5),
			new Interval(Interval.Quality.MAJOR, 7)
		}, "min7"),
		AUGMENTED_MAJOR_SEVENTH(new Interval[]{
			new Interval(Interval.Quality.PERFECT, 1),
			new Interval(Interval.Quality.MAJOR, 3),
			new Interval(Interval.Quality.AUGMENTED, 5),
			new Interval(Interval.Quality.MAJOR, 7)
		}, "min7");

		private final Interval[] intervalPattern;
		private final String symbol;

		private Quality(Interval[] intervalPattern, String symbol){
			this.intervalPattern = intervalPattern;
			this.symbol = symbol;
		}

		public final Interval[] getIntervalPattern(){
			return intervalPattern;
		}

		@Override
		public String toString(){
			return symbol;
		}
	}

	private final Note[] notes;

	public Chord(Note[] notes){
		this.notes = notes;
	}

	public Chord(KeySignature ks, Degree degree, int octave, Quality quality){
		this(ks, degree, octave, quality, Inversion.ROOT);
	}

	public Chord(KeySignature ks, Degree degree, int octave, Quality quality, Inversion inversion){
		this(new Note(ks.keyOf(degree), octave), quality, inversion);
	}

	public Chord(Note note, Quality quality){
		this(note, quality, Inversion.ROOT);
	}

	public Chord(Note note, Quality quality, Inversion inversion){
		List<Note> notes = Arrays
			.stream(quality.getIntervalPattern())
			.map(note::step)
			.collect(Collectors.toList());
		if(Inversion.THIRD == inversion && quality.getIntervalPattern().length < Inversion.THIRD.getValue() + 1){
			throw new RuntimeException("Invalid inversion: " + inversion + " (unable to invert chord with less than 4 notes to its third inversion)");
		}
		Collections.rotate(notes, -inversion.getValue());
		this.notes = notes.toArray(new Note[notes.size()]);
	}

	public final Note[] getNotes(){
		return notes;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(Arrays.stream(notes).map(Note::toString).collect(Collectors.joining(",")));
		builder.append("]");
		return builder.toString();
	}
}
