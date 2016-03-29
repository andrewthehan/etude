
package infinotes;

import infinotes.theory.*;

import java.util.regex.Pattern;
import java.util.Arrays;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

public class Infinotes{
	private static final Pattern WHITESPACE = Pattern.compile("\\s{1,}");

	private final Synthesizer synth;
	private final MidiChannel[] channels;

	public static void main(String[] args){
		Infinotes infinotes = new Infinotes();
		infinotes.start();

		// C Major scale, parsing from a string
		Arrays.stream(Infinotes.parse("C4 D4 E4 F4 G4 A4 B4 C5")).forEach(n -> infinotes.play(200, n));

		// c natural minor scale, using key signature
		KeySignature ks = new KeySignature(Key.fromString("C"), Mode.HARMONIC_MINOR);
		Note scale = new Note(ks.getKey().flat(), 4);
		Letter
			.iterator(ks.getKey().getLetter())
			.forEachRemaining(l -> infinotes.play(200, scale.getHigherNote(new Key(l)).apply(ks)));

		// circle of fifths
		Note note = Note.fromString("C2");
		Interval circle = new Interval(Interval.Quality.PERFECT, 5);
		do{
			infinotes.play(200, note = note.step(circle));
		}while(!Key.isEnharmonic(note.getKey(), Key.fromString("C")));

		// chromatic scale
		note = Note.fromString("C4");
		infinotes.play(200, note);
		do{
			infinotes.play(200, note = note.halfStepUp());
		}while(!Key.isEnharmonic(note.getKey(), Key.fromString("C")));

		// c minor arpeggio using degrees
		Key tonic = ks.keyOf(Degree.TONIC);
		Key mediant = ks.keyOf(Degree.MEDIANT);
		Key dominant = ks.keyOf(Degree.DOMINANT);
		Note arpeggio;
		infinotes.play(200, arpeggio = new Note(tonic, 4));
		infinotes.play(200, arpeggio = arpeggio.getHigherNote(mediant));
		infinotes.play(200, arpeggio = arpeggio.getHigherNote(dominant));
		infinotes.play(200, arpeggio = arpeggio.getHigherNote(tonic));

		// play all chords on C4
		Note c4 = Note.fromString("C4");
		infinotes.play(1000, new Chord(c4, Chord.Quality.MAJOR_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.MINOR_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.DOMINANT_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.DIMINISHED_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.HALF_DIMINISHED_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.MINOR_MAJOR_SEVENTH));
		infinotes.play(1000, new Chord(c4, Chord.Quality.AUGMENTED_MAJOR_SEVENTH));

		infinotes.end();
	}

	public Infinotes(){
		try{
			synth = MidiSystem.getSynthesizer();
			channels = synth.getChannels();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("Error");
		}
	}

	public void start(){
		try{
			synth.open();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void end(){
		try{
			synth.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void play(int duration, Note... notes){
		int voice = 0;
		int piano = 0;
		int volume = 127;
		channels[voice].programChange(piano);
		for(Note n : notes){
			channels[voice].noteOn(n.getProgramNumber(), volume);
		}
		try{
			Thread.sleep(duration);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		for(Note n : notes){
			channels[voice].noteOff(n.getProgramNumber());
		}
	}

	public void play(int duration, Chord chord){
		play(duration, chord.getNotes());
	}

	public static Note[] parse(String notes){
		return Arrays.stream(WHITESPACE.split(notes))
			.map(Note::fromString)
			.toArray(Note[]::new);
	}
}
