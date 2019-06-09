package p1;

public class Sequence {
	boolean[][] chart;
	int tempo;
	int octave;
	int instrument;
	String[] colors;
	String[] keys;
	int volume;
	boolean smooth;
	
	public Sequence (boolean[][] noteChart, int tempo, int octave, int instrument){
		this.chart = new boolean[13][32];
		//THIS LOOKS STUPID BUT IT SOLVES A BUG VVVV
		for(int i = 0; i < 32;i++){
			for(int j = 0; j < 13;j++){
				this.chart[j][i] = noteChart[j][i];
			}
		}
		this.tempo = tempo;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = 10200;
	}
	
	public Sequence (boolean[][] noteChart, int tempo, int octave, int instrument, int volume){
		this.chart = new boolean[13][32];
		//THIS LOOKS STUPID BUT IT SOLVES A BUG VVVV
		for(int i = 0; i < 32;i++){
			for(int j = 0; j < 13;j++){
				this.chart[j][i] = noteChart[j][i];
			}
		}
		this.tempo = tempo;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = volume;
	}
	
	public Sequence (boolean[][] noteChart, int tempo, int octave, int instrument, boolean isSmooth){
		this.chart = new boolean[13][32];
		//THIS LOOKS STUPID BUT IT SOLVES A BUG VVVV
		for(int i = 0; i < 32;i++){
			for(int j = 0; j < 13;j++){
				this.chart[j][i] = noteChart[j][i];
			}
		}
		this.tempo = tempo;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = 10200;
		if(isSmooth)
			this.smooth = true;
	}
	
	public Sequence (boolean[][] noteChart, int tempo, int octave, int instrument, int volume, boolean isSmooth){
		this.chart = new boolean[13][32];
		//THIS LOOKS STUPID BUT IT SOLVES A BUG VVVV
		for(int i = 0; i < 32;i++){
			for(int j = 0; j < 13;j++){
				this.chart[j][i] = noteChart[j][i];
			}
		}
		this.tempo = tempo;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = volume;
		if(isSmooth)
			this.smooth = true;
	}
	
	public Sequence(){
		this.chart = new boolean[13][32];
		this.tempo = 32;
		this.octave = 4;
		this.instrument = 1;
		this.smooth = false;
	}
	public String toText(){
		return ("SMOOTH : [" + smooth +"] - INST : [" + MIDIController.InstList.get((byte) (instrument-+1)) + "] - OCT : [" + octave + "] - TEMPO : [1/" +tempo +"]");
	}
}
