
package infinotes.theory;

public final class KeySignature{
	private final Key key;
	private final Mode mode;

	public KeySignature(Key key, Mode mode){
		this.key = key;
		this.mode = mode;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(key);
		builder.append(mode);
		return builder.toString();
	}

	public final Key getKey(){
		return key;
	}

	public final Mode getMode(){
		return mode;
	}
}
