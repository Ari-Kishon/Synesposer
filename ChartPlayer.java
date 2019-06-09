package p1;
import java.io.IOException;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
public class ChartPlayer extends JFrame {
	
	public static void Play(String filename)
	{
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
	
	public static void PlayChart(boolean[][] chart, int tempo, int n) throws InterruptedException{
		for(int i = 0; i < n; i++){
			if(chart[11][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\B-Note.wav");
			}
			if(chart[9][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\A-Note.wav");
			}
			if(chart[7][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\G-Note.wav");
			}
			if(chart[5][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\F-Note.wav");
			}
			if(chart[4][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\E-Note.wav");
			}
			if(chart[2][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\D-Note.wav");
			}
			if(chart[0][i]){
				Play("C:\\Users\\Ari\\Projects\\Computer Music\\Project\\Phase 1 Test\\Phase 1\\src\\p1\\Sounds\\C-Note.wav");
			}
			Thread.sleep(1000/tempo);
		}
	}
}
