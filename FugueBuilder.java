package p1;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
public class FugueBuilder {

	
	public static String BuildVoice(boolean[] chart,String Note, int tempo , int voiceNum, int inst, int octave){
		char dur = 'i';
		switch(tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(inst-1) + " ";
		voice += ("R" + dur + " ");
		for(int i = 0; i < chart.length; i++){
			if(chart[i]){
				voice += (Note + Integer.toString(octave) +dur + " ");
			}
			else{
				voice += ("R" + dur + " ");
			}
		}
		voice += ("R" + dur + " ");
		return voice;
	}
	
	public static String BuildBand(String[] voices){
		String temp ="";
		for(String voice : voices){
			temp += voice + " ";
		}
		return temp;
	}
	public static String BuildChoir(boolean[][] chart, int tempo,int inst,int octave,String [] scale){
		String[] choir = new String[12];
		for(int i = 0; i < 12; i++){
			choir[i] = BuildVoice(chart[i],scale[i],tempo,i,inst, octave);
		}
		String fugue = "";
		System.out.println("________________");
		for(String voice : choir){
			fugue += voice + " ";
			System.out.println(voice);
		}
		System.out.println("________________");
		return fugue;
	}
	
	public static String BuildEternalChoir(boolean[][] chart, int tempo, int inst, int octave , String[] scale){
		String[] choir = new String[12];
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 64 ; j++){
				choir[i] = BuildVoice(chart[i],scale[i],tempo,i,inst, octave);
			}
		}
		String fugue = "";
		System.out.println("________________");
		for(String voice : choir){
			fugue += voice + " ";
			System.out.println(voice);
		}
		System.out.println("________________");
		return fugue;
	}
	
	public static String Chart2Voice(boolean[][] chart, int tempo , int voiceNum, int inst, int octave){
		char dur = 'i';
		switch(tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(inst-1) + " ";
		boolean found = false;
		for(int i = 0; i < chart.length; i++){
			found = false;
			for(int j = 0; j < 12; j++){
				if(chart[j][i]){
					voice += (48+j);
					found = true;
				}
			}
			if(!found)
			voice += ("R" + dur + " ");
		}
		return voice;
	}
	
	public static String Sequence2Voice(Sequence seq, int voiceNum){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 12; j++){
				if(seq.chart[j][i]){
					voice += (((12*seq.octave)+j) + "" + dur + " ");
					found = true;
				}
			}
			if(!found)
			voice += ("R" + dur + " ");
		}
		return voice;
	}
	
	public static String Sequence2Voice(Sequence seq, int voiceNum, String[] scale){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		voice += ("Rq ");
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 12; j++){
				if(seq.chart[j][i]){
					String note = scale[j];
					if(!note.contains("0123456789"))
						// OG CODE 4.2.19
						voice += (scale[j] + seq.octave + "" + dur + " ");
					else
						voice += (scale[j] + dur + " ");
					found = true;
				}
			}
			if(!found)
			voice += ("R" + dur + " ");
		}
		voice += "Rq";
		return voice;
	}
	
	public static String Sequence2SmoothVoice(Sequence seq, int voiceNum, String[] scale){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		voice += ("Rq ");
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 12; j++){
				if(seq.chart[j][i]){
					
					if(i >= 1)
					if(seq.chart[j][i] == seq.chart[j][i-1]){
						voice += dur;
						found = true;
						break;
					}
					
					String note = scale[j];
					if(!note.contains("0123456789"))
						// OG CODE 4.2.19
						voice += (" " + scale[j] + seq.octave + "" + dur);
					else
						voice += (scale[j] + dur + " ");
					found = true;
				}
			}
			if(!found)
			voice += (" R" + dur);
		}
		voice += " R" + dur;
		System.out.println(voice);
		return voice;
	}
	
	// for building voices with integer Scales
	public static String Sequence2Voice(Sequence seq, int voiceNum, int[] scale){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		voice += ":CON(7," + Integer.toString(seq.volume) + ") ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		voice += ":CON(7," + Integer.toString(seq.volume) + ") ";
		voice += ("R" + dur + " ");
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 13; j++){
				if(seq.chart[j][i]){
					String note = Integer.toString(scale[j] + (12*(seq.octave - 4)));
						voice += (note + dur + " ");
					found = true;
				}
			}
			if(!found)
			voice += ("R" + dur + " ");
		}
		voice += ("0q");
		System.out.println(voice);
		return voice;
	}
	
	// Smooth voices with integer scales
	public static String Sequence2SmoothVoice(Sequence seq, int voiceNum, int[] scale){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		voice += ":CON(7," + Integer.toString(seq.volume) + ") ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		voice += ("Rq ");
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 13; j++){
					if(seq.chart[j][i]){
						if(i >= 1)
							if(seq.chart[j][i] == seq.chart[j][i-1]){
								voice += dur;
								found = true;
								break;
							}
						String note = Integer.toString(scale[j] + (12*(seq.octave - 4)));
							voice += (" "+ note + dur);
						found = true;
					}
			}
			if(!found)
			voice += (" R" + dur);
		}
		voice += " 0" + dur;
		return voice;
	}
	public static String Sequence2VoiceDEMO(Sequence seq, int voiceNum, String[] scale){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		voice += ("R" + dur + " ");
		for(int i = 0; i < seq.chart[0].length; i++){
			Boolean found = false;
			for(int j = 0; j < 13; j++){
				if(seq.chart[j][i]){
					//
					//DEMO HACK
					//og VV
					//voice += (scale[j] + seq.octave + "" + dur + " ");
					voice += (scale[j] + dur + " ");
					found = true;
				}
			}
			if(!found)
			voice += ("R" + dur + " ");
		}
		return voice;
	}
	
	public static String Sequence2LoopedVoice(Sequence seq, int voiceNum, int loop){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		voice += ":CON(7," + Integer.toString(seq.volume) + ") ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		//voice += ("R" + dur + " ");
		for(int k = 0; k < loop; k++){
			for(int i = 0; i < seq.chart[0].length; i++){
				Boolean found = false;
				for(int j = 0; j < 13; j++){
					if(seq.chart[j][i]){
						String note = Integer.toString(Scales.chromatic_Int[j] + (12*(seq.octave - 4)));
						voice += (note + dur + " ");
						found = true;
					}
				}
				if(!found)
				voice += ("R" + dur + " ");
			}
		}
		return voice;
	}
	public static String Sequence2LoopedSmoothVoice(Sequence seq, int voiceNum, int loop){
		char dur = 'i';
		switch(seq.tempo){
			case 32 :
				dur = 't';
				break;
			case  16 : 
				dur = 's';
				break;
			case 8 :
				dur = 'i';
				break;
			case 4 :
				dur = 'q';
				break;
			case 2:
				dur = 'h';
				break;
			}
		if(voiceNum == 9){
			voiceNum = 12;
		}
		String voice = "V" + Integer.toString(voiceNum) + " ";
		voice += ":CON(7," + Integer.toString(seq.volume) + ") ";
		//
		// REMEMBER INST-1
		//
		voice += "I" + Integer.toString(seq.instrument-1) + " ";
		// POSSIBLE BUG FIX : CHANGED 2.5.19 
		//voice += ("R" + dur + " ");
		for(int k = 0; k < loop; k++){
			for(int i = 0; i < seq.chart[0].length; i++){
				Boolean found = false;
				for(int j = 0; j < 13; j++){
						if(seq.chart[j][i]){
							if(i >= 1)
								if(seq.chart[j][i] == seq.chart[j][i-1]){
									voice += dur;
									found = true;
									break;
								}
							String note = Integer.toString(Scales.chromatic_Int[j] + (12*(seq.octave - 4)));
								voice += (" "+ note + dur);
							found = true;
						}
				}
				if(!found)
				voice += (" R" + dur);
			}
		}
		return voice;
	}
}
