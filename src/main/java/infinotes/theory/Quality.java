
package infinotes.theory;


public enum Quality{
	PERFECT("P"), MAJOR("M"), MINOR("m"), DIMINISHED("d"), AUGMENTED("A");

	private final String symbol;

	private Quality(String symbol){
		this.symbol = symbol;
	}

	@Override
	public String toString(){
		return symbol;
	}
}
