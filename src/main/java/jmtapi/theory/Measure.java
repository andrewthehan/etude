
package jmtapi.theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Measure{
	private final TimeSignature timeSignature;

	private final Chord[] chords;

	public Measure(TimeSignature timeSignature, Chord[] chords){
		double sum = Arrays.stream(chords).mapToDouble(c -> c.getValue().getDuration()).sum();
		if(sum < timeSignature.getDurationOfMeasure()){
			throw new RuntimeException("Not enough chords in measure");
		}
		else if(sum > timeSignature.getDurationOfMeasure()){
			throw new RuntimeException("Too many chords in measure");
		}

		this.timeSignature = timeSignature;
		this.chords = chords;
	}

	public static final Builder builder(){
		return new Builder();
	}

	public final TimeSignature getTimeSignature(){
		return timeSignature;
	}

	public final Chord[] getChords(){
		return chords;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("| ");
		builder.append(Arrays.stream(chords).map(Chord::toString).collect(Collectors.joining(" ")));
		builder.append(" |");
		return builder.toString();
	}

	public static final class Builder{
		private TimeSignature timeSignature;
		private List<Chord> chords;

		private double durationSoFar;

		public Builder(){
			chords = new ArrayList<>();
			durationSoFar = 0;
		}

		public final Builder setTimeSignature(TimeSignature timeSignature){
			this.timeSignature = timeSignature;
			if(durationSoFar > timeSignature.getDurationOfMeasure()){
				throw new RuntimeException("Too many chords in measure");
			}
			return this;
		}

		public final Builder add(Note note){
			chords.add(new Chord(new Note[]{note}));
			durationSoFar += note.getValue().getDuration();
			if(timeSignature != null && durationSoFar > timeSignature.getDurationOfMeasure()){
				throw new RuntimeException("Too many chords in measure");
			}
			return this;
		}

		public final Builder add(Chord chord){
			chords.add(chord);
			durationSoFar += chord.getValue().getDuration();
			if(timeSignature != null && durationSoFar > timeSignature.getDurationOfMeasure()){
				throw new RuntimeException("Too many chords in measure");
			}
			return this;
		}

		public final double durationLeft(){
			if(timeSignature == null){
				throw new RuntimeException("Measure requires a time signature");
			}
			return timeSignature.getDurationOfMeasure() - durationSoFar;
		}

		public final Measure build(){
			if(timeSignature == null){
				throw new RuntimeException("Measure requires a time signature");
			}
			return new Measure(timeSignature, chords.toArray(new Chord[chords.size()]));
		}
	}
}
