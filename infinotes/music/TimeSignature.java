
package infinotes.music;

public class TimeSignature{
	public static final TimeSignature COMMON_TIME = TimeSignature.make(4, Duration.QUARTER);
	public static final TimeSignature CUT_TIME = TimeSignature.make(2, Duration.HALF);
	public static final TimeSignature ALLA_BREVE = CUT_TIME;
	
	private int beatCount;
	private Duration beatValue;
	
	private TimeSignature(int beatCount, Duration beatValue){
		this.beatCount = beatCount;
		this.beatValue = beatValue;
	}
	
	public static TimeSignature make(int beatCount, Duration beatValue){
		return new TimeSignature(beatCount, beatValue);
	}
	
	public int getBeatCount(){
		return beatCount;
	}
	
	public Duration getBeatValue(){
		return beatValue;
	}
	
	@Override
	public String toString(){
		return beatCount + "/" + (int) (1 / beatValue.getValue());
	}
}