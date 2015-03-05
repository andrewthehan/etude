
package infinotes.theory;

public class TimeSignature{
	public static final TimeSignature COMMON_TIME = TimeSignature.make(4, Duration.QUARTER);
	public static final TimeSignature CUT_TIME = TimeSignature.make(2, Duration.HALF);
	public static final TimeSignature ALLA_BREVE = CUT_TIME;
	
	private int beatCount;
	private Duration beatDuration;
	
	private TimeSignature(int beatCount, Duration beatDuration){
		this.beatCount = beatCount;
		this.beatDuration = beatDuration;
	}
	
	public static TimeSignature make(int beatCount, Duration beatDuration){
		return new TimeSignature(beatCount, beatDuration);
	}
	
	public double getMeasureLength(){
		return beatCount * beatDuration.getValue();
	}
	
	public int getBeatCount(){
		return beatCount;
	}
	
	public Duration getBeatDuration(){
		return beatDuration;
	}
	
	@Override
	public String toString(){
		return beatCount + "/" + (int) (1 / beatDuration.getValue());
	}
}