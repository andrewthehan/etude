
package infinotes.theory;

public class KeySignature{
	private Key key;
	private Mode mode;
	
	private KeySignature(Key key, Mode mode){
		this.key = key;
		this.mode = mode;
	}
	
	public static KeySignature make(Key key, Mode mode){
		return new KeySignature(key, mode);
	}
	
	public Key getKey(){
		return key;
	}
	
	public Mode getMode(){
		return mode;
	}
	
	@Override
	public String toString(){
		return key.toString() + mode.name();
	}
}