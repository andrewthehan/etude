
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
		Arrays.stream(Infinotes.parse("C4 D4 E4 F4 G4 A4 B4 C5")).forEach(n -> infinotes.play(500, n));

		// c natural minor scale, using key signature
		KeySignature ks = new KeySignature(Key.fromString("C"), Mode.HARMONIC_MINOR);
		Note scale = new Note(ks.getKey().flat(), 4);
		Letter
			.iterator(ks.getKey().getLetter())
			.forEachRemaining(l -> infinotes.play(500, scale.getHigherNote(new Key(l)).apply(ks)));

		// circle of fifths
		Note note = Note.fromString("C2");
		Interval circle = new Interval(Quality.PERFECT, 5);
		do{
			infinotes.play(500, note = note.step(circle));
		}while(!Key.isEnharmonic(note.getKey(), Key.fromString("C")));

		// chromatic scale
		note = Note.fromString("C4");
		infinotes.play(500, note);
		do{
			infinotes.play(500, note = note.halfStepUp());
		}while(!Key.isEnharmonic(note.getKey(), Key.fromString("C")));

		// C Major arpeggio and chord
		infinotes.play(500, Note.fromString("C4"));
		infinotes.play(500, Note.fromString("E4"));
		infinotes.play(500, Note.fromString("G4"));
		infinotes.play(500, Note.fromString("C5"));
		infinotes.play(2000, Note.fromString("C4"), Note.fromString("E4"), Note.fromString("G4"), Note.fromString("C5"));

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

	public static Note[] parse(String notes){
		return Arrays.stream(WHITESPACE.split(notes))
			.map(Note::fromString)
			.toArray(Note[]::new);
	}
}
