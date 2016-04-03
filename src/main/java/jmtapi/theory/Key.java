
package jmtapi.theory;

import java.util.Iterator;

/*
* Letter with the concept of accidental
*/
public final class Key{
	private final Letter letter;
	private final Accidental accidental;

	public Key(Letter letter){
		this(letter, Accidental.NONE);
	}

	public Key(Letter letter, Accidental accidental){
		this.letter = letter;
		this.accidental = accidental;
	}

	public final Key apply(KeySignature keySignature){
		Accidental accidental = Accidental.NONE;

		Key key = keySignature.getKey();
		Iterator<Letter> letters = Letter.iterator(key.getLetter());

		int offset = key.getOffset();
		for(int step : keySignature.getMode().getStepPattern()){
			if(letter == letters.next()){
				offset %= MusicConstants.KEYS_IN_OCTAVE;
				if(offset - letter.getOffset() > Accidental.TRIPLE_SHARP.getOffset()){
					offset -= MusicConstants.KEYS_IN_OCTAVE;
				}
				else if(offset - letter.getOffset() < Accidental.TRIPLE_FLAT.getOffset()){
					offset += MusicConstants.KEYS_IN_OCTAVE;
				}
				accidental = Accidental.fromOffset(offset - letter.getOffset());
				break;
			}
			offset += step;
		}
		return new Key(letter, accidental);
	}

	public final Key none(){
		return new Key(letter, Accidental.NONE);
	}

	public final Key natural(){
		return new Key(letter, Accidental.NATURAL);
	}

	public final Key sharp(){
		return new Key(letter, Accidental.SHARP);
	}

	public final Key doubleSharp(){
		return new Key(letter, Accidental.DOUBLE_SHARP);
	}

	public final Key tripleSharp(){
		return new Key(letter, Accidental.TRIPLE_SHARP);
	}

	public final Key flat(){
		return new Key(letter, Accidental.FLAT);
	}

	public final Key doubleFlat(){
		return new Key(letter, Accidental.DOUBLE_FLAT);
	}

	public final Key tripleFlat(){
		return new Key(letter, Accidental.TRIPLE_FLAT);
	}

	public final boolean isNone(){
		return accidental == Accidental.NONE;
	}

	public final boolean isNatural(){
		return accidental == Accidental.NATURAL;
	}

	public final boolean isSharp(){
		return accidental == Accidental.SHARP;
	}

	public final boolean isDoubleSharp(){
		return accidental == Accidental.DOUBLE_SHARP;
	}

	public final boolean isTripleSharp(){
		return accidental == Accidental.TRIPLE_SHARP;
	}

	public final boolean isFlat(){
		return accidental == Accidental.FLAT;
	}

	public final boolean isDoubleFlat(){
		return accidental == Accidental.DOUBLE_FLAT;
	}

	public final boolean isTripleFlat(){
		return accidental == Accidental.TRIPLE_FLAT;
	}

	public static final boolean isEnharmonic(Key a, Key b){
		return Math.floorMod(a.getOffset(), MusicConstants.KEYS_IN_OCTAVE) == Math.floorMod(b.getOffset(), MusicConstants.KEYS_IN_OCTAVE);
	}

	public static final Key fromOffset(int offset){
		return Key.fromOffset(offset, Accidental.Policy.PRIORITIZE_NATURAL);
	}

	public static final Key fromOffset(int offset, Accidental.Policy policy){
		Letter letter = null;
		Accidental accidental = Accidental.NONE;

		switch(policy){
			case MAINTAIN_LETTER:
				throw new RuntimeException("Letter cannot be maintained when creating a Key from an offset");
			case PRIORITIZE_SHARP:
				// maintain order of cases for fall throughs to function correctly
				switch(offset){
					case 11:
						letter = Letter.B;
						break;
					case 10:
						accidental = Accidental.SHARP;
						// fall through
					case 9:
						letter = Letter.A;
						break;
					case 8:
						accidental = Accidental.SHARP;
						// fall through
					case 7:
						letter = Letter.G;
						break;
					case 6:
						accidental = Accidental.SHARP;
						// fall through
					case 5:
						letter = Letter.F;
						break;
					case 4:
						letter = Letter.E;
						break;
					case 3:
						accidental = Accidental.SHARP;
						// fall through
					case 2:
						letter = Letter.D;
						break;
					case 1:
						accidental = Accidental.SHARP;
						// fall through
					case 0:
						letter = Letter.C;
						break;
					default:
						throw new RuntimeException("Invalid offset: " + offset);
				}
				break;
			/*
			* PRIORITIZING_NATURAL prioritizies flats because
			* sharps are more often added (harmonic minor scale)
			* and a natural is preferred over a double sharp
			* (flat + sharp = natural vs. sharp + sharp = double sharp)
			*/
			case PRIORITIZE_NATURAL: case PRIORITIZE_FLAT:
				// maintain order of cases for fall throughs to function correctly
				switch(offset){
					case 0:
						letter = Letter.C;
						break;
					case 1:
						accidental = Accidental.FLAT;
						// fall through
					case 2:
						letter = Letter.D;
						break;
					case 3:
						accidental = Accidental.FLAT;
						// fall through
					case 4:
						letter = Letter.E;
						break;
					case 5:
						letter = Letter.F;
						break;
					case 6:
						accidental = Accidental.FLAT;
						// fall through
					case 7:
						letter = Letter.G;
						break;
					case 8:
						accidental = Accidental.FLAT;
						// fall through
					case 9:
						letter = Letter.A;
						break;
					case 10:
						accidental = Accidental.FLAT;
						// fall through
					case 11:
						letter = Letter.B;
						break;
					default:
						throw new RuntimeException("Invalid offset: " + offset);
				}
			break;
		}
		return new Key(letter, accidental);
	}

	public final int getOffset(){
		return letter.getOffset() + accidental.getOffset();
	}

	public static final Key fromString(String keyString){
		if(keyString == null){
			throw new RuntimeException("Invalid key string: " + keyString);
		}
		else if(keyString.trim().isEmpty()){
			throw new RuntimeException("Invalid key string: " + keyString + " (blank)");
		}
		Letter letter = Letter.fromChar(keyString.charAt(0));
		Accidental accidental = keyString.length() == 1 ? Accidental.NONE : Accidental.fromString(keyString.substring(1));
		return new Key(letter, accidental);
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(letter);
		builder.append(accidental);
		return builder.toString();
	}

	public final Letter getLetter(){
		return letter;
	}

	public final Accidental getAccidental(){
		return accidental;
	}
}
