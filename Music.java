
import java.util.HashMap;
import java.util.Random;
import org.jfugue.IntervalNotation;
import org.jfugue.Pattern;

class Music{
	static final String[] CHROMATIC_KEYS_ENHARMONIC={"A", "A#/Bb", "B", "C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab"};
	static final String[] CHROMATIC_KEYS={"A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab"};
	static final String[] DIATONIC_KEYS={"A", "B", "C", "D", "E", "F", "G"};
	static final String[] DURATIONS={"w", "h" , "q", "i"/*, "s", "t", "x", "o" */};
	static HashMap<String, Double> duration_values;
	static final String[] MAJOR_CHORD_PROGRESSIONS={"1maj -4maj^ -2min -7min^ -6maj -11maj^ -9min -4maj",
										"1maj -6maj -4maj 1maj",
										"1maj -4maj -6maj -4maj"};
	static final String[] MINOR_CHORD_PROGRESSIONS={"1min -4min -3maj -8maj"};
	static HashMap<String, String> interval_patterns;
	static HashMap<Integer, String[]> harmonic_patterns;
	static final String[] CHORD_PATTERNS={"0+1+2+3w"};
	static final String[] OCTAVE_PATTERNS={"0i 3i 0i 3i"}; // <'s = number of octaves down / >'s = number of octaves up
	static final String[] ARPEGGIO_PATTERNS={"0i 1i 2i 3i", "0i 2i 3i 1>i", "0i 2i 3i 2i"};
	static final String[] RAGTIME_PATTERNS={"0i 2+3+1>i 2<i 2+3+1>i"};
	static final String[] WALTZ_PATTERNS={"0q 1+2+3q 2+3+1>q"};
	static Random r=new Random();
	static{
		duration_values=new HashMap<String, Double>();
		duration_values.put("w", 1.0);
		duration_values.put("h", 0.5);
		duration_values.put("q", 0.25);
		duration_values.put("i", 0.125);
		duration_values.put("s", 0.0625);
		duration_values.put("t", 0.03125);
		duration_values.put("x", 0.015625);
		duration_values.put("o", 0.0078125);
		
		interval_patterns=new HashMap<String, String>();
		interval_patterns.put("maj", "0 4 7 12");
		interval_patterns.put("maj^", "4 7 12 16");
		interval_patterns.put("maj^^", "7 12 16 19");
		interval_patterns.put("min", "0 3 7 12");
		interval_patterns.put("min^", "3 7 12 15");
		interval_patterns.put("min^^", "7 12 15 19");
		interval_patterns.put("dom7", "0 4 7 10");
		interval_patterns.put("dom7^", "4 7 10 12");
		interval_patterns.put("dom7^^", "7 10 12 16");
		interval_patterns.put("dom7^^^", "10 12 16 19");
		interval_patterns.put("dim7", "0 3 6 9");
		interval_patterns.put("dim7^", "3 6 9 12");
		interval_patterns.put("dim7^^", "6 9 12 15");
		interval_patterns.put("dim7^^^", "9 12 15 18");
		interval_patterns.put("sus2", "0 2 7 12");
		interval_patterns.put("sus2^", "2 7 12 14");
		interval_patterns.put("sus2^^", "7 12 14 19");
		interval_patterns.put("sus4", "0 5 7 12");
		interval_patterns.put("sus4^", "5 7 12 17");
		interval_patterns.put("sus4^^", "7 12 17 19");
		
		harmonic_patterns=new HashMap<Integer, String[]>();
		harmonic_patterns.put(0, CHORD_PATTERNS);
		harmonic_patterns.put(1, OCTAVE_PATTERNS);
		harmonic_patterns.put(2, ARPEGGIO_PATTERNS);
		harmonic_patterns.put(3, RAGTIME_PATTERNS);
		harmonic_patterns.put(4, WALTZ_PATTERNS);
	}
	private static String getRandomFromArray(String[] array){
		return array[r.nextInt(array.length)];
	}
	private static String getRandomChromaticKey(){
		return getRandomFromArray(CHROMATIC_KEYS);
	}
	private static String getRandomDiatonicKey(){
		return getRandomFromArray(DIATONIC_KEYS);
	}
	private static String getRandomDuration(){
		return getRandomFromArray(DURATIONS);
	}
	private static String getRandomChordProgression(boolean isMajor){
		return getRandomFromArray(isMajor?MAJOR_CHORD_PROGRESSIONS:MINOR_CHORD_PROGRESSIONS);
	}
	private static String[] getRandomHarmonicPattern(){
		return harmonic_patterns.get(r.nextInt(harmonic_patterns.size()));
	}
	private static String getRandomPattern(String[] harmonic_pattern){
		return getRandomFromArray(harmonic_pattern);
	}
	private static double getTimeSignatureValue(String timeSignature){
		String[] values=timeSignature.split("/");
		return Double.parseDouble(values[0])/Double.parseDouble(values[1]);
	}
	public static Pattern getRandomMelody(String timeSignature, int measures){
		double completeMeasure=getTimeSignatureValue(timeSignature);
		Pattern melody=new Pattern();
		for(int i=0; i<measures; i++){
			for(double j=0; j<completeMeasure;){
				String note=getRandomDiatonicKey();
				String duration;
				do
					duration=getRandomDuration();
				while(j+duration_values.get(duration)>completeMeasure);
				note+=duration;
				j+=duration_values.get(duration);
				melody.add(note);
			}
			melody.add("|");
		}
		return melody;
	}
	public static Pattern getRandomHarmony(String timeSignature, int measures, String keySignature, boolean isMajor){
		double completeMeasure=getTimeSignatureValue(timeSignature);
		Pattern harmony=new Pattern();
		String[] harmonic_pattern=getRandomHarmonicPattern(); //octave/arpeggio/waltz
		measuresLoop:
			for(double i=0; i<measures*completeMeasure;){
				String[] chords=getRandomChordProgression(isMajor).split(" "); //split chord progression
				for(String c:chords){
					int rootKey=firstInt(c);
					c=c.replace(Integer.toString(rootKey), ""); //remove rootKey from chord
					String[] intervals=interval_patterns.get(c).split(" "); //get arpeggiated chord
					String[] pattern=getRandomPattern(harmonic_pattern).split(" "); //split a random element in *_PATTERNS
					for(String p:pattern){
						String notes=getNotes(rootKey, p, intervals);
						harmony.add(notes);
						i+=getDurationOfNotes(notes);
					}
					if(i%completeMeasure==0) 
						harmony.add("|");
					if(i>=measures) 
						break measuresLoop;
				}
			}
		IntervalNotation harmony2=new IntervalNotation(harmony.toString());
		return harmony2.getPatternForRootNote(keySignature+"4");
	}
	private static String getNotes(int rootKey, String patternElement, String[] intervals){
		String notes="";
		String keys=patternElement.substring(0,  patternElement.length()-1), duration=patternElement.substring(patternElement.length()-1);
		String[] chordPattern=(patternElement.contains("+")?keys.split("\\+"):new String[]{keys});
		for(String note:chordPattern){
			int octaveAdapt=12*numberOfOccurences(note, ">")-12*numberOfOccurences(note, "<");
			notes+="+<"+(rootKey+Integer.parseInt(intervals[firstInt(note)])+octaveAdapt)+">";
		}
		notes+=duration;
		notes=notes.substring(1); //remove first "+"
		return notes; 
	}
	private static double getDurationOfNotes(String notes){
		return duration_values.get("w")*numberOfOccurences(notes, "w")
						+duration_values.get("h")*numberOfOccurences(notes, "h")
						+duration_values.get("q")*numberOfOccurences(notes, "q")
						+duration_values.get("i")*numberOfOccurences(notes, "i")
						+duration_values.get("s")*numberOfOccurences(notes, "s")
						+duration_values.get("t")*numberOfOccurences(notes, "t")
						+duration_values.get("x")*numberOfOccurences(notes, "x")
						+duration_values.get("o")*numberOfOccurences(notes, "o");
	}
	private static int firstInt(String s){
		return Integer.parseInt(s.replaceAll("[^\\d-]", ""));
	}
	private static int numberOfOccurences(String s, String target){
		String s2=s.replaceAll(target, "");
		return (s.length()-s2.length())/target.length();
	}
}