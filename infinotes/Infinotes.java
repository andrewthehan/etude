
package infinotes;

import infinotes.music.Instrument;
import infinotes.music.Key;
import infinotes.music.KeySignature;
import infinotes.music.Mode;
import infinotes.music.Phrase;
import infinotes.music.Song;
import infinotes.music.Style;
import infinotes.music.TimeSignature;
import infinotes.music.Voice;

import org.jfugue.Pattern;
import org.jfugue.Player;

import java.util.List;
import java.util.Map;

public class Infinotes{
	public static void main(String[] args){
		Song song = Song
			.builder()
			.setKeySignature(KeySignature.make(Key.C, Mode.MAJOR))
			.setTimeSignature(TimeSignature.COMMON_TIME)
			.setTempo(120)
			.addVoice(Instrument.PIANO, Style.MELODY)
			.addVoice(Instrument.PIANO, Style.HARMONY)
			.build();
			
		System.out.println(song);
			
		Map<Voice, List<Phrase>> phrasesForVoice = song.makeSong(16);
		
		Player player = new Player();
		Pattern pattern = new Pattern();
		//pattern.add("K" + song.getKeySignature().toString().substring(0, song.getKeySignature().toString().length() - 2));
		pattern.add("T" + song.getTempo());
		song.getVoices().forEach(v -> {
			pattern.add("\n");
			pattern.add("V" + song.getVoices().indexOf(v));
			pattern.add("I[" + v.getInstrument() + "]");
			phrasesForVoice.get(v).forEach(p -> pattern.add(p.toString()));
		});
		System.out.println(pattern);
		player.play(pattern);
	}
}