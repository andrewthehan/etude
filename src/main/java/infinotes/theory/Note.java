
package infinotes.theory;

import infinotes.util.RegEx;

import java.util.Iterator;

/*
* Key with the concept of octave
*/
public class Note{
	private final Key key;
	private final int octave;

	public Note(Key key, int octave){
		this.key = key;
		this.octave = octave;
	}

	public Note apply(KeySignature keySignature){
		return new Note(key.apply(keySignature), octave);
	}

	public Note step(int amount){
		return step(amount, Accidental.Policy.PRIORITIZE_NATURAL);
	}

	public Note step(int amount, Accidental.Policy policy){
		if(Accidental.Policy.MAINTAIN_LETTER == policy){
			int offset = key.getAccidental().getOffset();
			if(offset + amount > Accidental.DOUBLE_SHARP.getOffset() || offset + amount < Accidental.DOUBLE_FLAT.getOffset()){
				throw new RuntimeException("Can't move note " + amount + " step(s) up while maintaining letter: " + this);
			}
			return new Note(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() + amount)), octave);
		}
		return Note.fromProgramNumber(getProgramNumber() + amount, policy);
	}

	public Note step(Interval interval){
		// determine the letter
		Iterator<Letter> it = Letter.iterator(key.getLetter());
		Letter letter = key.getLetter();
		for(int i = 0; i < interval.getNumber() % (MusicConstants.UNIQUE_LETTER_COUNT + 1); ++i){
			letter = it.next();
		}

		// initialize accidental to be the accidental of the new letter in the key signature of this key
		Accidental accidental = new Key(letter).apply(new KeySignature(key, Mode.MAJOR)).getAccidental();
		// change accidental based on interval's quality
		switch(interval.getQuality()){
			case PERFECT: MAJOR:
				break;
			case MINOR:
				accidental = Accidental.fromOffset(accidental.getOffset() - 1);
				break;
			case DIMINISHED:
				accidental = Accidental.fromOffset(accidental.getOffset() - (Interval.isPerfect(interval.getNumber()) ? 1 : 2));
				break;
			case AUGMENTED:
				accidental = Accidental.fromOffset(accidental.getOffset() + 1);
				break;
		}

		int octaveOffset = (interval.getNumber() + key.getLetter().getOffset() - letter.getOffset()) / MusicConstants.UNIQUE_LETTER_COUNT;
		return new Note(new Key(letter, accidental), octave + octaveOffset);
	}

	public Note halfStepUp(){
		return halfStepUp(Accidental.Policy.PRIORITIZE_SHARP);
	}

	public final Note halfStepUp(Accidental.Policy policy){
		if(Accidental.Policy.MAINTAIN_LETTER == policy){
			if(key.isDoubleSharp()){
				throw new RuntimeException("Can't move note half step up while maintaining letter: " + this);
			}
			return new Note(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() + 1)), octave);
		}
		return Note.fromProgramNumber(getProgramNumber() + 1, policy);
	}

	public Note halfStepDown(){
		return halfStepDown(Accidental.Policy.PRIORITIZE_FLAT);
	}

	public final Note halfStepDown(Accidental.Policy policy){
		if(Accidental.Policy.MAINTAIN_LETTER == policy){
			if(key.isDoubleFlat()){
				throw new RuntimeException("Can't move note half step down while maintaining letter: " + this);
			}
			return new Note(new Key(key.getLetter(), Accidental.fromOffset(key.getAccidental().getOffset() - 1)), octave);
		}
		return Note.fromProgramNumber(getProgramNumber() - 1, policy);
	}

	public final Note getHigherNote(Key key){
		Note note = new Note(key, octave);
		if(!isLowerThan(note)){
			note = new Note(key, octave + 1);
		}
		return note;
	}

	public final Note getLowerNote(Key key){
		Note note = new Note(key, octave);
		if(!isHigherThan(note)){
			note = new Note(key, octave - 1);
		}
		return note;
	}

	public final boolean isHigherThan(Note note){
		return getProgramNumber() > note.getProgramNumber();
	}

	public final boolean isLowerThan(Note note){
		return getProgramNumber() < note.getProgramNumber();
	}

	public static final boolean isEnharmonic(Note a, Note b){
		return a.getProgramNumber() == b.getProgramNumber();
	}

	public static final Note fromProgramNumber(int programNumber){
		return Note.fromProgramNumber(programNumber, Accidental.Policy.PRIORITIZE_NATURAL);
	}

	public static final Note fromProgramNumber(int programNumber, Accidental.Policy policy){
		if(programNumber < MusicConstants.SMALLEST_PROGRAM_NUMBER || programNumber > MusicConstants.LARGEST_PROGRAM_NUMBER){
			throw new RuntimeException("Invalid program number: " + programNumber);
		}
		Key key = Key.fromOffset(Math.floorMod(programNumber, MusicConstants.KEYS_IN_OCTAVE), policy);
		int octave = (int) Math.floor((double) programNumber / MusicConstants.KEYS_IN_OCTAVE);
		return new Note(key, octave);
	}

	public final int getProgramNumber(){
		return octave * MusicConstants.KEYS_IN_OCTAVE + key.getOffset();
	}

	/**
	* Any input in the form
	*   - ${key}${octave}
	*   - ${key}${octave}(${program number})
	* is accepted and converted into a Note.
	* ${program number} is intentionally not accepted because #fromProgramNumber
	* exists and should be used instead.
	*/
	public static final Note fromString(String noteString){
		// longest prefix that contains only letters or #
		Key key = Key.fromString(RegEx.extract("^[a-zA-Z#]{1,}", noteString));
		// first number of length greater than 1 thats followed by an open parentheses (if there is any)
		String octaveString = RegEx.extract("\\d{1,}(?![^(]*\\))", noteString);
		if(octaveString == null){
			throw new RuntimeException("Invalid note string: " + noteString + " (missing octave)");
		}
		int octave = Integer.parseInt(octaveString);
		Note note = new Note(key, octave);

		// a number that has an open parentheses somewhere before it
		String programNumber = RegEx.extract("(?<=\\()\\d{1,}", noteString);
		if(programNumber != null){
			if(note.getProgramNumber() != Integer.parseInt(programNumber)){
				throw new RuntimeException("Invalid note string: " + noteString + " (program number doesn't match key and octave)");
			}
		}

		String converted = note.toString();
		if(programNumber == null){
			String convertedNoParentheses = converted.substring(0, converted.indexOf("("));
			if(!convertedNoParentheses.equals(noteString)){
				if(convertedNoParentheses.length() > noteString.length()){
					throw new RuntimeException("Invalid note string: " + noteString + " (missing information)");
				}
				else{
					throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
				}
			}
		}
		else{
			if(!converted.equals(noteString)){
				if(converted.length() > noteString.length()){
					throw new RuntimeException("Invalid note string: " + noteString + " (missing information)");
				}
				else{
					throw new RuntimeException("Invalid note string: " + noteString + " (contains extra information)");
				}
			}
		}
		return note;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(key);
		builder.append(octave);
		builder.append("(");
		builder.append(getProgramNumber());
		builder.append(")");
		return builder.toString();
	}

	public final Key getKey(){
		return key;
	}

	public final int getOctave(){
		return octave;
	}
}
