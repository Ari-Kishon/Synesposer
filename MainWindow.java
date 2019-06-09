package p1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.awt.Canvas;
import java.awt.Color;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.Choice;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.Box;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainWindow extends JFrame {
	
	static SynthGen synthGen;
	static Sequence sequenceA = new Sequence();
	static Sequence sequenceB = new Sequence();
	static Sequence sequenceC = new Sequence();
	static Sequence sequenceD = new Sequence();
	static Sequence emptySeq = new Sequence();
	private List<Pattern> imports = new ArrayList<Pattern>();
	
	static Sequence[] sequences = {sequenceA,sequenceB,sequenceC,sequenceD};
	static String[] tracks = new String[4];
	
	private JPanel contentPane;
	private JTextField labelA;
	private JTextField labelB;
	private JTextField labelC;
	private JTextField labelD;
	private String currentBTrack;
	private String currentDTrack;
	MIDIController MIDIplayer;
	Player mainPlayer = new Player();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setTitle("Main Window");
					synthGen = new SynthGen();
					frame.setVisible(true);
					synthGen.frmSynesposer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private void changeDTrack(String track){
		try {
			currentDTrack = MidiFileManager.loadPatternFromMidi(new File(track)).toString();
		} catch (IOException | InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			//ADD ERROR HANDLING ############
			e.printStackTrace();
		}
	}
	
	private void changeBTrack(String track){
		try {
			currentBTrack = MidiFileManager.loadPatternFromMidi(new File(track)).toString();
		} catch (IOException | InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			//ADD ERROR HANDLING ############
			e.printStackTrace();
		}
	}
	public MainWindow() {
		MIDIplayer = new MIDIController();
		MIDIplayer.start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSpinner volA = new JSpinner();
		volA.setOpaque(false);
		volA.setForeground(new Color(238, 232, 170));
		volA.setFont(new Font("Hameagel", Font.PLAIN, 14));
		volA.setBackground(new Color(0, 0, 0));
		volA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(sequenceA != null){
					sequenceA.volume = (int) volA.getValue();
				}
			}
		});
		volA.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		volA.setBounds(516, 63, 43, 31);
		contentPane.add(volA);
		
		JSpinner octA = new JSpinner();
		octA.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		octA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				sequenceA.octave = (int) octA.getValue();
			}
		});
		octA.setBounds(516, 105, 43, 31);
		contentPane.add(octA);
		
		JLabel lblVolume = new JLabel("Volume");
		lblVolume.setForeground(new Color(255, 255, 240));
		lblVolume.setFont(new Font("Hameagel", Font.PLAIN, 14));
		lblVolume.setBounds(569, 63, 46, 14);
		contentPane.add(lblVolume);
		
		JLabel lblNewLabel = new JLabel("Octave");
		lblNewLabel.setForeground(new Color(255, 255, 240));
		lblNewLabel.setFont(new Font("Hameagel", Font.PLAIN, 14));
		lblNewLabel.setBounds(569, 107, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("Volume");
		label.setForeground(new Color(255, 255, 240));
		label.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label.setBounds(569, 288, 46, 14);
		contentPane.add(label);
		
		JSpinner volB = new JSpinner();
		volB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequenceB.volume = (int) volB.getValue();
			}
		});
		volB.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		volB.setBounds(516, 288, 43, 31);
		contentPane.add(volB);
		
		JSpinner octB = new JSpinner();
		octB.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		octB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequenceB.octave = (int) octB.getValue();
			}
		});
		octB.setBounds(516, 330, 43, 31);
		contentPane.add(octB);
		
		JLabel label_1 = new JLabel("Octave");
		label_1.setForeground(new Color(255, 255, 240));
		label_1.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label_1.setBounds(569, 332, 46, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Volume");
		label_2.setForeground(new Color(255, 255, 240));
		label_2.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label_2.setBounds(569, 524, 46, 14);
		contentPane.add(label_2);
		
		JSpinner volC = new JSpinner();
		volC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			sequenceC.volume = (int) volC.getValue();
			}
		});
		volC.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		volC.setBounds(516, 524, 43, 31);
		contentPane.add(volC);
		
		JSpinner octC = new JSpinner();
		octC.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		octC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequenceC.octave = (int) octC.getValue();
			}
		});
		octC.setBounds(516, 566, 43, 31);
		contentPane.add(octC);
		
		JLabel label_3 = new JLabel("Octave");
		label_3.setForeground(new Color(255, 255, 240));
		label_3.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label_3.setBounds(569, 568, 46, 14);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("Volume");
		label_4.setForeground(new Color(255, 255, 240));
		label_4.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label_4.setBounds(569, 741, 46, 14);
		contentPane.add(label_4);
		
		JSpinner volD = new JSpinner();
		volD.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequenceD.volume = (int) volD.getValue();
			}
		});
		volD.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		volD.setBounds(516, 741, 43, 31);
		contentPane.add(volD);
		
		JSpinner octD = new JSpinner();
		octD.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		octD.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequenceD.octave = (int) octD.getValue();
			}
		});
		octD.setBounds(516, 783, 43, 31);
		contentPane.add(octD);
		
		JLabel label_5 = new JLabel("Octave");
		label_5.setForeground(new Color(255, 255, 240));
		label_5.setFont(new Font("Hameagel", Font.PLAIN, 14));
		label_5.setBounds(569, 785, 46, 14);
		contentPane.add(label_5);
		
		Canvas CanvasA = new Canvas();
		CanvasA.setBackground(Color.BLACK);
		CanvasA.setBounds(10, 56, 500, 107);
		contentPane.add(CanvasA);
		
		JButton btnDebug = new JButton("Stop");
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*System.out.println("--------------------------------------");
				for(int i = 1; i < 2; i++){
					System.out.println(tracks[i]);
				}*/
				MIDIController.Debug(FugueBuilder.Sequence2Voice(emptySeq, 0));
			}
		});
		btnDebug.setBounds(10, 927, 158, 23);
		contentPane.add(btnDebug);
		
		JButton GetA = new JButton("Get Sequence 1");
		GetA.setBackground(new Color(255, 255, 240));
		GetA.setFont(new Font("Hameagel", Font.PLAIN, 14));
		GetA.setForeground(new Color(0, 0, 128));
		GetA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sequenceA = synthGen.Export();
				sequences[0] = sequenceA;
				CanvasA.setSize(4000 / (sequenceA.tempo), CanvasA.getHeight());
				CanvasA.getGraphics().clearRect(0, 0, CanvasA.getWidth(), CanvasA.getHeight());
				tracks[0] = FugueBuilder.Sequence2Voice(sequenceA, 1);
				labelA.setText(sequenceA.toText());
				Drawing.DrawChart(CanvasA,sequences[0],ColorSets.synesposer);
				volA.setValue(sequenceA.volume);
				octA.setValue(sequenceA.octave);
			}
		});
		GetA.setBounds(10, 0, 248, 23);
		contentPane.add(GetA);
		
		Canvas CanvasB = new Canvas();
		CanvasB.setBackground(Color.BLACK);
		CanvasB.setBounds(10, 281, 500, 107);
		contentPane.add(CanvasB);
		
		Canvas CanvasC = new Canvas();
		CanvasC.setBackground(Color.BLACK);
		CanvasC.setBounds(10, 517, 500, 107);
		contentPane.add(CanvasC);
		
		Canvas CanvasD = new Canvas();
		CanvasD.setBackground(Color.BLACK);
		CanvasD.setBounds(10, 741, 500, 100);
		contentPane.add(CanvasD);
		
		JButton GetB = new JButton("Get Sequence 2");
		GetB.setBackground(new Color(255, 255, 240));
		GetB.setForeground(new Color(0, 0, 128));
		GetB.setFont(new Font("Hameagel", Font.PLAIN, 14));
		GetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sequenceB = synthGen.Export();
				sequences[1] = sequenceB;
				CanvasB.setSize(4000 / (sequenceB.tempo), CanvasB.getHeight());
				CanvasB.getGraphics().clearRect(0, 0, CanvasB.getWidth(), CanvasB.getHeight());
				tracks[1] = FugueBuilder.Sequence2Voice(sequenceB, 2);
				labelB.setText(sequenceB.toText());
				Drawing.DrawChart(CanvasB,sequences[1],ColorSets.synesposer);
				Drawing.DrawChart(CanvasB,sequences[1],ColorSets.synesposer);
				volB.setValue(sequenceB.volume);
				octB.setValue(sequenceB.octave);
			}
		});
		GetB.setBounds(10, 228, 248, 23);
		contentPane.add(GetB);
		
		JButton GetC = new JButton("Get Sequence 3");
		GetC.setBackground(new Color(255, 255, 240));
		GetC.setForeground(new Color(0, 0, 128));
		GetC.setFont(new Font("Hameagel", Font.PLAIN, 14));
		GetC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sequenceC = synthGen.Export();
				sequences[2] = sequenceC;
				CanvasC.setSize(4000 / (sequenceC.tempo), CanvasC.getHeight());
				CanvasC.getGraphics().clearRect(0, 0, CanvasC.getWidth(), CanvasC.getHeight());
				tracks[2] = FugueBuilder.Sequence2Voice(sequenceC, 3);
				labelC.setText(sequenceC.toText());
				Drawing.DrawChart(CanvasC,sequences[2],ColorSets.synesposer);
				volC.setValue(sequenceC.volume);
				octC.setValue(sequenceC.octave);
			}
		});
		GetC.setBounds(10, 461, 248, 23);
		contentPane.add(GetC);
		
		JButton GetD = new JButton("Get Sequence 4");
		GetD.setBackground(new Color(255, 255, 240));
		GetD.setForeground(new Color(0, 0, 128));
		GetD.setFont(new Font("Hameagel", Font.PLAIN, 14));
		GetD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sequenceD = synthGen.Export();
				sequences[3] = sequenceD;
				CanvasD.setSize(4000 / (sequenceD.tempo), CanvasD.getHeight());
				CanvasD.getGraphics().clearRect(0, 0, CanvasD.getWidth(), CanvasD.getHeight());
				tracks[3] = FugueBuilder.Sequence2Voice(sequenceD, 4);
				labelD.setText(sequenceD.toText());
				Drawing.DrawChart(CanvasD,sequences[3],ColorSets.synesposer);
				volD.setValue(sequenceD.volume);
				octD.setValue(sequenceD.octave);
			}
		});
		GetD.setBounds(10, 682, 248, 23);
		contentPane.add(GetD);
		
		JButton btnPlayA = new JButton("Play A");
		btnPlayA.setBackground(new Color(255, 255, 240));
		btnPlayA.setForeground(new Color(0, 128, 0));
		btnPlayA.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnPlayA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(() -> {
					if(sequenceA.smooth)
						MIDIplayer.playVoice(FugueBuilder.Sequence2SmoothVoice(sequenceA,1,Scales.chromatic_Int));
					else
						MIDIplayer.playVoice(FugueBuilder.Sequence2Voice(sequenceA,1,Scales.chromatic_Int));
				}).start();
			}
		});
		btnPlayA.setBounds(10, 170, 89, 23);
		contentPane.add(btnPlayA);
		
		JButton btnPlayB = new JButton("Play B");
		btnPlayB.setBackground(new Color(255, 255, 240));
		btnPlayB.setForeground(new Color(0, 128, 0));
		btnPlayB.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnPlayB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(() -> {
					if(sequenceB.smooth)
						MIDIplayer.playVoice(FugueBuilder.Sequence2SmoothVoice(sequenceB,1,Scales.chromatic_Int));
					else
						MIDIplayer.playVoice(FugueBuilder.Sequence2Voice(sequenceB,1,Scales.chromatic_Int));
				}).start();
			}
		});
		btnPlayB.setBounds(10, 394, 89, 23);
		contentPane.add(btnPlayB);
		
		JButton btnPlayC = new JButton("Play C");
		btnPlayC.setBackground(new Color(255, 255, 240));
		btnPlayC.setForeground(new Color(0, 128, 0));
		btnPlayC.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnPlayC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(() -> {
					if(sequenceC.smooth)
						MIDIplayer.playVoice(FugueBuilder.Sequence2SmoothVoice(sequenceC,1,Scales.chromatic_Int));
					else
						MIDIplayer.playVoice(FugueBuilder.Sequence2Voice(sequenceC,1,Scales.chromatic_Int));
				}).start();
			}
		});
		btnPlayC.setBounds(10, 630, 89, 23);
		contentPane.add(btnPlayC);
		
		JButton btnPlayD = new JButton("Play D");
		btnPlayD.setBackground(new Color(255, 255, 240));
		btnPlayD.setForeground(new Color(0, 128, 0));
		btnPlayD.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnPlayD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(() -> {
					if(sequenceD.smooth)
						MIDIplayer.playVoice(FugueBuilder.Sequence2SmoothVoice(sequenceD,1,Scales.chromatic_Int));
					else
						MIDIplayer.playVoice(FugueBuilder.Sequence2Voice(sequenceD ,1,Scales.chromatic_Int));
				}).start(); 
			}
		});
		btnPlayD.setBounds(10, 847, 89, 23);
		contentPane.add(btnPlayD);
		
		
		JButton btnDebug2 = new JButton("Play LOOP");
		btnDebug2.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnDebug2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] LoopedTracks = new String[6];
				for(int i = 0; i < sequences.length; i++){
					if(sequences[i].smooth)
						LoopedTracks[i] = FugueBuilder.Sequence2LoopedSmoothVoice(sequences[i], i+ 11, 2 * (sequences[i].tempo/2));
					else
						LoopedTracks[i] = FugueBuilder.Sequence2LoopedVoice(sequences[i], i+ 11, 2 * (sequences[i].tempo/2));
					System.out.println(LoopedTracks[i]);
				}
				LoopedTracks[4] = currentBTrack;
				LoopedTracks[5] = currentDTrack;
				System.out.println("BASS : " + currentBTrack);
				System.out.println("DRUMS : " + currentDTrack);
				new Thread(() -> {
					MIDIplayer.playVoice(FugueBuilder.BuildBand(LoopedTracks));
				}).start();
			}
		});
		btnDebug2.setBounds(178, 927, 158, 23);
		contentPane.add(btnDebug2);
		
		labelA = new JTextField();
		labelA.setBackground(new Color(0, 0, 0));
		labelA.setForeground(new Color(240, 230, 140));
		labelA.setFont(new Font("Hameagel", Font.PLAIN, 13));
		labelA.setEditable(false);
		labelA.setBounds(10, 35, 500, 20);
		contentPane.add(labelA);
		labelA.setColumns(10);
		
		labelB = new JTextField();
		labelB.setBackground(new Color(0, 0, 0));
		labelB.setForeground(new Color(240, 230, 140));
		labelB.setFont(new Font("Hameagel", Font.PLAIN, 13));
		labelB.setEditable(false);
		labelB.setColumns(10);
		labelB.setBounds(10, 260, 500, 20);
		contentPane.add(labelB);
		
		labelC = new JTextField();
		labelC.setBackground(new Color(0, 0, 0));
		labelC.setForeground(new Color(240, 230, 140));
		labelC.setFont(new Font("Hameagel", Font.PLAIN, 13));
		labelC.setEditable(false);
		labelC.setColumns(10);
		labelC.setBounds(10, 496, 500, 20);
		contentPane.add(labelC);
		
		labelD = new JTextField();
		labelD.setBackground(new Color(0, 0, 0));
		labelD.setForeground(new Color(240, 230, 140));
		labelD.setFont(new Font("Hameagel", Font.PLAIN, 13));
		labelD.setEditable(false);
		labelD.setColumns(10);
		labelD.setBounds(10, 715, 500, 20);
		contentPane.add(labelD);
		
		JButton btnResetC = new JButton("Reset C");
		btnResetC.setBackground(new Color(255, 255, 240));
		btnResetC.setForeground(new Color(128, 0, 0));
		btnResetC.setFont(new Font("Hameagel", Font.PLAIN, 16));
		btnResetC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequenceC = emptySeq;
				sequences[2] = sequenceC;
				CanvasC.setSize(2000 / (sequenceC.tempo), CanvasC.getHeight());
				CanvasC.getGraphics().clearRect(0, 0, CanvasC.getWidth(), CanvasC.getHeight());
				tracks[2] = FugueBuilder.Sequence2Voice(sequenceC, 3);
				labelC.setText(sequenceC.toText());
			}
		});
		btnResetC.setBounds(268, 630, 242, 23);
		contentPane.add(btnResetC);
		
		JButton btnResetD = new JButton("Reset D");
		btnResetD.setBackground(new Color(255, 255, 240));
		btnResetD.setForeground(new Color(128, 0, 0));
		btnResetD.setFont(new Font("Hameagel", Font.PLAIN, 16));
		btnResetD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequenceD = emptySeq;
				sequences[3] = sequenceC;
				CanvasD.setSize(2000 / (sequenceD.tempo), CanvasD.getHeight());
				CanvasD.getGraphics().clearRect(0, 0, CanvasD.getWidth(), CanvasD.getHeight());
				tracks[3] = FugueBuilder.Sequence2Voice(sequenceD, 4);
				labelD.setText(sequenceD.toText());
			}
		});
		btnResetD.setBounds(268, 847, 242, 23);
		contentPane.add(btnResetD);
		
		JButton btnResetB = new JButton("Reset B");
		btnResetB.setBackground(new Color(255, 255, 240));
		btnResetB.setFont(new Font("Hameagel", Font.PLAIN, 16));
		btnResetB.setForeground(new Color(128, 0, 0));
		btnResetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequenceB = emptySeq;
				sequences[1] = sequenceB;
				CanvasB.setSize(2000 / (sequenceB.tempo), CanvasB.getHeight());
				CanvasB.getGraphics().clearRect(0, 0, CanvasB.getWidth(), CanvasB.getHeight());
				tracks[1] = FugueBuilder.Sequence2Voice(sequenceB, 2);
				labelB.setText(sequenceB.toText());
				Drawing.DrawChart(CanvasB,sequences[1],ColorSets.synesposer);
			}
		});
		btnResetB.setOpaque(false);
		btnResetB.setBounds(268, 394, 242, 23);
		contentPane.add(btnResetB);
		
		JButton btnResetA = new JButton("Reset A");
		btnResetA.setFont(new Font("Hameagel", Font.PLAIN, 16));
		btnResetA.setForeground(new Color(128, 0, 0));
		btnResetA.setBackground(new Color(255, 255, 240));
		btnResetA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				sequenceA = emptySeq;
				sequences[0] = sequenceA;
				CanvasA.setSize(2000 / (sequenceA.tempo), CanvasA.getHeight());
				CanvasA.getGraphics().clearRect(0, 0, CanvasA.getWidth(), CanvasA.getHeight());
				tracks[0] = FugueBuilder.Sequence2Voice(sequenceA, 1);
				labelA.setText(sequenceA.toText());
				Drawing.DrawChart(CanvasA,sequences[0],ColorSets.synesposer);
			}
		});
		btnResetA.setBounds(268, 169, 242, 23);
		contentPane.add(btnResetA);
		
		JButton btnDebugButton = new JButton("MIDI Import");
		btnDebugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final Player player = new Player();
	            Pattern pattern;
				try {
					imports.add(pattern = MidiFileManager.loadPatternFromMidi(new File("MIDI IMPORT")));
					currentBTrack = pattern.toString();
				} catch (IOException | InvalidMidiDataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
			}
		});
		btnDebugButton.setBounds(346, 927, 152, 23);
		contentPane.add(btnDebugButton);
		
	
		JButton btnMIDIExport = new JButton("MIDI Export");
		btnMIDIExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File f = new File("MIDI EXPORT");
					String[] LoopedTracks = new String[6];
					for(int i = 0; i < sequences.length; i++){
						if(sequences[i].smooth)
							LoopedTracks[i] = FugueBuilder.Sequence2LoopedSmoothVoice(sequences[i], i+ 1, 2 * (sequences[i].tempo/2));
						else
							LoopedTracks[i] = FugueBuilder.Sequence2LoopedVoice(sequences[i], i+ 1, 2 * (sequences[i].tempo/2));
						System.out.println(LoopedTracks[i]);
					}
					LoopedTracks[4] = currentBTrack;
					LoopedTracks[5] = currentDTrack;
					Pattern pattern = new Pattern(FugueBuilder.BuildBand(LoopedTracks)); 
					MidiFileManager.savePatternToMidi(pattern, f);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnMIDIExport.setBounds(508, 927, 158, 23);
		contentPane.add(btnMIDIExport);
		
		JButton btnReeditSequenceA = new JButton("RE-EDIT Sequence A");
		btnReeditSequenceA.setBackground(new Color(255, 255, 240));
		btnReeditSequenceA.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnReeditSequenceA.setForeground(new Color(0, 0, 128));
		btnReeditSequenceA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synthGen.LoadSequence(sequenceA);
			}
		});
		btnReeditSequenceA.setBounds(268, 0, 242, 23);
		contentPane.add(btnReeditSequenceA);
		
		JButton btnReeditSequenceB = new JButton("RE-EDIT Sequence B");
		btnReeditSequenceB.setBackground(new Color(255, 255, 240));
		btnReeditSequenceB.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnReeditSequenceB.setForeground(new Color(0, 0, 128));
		btnReeditSequenceB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synthGen.LoadSequence(sequenceB);
			}
		});
		btnReeditSequenceB.setBounds(268, 228, 242, 23);
		contentPane.add(btnReeditSequenceB);
		
		JButton btnReeditSequenceC = new JButton("RE-EDIT Sequence C");
		btnReeditSequenceC.setBackground(new Color(255, 255, 240));
		btnReeditSequenceC.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnReeditSequenceC.setForeground(new Color(0, 0, 128));
		btnReeditSequenceC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synthGen.LoadSequence(sequenceC);
			}
		});
		btnReeditSequenceC.setBounds(268, 461, 242, 23);
		contentPane.add(btnReeditSequenceC);
		
		JButton btnReeditSequenceD = new JButton("RE-EDIT Sequence D");
		btnReeditSequenceD.setBackground(new Color(255, 255, 240));
		btnReeditSequenceD.setFont(new Font("Hameagel", Font.PLAIN, 14));
		btnReeditSequenceD.setForeground(new Color(0, 0, 128));
		btnReeditSequenceD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synthGen.LoadSequence(sequenceD);
			}
		});
		btnReeditSequenceD.setBounds(268, 682, 242, 23);
		contentPane.add(btnReeditSequenceD);
		
		JButton btnNone = new JButton("None");
		btnNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDTrack = "";
			}
		});
		btnNone.setBounds(1040, 41, 130, 23);
		contentPane.add(btnNone);
		
		JLabel lblBackingTrack = new JLabel("Drum Track :");
		lblBackingTrack.setFont(new Font("Hameagel", Font.PLAIN, 20));
		lblBackingTrack.setBackground(Color.PINK);
		lblBackingTrack.setBounds(1040, 11, 130, 19);
		contentPane.add(lblBackingTrack);
		
		JButton btnDaFunk = new JButton("DaFunk");
		btnDaFunk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeDTrack("Drums - DaFunk.mid");
			}
		});
		btnDaFunk.setBounds(1040, 68, 130, 23);
		contentPane.add(btnDaFunk);
		
		JButton drum3 = new JButton("Fresh");
		drum3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeDTrack("Fresh.mid");
			}
		});
		drum3.setBounds(1040, 128, 130, 23);
		contentPane.add(drum3);
		
		JButton drum5 = new JButton("Times- BUGGY");
		drum5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeDTrack("Times.mid");
			}
		});
		drum5.setBounds(1040, 188, 130, 23);
		contentPane.add(drum5);
		
		JButton drum4 = new JButton("P.I");
		drum4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeDTrack("PIMP.mid");
			}
		});
		drum4.setBounds(1040, 158, 130, 23);
		contentPane.add(drum4);
		
		JLabel lblBassTrack = new JLabel("Bass Track :");
		lblBassTrack.setFont(new Font("Hameagel", Font.PLAIN, 20));
		lblBassTrack.setBackground(Color.PINK);
		lblBassTrack.setBounds(748, 4, 130, 19);
		contentPane.add(lblBassTrack);
		
		JButton btnJamesBrownStyle = new JButton("Jazz 1");
		btnJamesBrownStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeDTrack("Jazz 1.mid");
			}
		});
		btnJamesBrownStyle.setBounds(1040, 98, 130, 23);
		contentPane.add(btnJamesBrownStyle);
		
		JButton bass1 = new JButton("BreakOn");
		bass1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				changeBTrack("BreakOnThrough.mid");
			}
		});
		bass1.setBounds(748, 63, 130, 23);
		contentPane.add(bass1);
		
		JButton bass2 = new JButton("Dance");
		bass2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				changeBTrack("JustDance.mid");
			}
		});
		bass2.setBounds(748, 93, 130, 23);
		contentPane.add(bass2);
		
		JButton bass3 = new JButton("0.5$");
		bass3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeBTrack("PIMPb.mid");
			}
		});
		bass3.setBounds(748, 123, 130, 23);
		contentPane.add(bass3);
		
		
		JButton bassNone = new JButton("None");
		bassNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBTrack = "";
			}
		});
		bassNone.setBounds(748, 34, 130, 23);
		contentPane.add(bassNone);
		
	
		

		

	}
}
