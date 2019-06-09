package p1;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfugue.midi.MidiDictionary;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class MIDIController extends Thread {
	public void play(boolean[][] chart, int tempo, int inst,int octave){
		Player player = new Player(); 
		Pattern output = new Pattern(FugueBuilder.BuildChoir(chart, tempo,inst, octave));
		player.play(output);
	}
	
	public void playVoice(String voice){
		Player player = new Player(); 
		player.play(voice);
	}
	public static void Debug(String voice){
		Player player = new Player();
		player.play(voice);
		System.out.println(voice);
	}
	
	public void loop(boolean[][] chart, int tempo, int inst,int octave){
		Player player = new Player(); 
		Pattern output = new Pattern(FugueBuilder.BuildEternalChoir(chart, tempo,inst, octave));
		player.play(output);
	}
		
	public void save(boolean[][] chart, int tempo, int inst,int octave) throws IOException{
		File f = new File("MIDI EXPORT");
		Pattern pattern = new Pattern(FugueBuilder.BuildChoir(chart, tempo,inst, octave)); 
		MidiFileManager.savePatternToMidi(pattern, f);
	}
	
	static Map<Byte, String> InstList = MidiDictionary.INSTRUMENT_BYTE_TO_STRING;
	
	public void start(){
	}
}
