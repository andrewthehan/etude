
package infinotes.music;

public class Voice{
	private Instrument instrument;
	private Style style;
	
	private Voice(Instrument instrument, Style style){
		this.instrument = instrument;
		this.style = style;
	}
	
	public static Voice make(Instrument instrument, Style style){
		return new Voice(instrument, style);
	}
	
	public Instrument getInstrument(){
		return instrument;
	}
	
	public Style getStyle(){
		return style;
	}
	
	@Override
	public String toString(){
		return "Instrument: " + instrument + " Style: " + style;
	}
}