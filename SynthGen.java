package p1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JToggleButton;
import java.awt.Canvas;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;
import javax.swing.JSlider;

public class SynthGen {
	
	
	final int CHARTSIZE = 64;
	final Color[] COLORS = ColorSets.synesposer;
	
	MIDIController MIDIplayer;
	Player TestPlayer = new Player(); 
	private boolean mouseOverField = false;
	private boolean mousePressed = false;
	private boolean smoothPlay = false;
	private boolean showLetters = true;

	private int currentInstrument = 30;
	private int currentTempo = 8;
	private int N = 4;
	private int currentOctave = 4;
	private int[] currentScale = Scales.chromatic_Int;
	private Color[] currentColors = ColorSets.synesposer;
	
	JFrame frmSynesposer;
	private boolean[][] chart = new boolean[13][CHARTSIZE];
	private JButton[][] bChart = new JButton[13][CHARTSIZE];
	private boolean stop = false;
	
	private Box GSeq,GsSeq,FSeq,FsSeq,ESeq,DSeq,DsSeq,CSeq,CsSeq,BSeq,ASeq,AsSeq,C2Seq;
	private JLabel lblG,lblGS,lblF,lblFS,lblE,lblD,lblDS,lblC,lblCS,lblB,lblA,lblAS;
	private Box NoteRack;
	private Box NoteOverlay;
	ChartPlayer player = new ChartPlayer();
	private Box OctaveController;
	private JLabel CurrentOctave;
	private JButton OctaveUp;
	private JButton OctaveDown;
	private Box horizontalBox_1;
	private Box TempoController;
	private JButton TempoUp;
	private Box TempoBox;
	private JLabel CurrentTempo;
	private JButton TempoDown;
	private Box Debugging;
	private Box PlaybackControl;
	private JButton PlayButton,LoopButton;
	private JButton StopButton;
	private Box horizontalBox;
	private Box InstrumentController;
	private JLabel CurrentInstrument;
	private Box Stage;
	private JButton ExportButton;
	private JLabel Title;
	private Box DEBUG;
	private JToggleButton PaintButton;
	private JButton ResetButton;
	private JButton ClearButton;
	private Canvas canvas;
	private Box horizontalBox_2;
	private Box ScaleController;
	private JButton prevScale;
	private JLabel scaleLabel;
	private JSlider slider;
	private JLabel[] labels = new JLabel[12];
	private JButton btnChromaticScale;
	private JRadioButton smoothButton;
	
	//Borders for UI elements
	Border normalNote = BorderFactory.createStrokeBorder(new BasicStroke (3), Color.black);
	Border QNote = BorderFactory.createStrokeBorder(new BasicStroke (3), Color.red);
	private JButton PentScale;
	private JSpinner spinner;
	private Box ScaleLabel;
	private JToggleButton tglbtnLetters;
	private Box verticalBox_1;
	private Box horizontalBox_4;
	private Box SoundController;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Box ScaleBox;
	private Component verticalStrut;
	private Box verticalBox;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_4;
	private Box horizontalBox_3;
	private Component horizontalStrut_5;
	private Component horizontalStrut_6;
	private JSlider volSlider;
	private JLabel volLabel;
	private Box verticalBox_2;
	private Box verticalBox_3;
	
	
	void ClearAll(){
		ClearCanvas();
		for(int i = 0; i < chart[0].length; i++){
			ClearColumn(i);
		}
		for(boolean[] B : chart){
			for (int i = 0; i < B.length; i++) {
				B[i] = false;
			}
		}
	} 
	
	void DisableButtons (int row){
		for(JButton b : bChart[row]){
			b.setEnabled(false);
			b.setBackground(Color.BLACK);
			//labels[row].setEnabled(false);
		}
	}
	void EnableButtons (int row){
		for(JButton b : bChart[row]){
			b.setEnabled(true);
			b.setBackground(Color.DARK_GRAY);
			//labels[row].setVisible(true);
		}
	}
	
	
	void RefreshLabels(){
		for(int i = 0; i< 13; i++){
			labels[i].setText(Integer.toString(currentScale[i]));
			labels[i].setForeground(currentColors[i]);
			
			for(int j = 0; j < 12; j++){
				for(JButton b : bChart[j]){
					b.setForeground(currentColors[j]);
				}
			}
			ClearAll();
		}
	}
	
	private void pressButton(int degree, int time){
		if(bChart[degree][time].isEnabled()){
			if(!chart[degree][time]){
				ClearColumn(time);
				new Thread(() -> {
					TestPlayer.play("I" + Integer.toString(currentInstrument-1) + " " + Integer.toString(currentScale[degree]+(12*(currentOctave - 4))));
				}).start();
			}	
			//THIS CANT STAY
			//
			//
			//	
			chart[degree][time] = !chart[degree][time];
			ChangeButtonColor(bChart[degree][time]);
			//KILLING PERFORMANCE
			ClearCanvas();
			DrawMarkers((Graphics2D)NoteRack.getGraphics());
			DrawChart((Graphics2D)canvas.getGraphics());
			//DrawLine((Graphics2D)NoteOverlay.getGraphics(),degree,time);
		}
	}
	
	void DrawMarkers(Graphics2D G2D){
		int hUnit = NoteRack.getWidth()/chart[0].length;
		for(int i = 1; i < CHARTSIZE; i++){
			G2D.setStroke(new BasicStroke(3));
			if(i % 8 == 0){
				G2D.setColor(Color.BLACK);
				G2D.drawLine(i*hUnit + 8, NoteRack.getHeight(), i*hUnit + 8, 0);
			}
			if(i % 16 == 0){
				
				G2D.setColor(Color.RED);
				G2D.drawLine(i*hUnit + 8, NoteRack.getHeight(), i*hUnit + 8, 0);
			}
		}
	}
	
	void DrawConnection(Graphics2D G2D){
		G2D.setStroke(new BasicStroke(3));
		G2D.finalize();
		int hUnit = NoteRack.getWidth()/chart[0].length;
		int vUnit = NoteRack.getHeight()/14;
		for(int i = 1; i < CHARTSIZE; i++){
			for(int j = 0; j < 13; j++){
				G2D.setColor(COLORS[j]);
				if(chart[j][i]){
					for(int jN = 0; jN < 13; jN++){
						if(chart[jN][i - 1]){
							G2D.setPaint(new GradientPaint((i+1) *hUnit, NoteRack.getHeight() - (j+1) * vUnit,this.COLORS[j], (i) * hUnit, NoteRack.getHeight() - (jN+1) * vUnit, this.COLORS[jN]));
							G2D.drawLine((i+1) *hUnit, NoteRack.getHeight() - (j+1) * vUnit, (i) * hUnit, NoteRack.getHeight() - (jN+1) * vUnit);
						}
					}
				}
			}	
		}
	}
	
	void DrawLine(Graphics2D G2D, int note,int time){
		G2D.setStroke(new BasicStroke(3));
		int hUnit = NoteRack.getWidth()/chart[0].length + 1;
		int vUnit = NoteRack.getHeight()/14;
		
		//
		//Rectangle prevBar = new Rectangle((time) * hUnit, 0, bChart[0][0].getWidth(), NoteRack.getHeight());
		//G2D.clearRect((time+1) *hUnit, 0, (time) * hUnit, NoteRack.getHeight());
		//G2D.setColor(Color.DARK_GRAY);
		//G2D.fill(prevBar);
		//G2D.drawRect((time) * hUnit, 0, bChart[0][0].getWidth(), NoteRack.getHeight());
		for(int j = 0; j < 13; j++){
			G2D.setColor(ColorSets.synesposer[note]);
			if(time != 0)
			if(chart[j][time-1]){
				G2D.setPaint(new GradientPaint((time+1) *hUnit, NoteRack.getHeight() - (note+1) * vUnit, COLORS[note],(time) * hUnit, NoteRack.getHeight() - (j+1) * vUnit,COLORS[j]));
				G2D.drawLine((time+1) *hUnit, NoteRack.getHeight() - (note+1) * vUnit, (time) * hUnit, NoteRack.getHeight() - (j+1) * vUnit);
			}
		}
	}
	Sequence Export(){
		Sequence temp = new Sequence(chart,currentTempo,currentOctave,currentInstrument,volSlider.getValue(),smoothPlay);
		return temp;
	}
	
	private void ClearCanvas(){
		canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	private void ClearColumn(int col){
		for(JButton[] J : bChart){
			if(J[col].isEnabled())
			J[col].setBackground(Color.DARK_GRAY);
		}
		for(boolean[] B : chart){
				B[col] = false;
		}
	}
	
	private void DrawChart(Graphics2D G2D){
		int hUnit = canvas.getWidth()/chart[0].length;
		int vUnit = canvas.getHeight()/13;
		int oSize = Math.min(vUnit,hUnit)/6 * (11 - currentOctave);
		for (int j = 1; j < 14; j++) {
			G2D.setPaint(Color.white);
			G2D.drawLine(0,vUnit*j ,canvas.getWidth(),vUnit*j);
		}
		Point origin = canvas.getLocation();
		for(int i = 0; i < CHARTSIZE; i++){
			for (int j = 0; j < 13; j++) {
				Point p = bChart[j][i].getLocation();
		        G2D.setPaint(bChart[j][i].getForeground());
		        //this looks cool AND USEFUL!
				//G2D.drawOval(origin.x + (i*50),origin.y - (j)*vUnit,20,20);
				if(chart[j][i]){
					boolean foundNext = false;
					// Set Color to note color
			        G2D.setPaint(currentColors[j]);
					// Draw note circle
			        if(smoothPlay && i != (CHARTSIZE-1)){
			        	if(chart[j][i+1])
			        		G2D.fillOval(origin.x + (i*hUnit) ,(13 - j)*vUnit - oSize/2 ,(oSize + hUnit) ,oSize);
			        	else
			        		G2D.fillOval(origin.x + (i*hUnit) ,(13 - j)*vUnit - oSize/2 ,oSize,oSize);
			        }
			        else{
			        	G2D.fillOval(origin.x + (i*hUnit) ,(13 - j)*vUnit - oSize/2 ,oSize,oSize);
			        }
					if(i != CHARTSIZE - 1){
						for(int k = 0; k < 13; k++){
							if(chart[k][i+1] == true){
								foundNext = true;
								Point p2 = bChart[k][i+1].getLocation();
								//Set Stroke Width
								G2D.setStroke(new BasicStroke((12 - currentOctave)));
								//Draw Line
								G2D.setPaint(new GradientPaint(origin.x + (i*hUnit) + oSize/2,(13-j)*vUnit,currentColors[j],origin.x + ((i+1)*hUnit) + oSize/2,(13 - k)*vUnit,currentColors[k]));
								G2D.drawLine(origin.x + (i*hUnit) + oSize/2, (13 - j)*vUnit, origin.x + ((i+1)*hUnit) + oSize/2, (13 - k)*vUnit);
							}
						}
					}
				}
			}
		}
	}
	
	private void ChangeButtonColor(JButton button){
		if(button.getBackground().equals(button.getForeground())){
			button.setBackground(Color.DARK_GRAY);
		}
		else{
			button.setBackground(button.getForeground());
		}
	}
	
	void LoadSequence(Sequence seq){
		ClearAll();
		currentInstrument = seq.instrument;
		UpdateInstrument();
		currentTempo = seq.tempo;
		UpdateTempo();
		currentOctave = seq.octave;
		UpdateOctave();
		smoothPlay = seq.smooth;
		for(int i = 0; i < seq.chart[0].length; i++){
			for(int j = 0; j < 13; j++){
				if(seq.chart[j][i]){
					bChart[j][i].doClick();
					pressButton(j,i);
				}
			}
		}
	}
	
	void UpdateInstrument(){
		CurrentInstrument.setText(MIDIController.InstList.get((byte) (currentInstrument-1)));
		slider.setValue(currentInstrument);
	}
	
	void UpdateTempo(){
		CurrentTempo.setText("TEMPO : 1/" + Integer.toString(currentTempo));
	}
	
	void UpdateOctave(){
		CurrentOctave.setText("OCTAVE : " + Integer.toString(currentOctave));
	} 
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SynthGen window = new SynthGen();
					window.frmSynesposer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SynthGen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MIDIplayer = new MIDIController();
		MIDIplayer.start();
		
		frmSynesposer = new JFrame();
		frmSynesposer.setTitle("Synesposer");
		frmSynesposer.getContentPane().setBackground(new Color(0, 128, 128));
		frmSynesposer.setBackground(Color.WHITE);
		frmSynesposer.setBounds(0, 0, 1600, 900);
		frmSynesposer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Stage = Box.createVerticalBox();
		frmSynesposer.getContentPane().add(Stage, BorderLayout.CENTER);
		
		NoteRack = Box.createVerticalBox();
		NoteRack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mousePressed = true;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}
		});
		
		verticalBox = Box.createVerticalBox();
		verticalBox.setBackground(new Color(250, 240, 230));
		verticalBox.setBorder(new LineBorder(new Color(250, 240, 230), 4, true));
		Stage.add(verticalBox);
		
		SoundController = Box.createHorizontalBox();
		verticalBox.add(SoundController);
		
		OctaveController = Box.createVerticalBox();
		OctaveController.setBorder(new LineBorder(new Color(250, 240, 230), 4));
		SoundController.add(OctaveController);
		OctaveController.setOpaque(true);
		OctaveController.setBackground(new Color(0, 128, 128));
		
		OctaveUp = new JButton("UP");
		OctaveUp.setForeground(new Color(0, 0, 128));
		OctaveUp.setBackground(new Color(250, 240, 230));
		OctaveUp.setFont(new Font("Hameagel", Font.PLAIN, 14));
		OctaveUp.setAlignmentX(Component.CENTER_ALIGNMENT);
		OctaveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentOctave < 9)
					currentOctave++;
				
				UpdateOctave();
			}
		});
		OctaveController.add(OctaveUp);
		
		horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setBackground(Color.WHITE);
		OctaveController.add(horizontalBox_1);
		
		CurrentOctave = new JLabel("OCTAVE : " + Integer.toString(currentOctave));
		CurrentOctave.setFont(new Font("Hameagel", Font.PLAIN, 20));
		CurrentOctave.setAlignmentX(Component.CENTER_ALIGNMENT);
		CurrentOctave.setForeground(new Color(255, 255, 224));
		horizontalBox_1.add(CurrentOctave);
		CurrentOctave.setHorizontalAlignment(SwingConstants.CENTER);
		
		OctaveDown = new JButton("DOWN");
		OctaveDown.setBorder(UIManager.getBorder("Button.border"));
		OctaveDown.setForeground(new Color(0, 0, 128));
		OctaveDown.setBackground(new Color(250, 240, 230));
		OctaveDown.setFont(new Font("Hameagel", Font.PLAIN, 14));
		OctaveDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		OctaveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentOctave > 0)
					currentOctave--;
				UpdateOctave();
			}
		});
		OctaveController.add(OctaveDown);
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setMaximumSize(new Dimension(20, 20));
		SoundController.add(horizontalStrut_2);
		
		InstrumentController = Box.createHorizontalBox();
		SoundController.add(InstrumentController);
		
		verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_1.setOpaque(true);
		verticalBox_1.setForeground(new Color(0, 128, 128));
		verticalBox_1.setBackground(new Color(0, 128, 128));
		verticalBox_1.setBorder(new LineBorder(new Color(250, 240, 230), 3));
		InstrumentController.add(verticalBox_1);
		
		CurrentInstrument = new JLabel("Instrument : " +  MIDIController.InstList.get((byte) (currentInstrument-1)));
		CurrentInstrument.setAlignmentX(Component.CENTER_ALIGNMENT);
		CurrentInstrument.setHorizontalTextPosition(SwingConstants.CENTER);
		CurrentInstrument.setHorizontalAlignment(SwingConstants.CENTER);
		verticalBox_1.add(CurrentInstrument);
		CurrentInstrument.setFont(new Font("Hameagel", Font.PLAIN, 24));
		CurrentInstrument.setForeground(new Color(255, 255, 224));
		
		horizontalBox_4 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_4);
		
		slider = new JSlider();
		horizontalBox_4.add(slider);
		slider.setForeground(new Color(255, 0, 0));
		slider.setBackground(new Color(250, 240, 230));
		slider.setValue(30);
		slider.setMinimum(1);
		slider.setMaximum(125);
		slider.setMaximumSize(new Dimension(400, 26));
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinner.setValue(slider.getValue());
				currentInstrument = (int) slider.getValue();
				UpdateInstrument();
			}
		});
		
		
		spinner = new JSpinner();
		horizontalBox_4.add(spinner);
		spinner.setForeground(Color.PINK);
		spinner.setBackground(Color.PINK);
		spinner.setFont(new Font("Hameagel", Font.PLAIN, 20));

		spinner.setModel(new SpinnerNumberModel(31, 1, 125, 1));
		spinner.setMaximumSize(new Dimension(70, 30));
		
		horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setMaximumSize(new Dimension(20, 20));
		SoundController.add(horizontalStrut_3);
		
		TempoController = Box.createVerticalBox();
		TempoController.setBorder(new LineBorder(new Color(250, 240, 230), 4));
		SoundController.add(TempoController);
		TempoController.setOpaque(true);
		TempoController.setBackground(new Color(0, 128, 128));
		
		TempoUp = new JButton("UP");
		TempoUp.setForeground(new Color(0, 0, 128));
		TempoUp.setBackground(new Color(250, 240, 230));
		TempoUp.setFont(new Font("Hameagel", Font.PLAIN, 14));
		TempoUp.setAlignmentX(Component.CENTER_ALIGNMENT);
		TempoUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentTempo < 32){
					currentTempo = currentTempo * 2;
				}
				else{
				}
				UpdateTempo();
			}
		});
		TempoController.add(TempoUp);
		
		TempoDown = new JButton("DOWN");
		TempoDown.setForeground(new Color(0, 0, 128));
		TempoDown.setBackground(new Color(250, 240, 230));
		TempoDown.setFont(new Font("Hameagel", Font.PLAIN, 14));
		TempoDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		TempoDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentTempo > 2){
					currentTempo = currentTempo/2;
				}
				else{	
				}
				UpdateTempo();
			}
		});
		
		TempoBox = Box.createHorizontalBox();
		TempoBox.setBackground(Color.WHITE);
		TempoController.add(TempoBox);
		
		CurrentTempo = new JLabel("TEMPO : 1/" + Integer.toString(currentTempo));
		CurrentTempo.setBackground(new Color(192, 192, 192));
		CurrentTempo.setFont(new Font("Hameagel", Font.PLAIN, 20));
		CurrentTempo.setAlignmentX(Component.CENTER_ALIGNMENT);
		CurrentTempo.setForeground(new Color(255, 255, 224));
		CurrentTempo.setHorizontalAlignment(SwingConstants.CENTER);
		TempoBox.add(CurrentTempo);
		TempoController.add(TempoDown);
		
		ScaleBox = Box.createVerticalBox();
		ScaleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		ScaleBox.setBackground(new Color(0, 128, 128));
		ScaleBox.setOpaque(true);
		verticalBox.add(ScaleBox);
		
		verticalBox_3 = Box.createVerticalBox();
		verticalBox_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_3.setBorder(new LineBorder(new Color(250, 240, 230), 4, true));
		ScaleBox.add(verticalBox_3);
		
		ScaleLabel = Box.createHorizontalBox();
		verticalBox_3.add(ScaleLabel);
		
		scaleLabel = new JLabel("Scale : Chromatic");
		scaleLabel.setOpaque(true);
		scaleLabel.setBorder(null);
		scaleLabel.setBackground(new Color(0, 128, 128));
		ScaleLabel.add(scaleLabel);
		scaleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scaleLabel.setForeground(new Color(255, 255, 224));
		scaleLabel.setFont(new Font("Hameagel", Font.PLAIN, 24));
		
		ScaleController = Box.createHorizontalBox();
		verticalBox_3.add(ScaleController);
		ScaleController.setBorder(new LineBorder(new Color(250, 240, 230), 3, true));
		
		prevScale = new JButton("Minor Scale");
		prevScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//currentScale = Scales.minor;
				//currentColors = ColorSets.minor;
				scaleLabel.setText("Scale : Minor");
				for(int i = 0; i < 13; i++){
					EnableButtons(i);
				}
				ClearAll();
				DisableButtons(1);
				DisableButtons(4);
				DisableButtons(6);
				DisableButtons(9);
				DisableButtons(11);
			}
		});
		prevScale.setForeground(new Color(0, 0, 128));
		prevScale.setBackground(new Color(250, 240, 230));
		prevScale.setFont(new Font("Hameagel", Font.PLAIN, 16));
		ScaleController.add(prevScale);
		
		JButton nextScale = new JButton("Major Scale");
		nextScale.setForeground(new Color(0, 0, 128));
		nextScale.setBackground(new Color(250, 240, 230));
		nextScale.setFont(new Font("Hameagel", Font.PLAIN, 16));
		nextScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//currentScale = Scales.major_Int;
				//currentColors = ColorSets.major;
				scaleLabel.setText("Scale : Major");
				for(int i = 0; i < 13; i++){
					EnableButtons(i);
				}
				ClearAll();
				DisableButtons(1);
				DisableButtons(3);
				DisableButtons(6);
				DisableButtons(8);
				DisableButtons(10);
				
				//RefreshLabels();
			}
		});
		
		btnChromaticScale = new JButton("Chromatic Scale");
		btnChromaticScale.setForeground(new Color(0, 0, 128));
		btnChromaticScale.setBackground(new Color(250, 240, 230));
		btnChromaticScale.setFont(new Font("Hameagel", Font.PLAIN, 16));
		btnChromaticScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//currentScale = Scales.pentatonic_Int;
				//currentColors = ColorSets.synesposer;
				scaleLabel.setText("Scale : Chromatic");
				for(int i = 0; i < 13; i++){
					EnableButtons(i);
				}
				ClearAll();
			}
		});
		
		PentScale = new JButton("Pentatonic Scale");
		PentScale.setForeground(new Color(0, 0, 128));
		PentScale.setBackground(new Color(250, 240, 230));
		PentScale.setFont(new Font("Hameagel", Font.PLAIN, 16));
		PentScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scaleLabel.setText("Scale : Pentatonic");
				for(int i = 0; i < 13; i++){
					EnableButtons(i);
				}
				ClearAll();
				DisableButtons(1);
				DisableButtons(3);
				DisableButtons(5);
				DisableButtons(6);
				DisableButtons(8);
				DisableButtons(10);
				DisableButtons(11);
			}
		});
		ScaleController.add(PentScale);
		ScaleController.add(btnChromaticScale);
		ScaleController.add(nextScale);
		
		verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_2.setBorder(new LineBorder(new Color(250, 240, 230), 4, true));
		ScaleBox.add(verticalBox_2);
		
		volLabel = new JLabel("Volume");
		verticalBox_2.add(volLabel);
		volLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		volLabel.setOpaque(true);
		volLabel.setHorizontalAlignment(SwingConstants.CENTER);
		volLabel.setForeground(new Color(255, 255, 224));
		volLabel.setFont(new Font("Hameagel", Font.PLAIN, 24));
		volLabel.setBorder(null);
		volLabel.setBackground(new Color(0, 128, 128));
		
		volSlider = new JSlider();
		volSlider.setBackground(new Color(250, 240, 230));
		verticalBox_2.add(volSlider);
		volSlider.setForeground(new Color(0, 0, 128));
		volSlider.setPaintTicks(true);
		volSlider.setMaximumSize(new Dimension(500, 26));
		volSlider.setMinimum(1);
		volSlider.setValue(100);
		volSlider.setMaximum(127);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				slider.setValue((int) spinner.getValue());
				currentInstrument = (int) spinner.getValue();
				UpdateInstrument();
			}
		});
		NoteRack.setFont(new Font("Dialog", Font.PLAIN, 10));
		NoteRack.setBorder(new LineBorder(new Color(250, 240, 230), 8, true));
		NoteOverlay = Box.createVerticalBox();
		NoteOverlay.setSize(NoteRack.getSize());
		
		Stage.add(NoteOverlay);
		NoteOverlay.setAlignmentY(Component.TOP_ALIGNMENT);
		Stage.add(NoteRack);
		NoteRack.setAlignmentY(Component.TOP_ALIGNMENT);
		//
		// C2 Sequencer
		//
		C2Seq = Box.createHorizontalBox();
		C2Seq.setBackground(Color.MAGENTA);
		C2Seq.setForeground(Color.MAGENTA);
		NoteRack.add(C2Seq);
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(Color.MAGENTA);
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(12,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(12,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("c");
			bChart[12][j] = temp;
			C2Seq.add(temp);
		}
		//
		// B Sequencer
		//
		BSeq = Box.createHorizontalBox();
		BSeq.setBackground(Color.MAGENTA);
		BSeq.setForeground(Color.MAGENTA);
		NoteRack.add(BSeq);
		
		lblB = new JLabel("B");
		lblB.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblB.setForeground(Color.RED);
		lblB.setBackground(Color.MAGENTA);
		//BSeq.add(lblB);
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(Color.RED);
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(11,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(11,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("b");
			bChart[11][j] = temp;
			BSeq.add(temp);
		}
		//
		// A#Sequencer
		//
		AsSeq = Box.createHorizontalBox();
		AsSeq.setBackground(Color.MAGENTA);
		AsSeq.setForeground(Color.MAGENTA);
		NoteRack.add(AsSeq);
		
		lblAS = new JLabel("A#");
		lblAS.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAS.setForeground(new Color(255, 69, 0));
		lblAS.setBackground(Color.MAGENTA);
		
		//AsSeq.add(lblAS);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(255, 69, 0));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(10,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(10,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("a");
			bChart[10][j] = temp;
			AsSeq.add(temp);
		}
		//
		// A Sequencer
		//
		ASeq = Box.createHorizontalBox();
		NoteRack.add(ASeq);
		
		lblA = new JLabel("a");
		lblA.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblA.setForeground(new Color(255, 140, 0));
		lblA.setBackground(Color.MAGENTA);
		//ASeq.add(lblA);

		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(255, 140, 0));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(9,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(9,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("a");
			bChart[9][j] = temp;
			ASeq.add(temp);
		}
		//
		// G Sequencer
		//
		GsSeq = Box.createHorizontalBox();
		NoteRack.add(GsSeq);
		
		lblGS = new JLabel("G#");
		lblGS.setForeground(new Color(255, 215, 0));
		lblGS.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGS.setBackground(Color.MAGENTA);
		//GsSeq.add(lblGS);

		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(255, 215, 0));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(8,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(8,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("g");
			bChart[8][j] = temp;
			GsSeq.add(temp);
		}
		//
		// G Sequencer
		//
		GSeq = Box.createHorizontalBox();
		NoteRack.add(GSeq);

		lblG = new JLabel("G");
		lblG.setForeground(Color.YELLOW);
		lblG.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblG.setBackground(Color.MAGENTA);
		//GSeq.add(lblG);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(Color.YELLOW);
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(7,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(7,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("g");
			bChart[7][j] = temp;
			GSeq.add(temp);
		}
		//
		// F# Sequencer
		//
		FsSeq = Box.createHorizontalBox();
		NoteRack.add(FsSeq);
		
		lblFS = new JLabel("F#");
		lblFS.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFS.setForeground(new Color(0, 255, 0));
		lblFS.setBackground(Color.MAGENTA);
		//FsSeq.add(lblFS);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(0, 255, 0));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(6,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(6,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("f ");
			bChart[6][j] = temp;
			FsSeq.add(temp);
		}
		//
		// F Sequencer
		//
		FSeq = Box.createHorizontalBox();
		NoteRack.add(FSeq);
		
		lblF = new JLabel("F");
		lblF.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblF.setForeground(new Color(34, 139, 34));
		lblF.setBackground(Color.MAGENTA);
		//FSeq.add(lblF);

		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(34, 139, 34));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(5,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(5,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("f ");
			bChart[5][j] = temp;
			FSeq.add(temp);
		}
		//
		// E Sequencer
		//
		ESeq = Box.createHorizontalBox();
		NoteRack.add(ESeq);
		
		lblE = new JLabel("E");
		lblE.setForeground(Color.CYAN);
		lblE.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblE.setBackground(Color.MAGENTA);
		//ESeq.add(lblE);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(Color.CYAN);
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(4,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(4,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("e");
			bChart[4][j] = temp;
			ESeq.add(temp);
		}
		//
		// D# Sequencer
		//
		DsSeq = Box.createHorizontalBox();
		NoteRack.add(DsSeq);
		
		lblDS = new JLabel("D#");
		lblDS.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDS.setForeground(new Color(0, 0, 255));
		lblDS.setBackground(Color.MAGENTA);
		//DsSeq.add(lblDS);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(0, 0, 255));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(3,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(3,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("d");
			bChart[3][j] = temp;
			DsSeq.add(temp);
		}
		//
		// D Sequencer
		//
		DSeq = Box.createHorizontalBox();
		NoteRack.add(DSeq);
		
		lblD = new JLabel("D");
		lblD.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblD.setForeground(new Color(138, 43, 226));
		lblD.setBackground(Color.MAGENTA);
		//DSeq.add(lblD);

		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(138, 43, 226));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(2,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(2,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("d");
			bChart[2][j] = temp;
			DSeq.add(temp);
		}
		//
		// C# Sequencer
		//
		CsSeq = Box.createHorizontalBox();
		NoteRack.add(CsSeq);
		
		lblCS = new JLabel("C#");
		lblCS.setForeground(new Color(123, 104, 238));
		lblCS.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCS.setBackground(Color.MAGENTA);
		//CsSeq.add(lblCS);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(new Color(123, 104, 238));
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(1,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(1,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("c");
			bChart[1][j] = temp;
			CsSeq.add(temp);
		}
		//
		// C Sequencer
		//		
		CSeq = Box.createHorizontalBox();
		NoteRack.add(CSeq);
		
		lblC = new JLabel("C");
		lblC.setForeground(Color.MAGENTA);
		lblC.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblC.setBackground(Color.MAGENTA);
		//CSeq.add(lblC);
		
		for(int i = 0; i < chart[0].length; i++){
			JButton temp = new JButton(" ");
			temp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp.setForeground(Color.MAGENTA);
			temp.setBackground(Color.DARK_GRAY);
			final int j = i;
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(mouseOverField){
						pressButton(0,j);
					}
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					mouseOverField = true;
					pressButton(0,j);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseOverField = false;
				}
			});
			temp.setText("c");
			bChart[0][j] = temp;
			CSeq.add(temp);
		}
		
		horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setBorder(new LineBorder(new Color(0, 128, 128), 6, true));
		Stage.add(horizontalBox_2);
		
		canvas = new Canvas();
		canvas.setMaximumSize(new Dimension(1600, 900));
		horizontalBox_2.add(canvas);
		canvas.setBackground(Color.BLACK);;
		
		DEBUG = Box.createVerticalBox();
		DEBUG.setOpaque(true);
		DEBUG.setBackground(new Color(250, 240, 230));
		frmSynesposer.getContentPane().add(DEBUG, BorderLayout.NORTH);
		
		Title = new JLabel("Synesposer !");
		Title.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		Title.setAlignmentX(Component.CENTER_ALIGNMENT);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		DEBUG.add(Title);
		Title.setForeground(new Color(255, 0, 255));
		Title.setBackground(Color.CYAN);
		Title.setFont(new Font("Hameagel", Font.PLAIN, 37));
		
		JButton Debug1 = new JButton("DEBUG");
		Debug1.setVisible(false);
		Debug1.setBackground(new Color(250, 240, 230));
		Debug1.setForeground(new Color(0, 0, 128));
		Debug1.setAlignmentX(Component.CENTER_ALIGNMENT);
		DEBUG.add(Debug1);
		Debug1.setFont(new Font("Hameagel", Font.PLAIN, 13));
		Debug1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DrawMarkers((Graphics2D) NoteRack.getGraphics());
				
		/*		System.out.println("--------------------------------------");
				for(int i = 11; i >= 0; i--){
					for(int j = 0; j < 8; j++){
						System.out.print("-" + chart[i][j]);
					}
					System.out.println();
				*/
			}
		});
		
		ExportButton = new JButton("Export Sequence as MIDI");
		ExportButton.setForeground(new Color(0, 0, 128));
		ExportButton.setBackground(new Color(250, 240, 230));
		ExportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		DEBUG.add(ExportButton);
		ExportButton.setFont(new Font("Hameagel", Font.PLAIN, 14));
		ExportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MidiFileManager.savePatternToMidi(new Pattern(FugueBuilder.Sequence2Voice(Export(),1)), new File("MIDI EXPORT"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		Debugging = Box.createVerticalBox();
		Debugging.setAlignmentY(Component.TOP_ALIGNMENT);
		DEBUG.add(Debugging);
		
		PlaybackControl = Box.createVerticalBox();
		PlaybackControl.setBackground(new Color(250, 240, 230));
		frmSynesposer.getContentPane().add(PlaybackControl, BorderLayout.SOUTH);
		
		horizontalBox_3 = Box.createHorizontalBox();
		PlaybackControl.add(horizontalBox_3);
		
		PaintButton = new JToggleButton("Grid");
		horizontalBox_3.add(PaintButton);
		PaintButton.setBackground(new Color(250, 240, 230));
		PaintButton.setForeground(new Color(0, 0, 128));
		PaintButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		
		horizontalStrut_5 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_5);
		
		tglbtnLetters = new JToggleButton("Letters");
		horizontalBox_3.add(tglbtnLetters);
		tglbtnLetters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(showLetters){
					for(JButton [] B : bChart){
						for(JButton b : B){
							b.setText("");
						}
					}
					showLetters = false;
				}
				else{
					for(int i = 0; i < 13; i++){
						for(JButton b : bChart[i]){
							b.setText(Scales.Default[i]);
						}
					}
					showLetters = true;
				}
				DrawMarkers((Graphics2D) NoteRack.getGraphics());
			}
		});
		tglbtnLetters.setForeground(new Color(0, 0, 128));
		tglbtnLetters.setFont(new Font("Hameagel", Font.PLAIN, 20));
		tglbtnLetters.setBackground(new Color(250, 240, 230));
		
		horizontalStrut_6 = Box.createHorizontalStrut(20);
		horizontalStrut_6.setMaximumSize(new Dimension(75, 32767));
		horizontalBox_3.add(horizontalStrut_6);
		
		ClearButton = new JButton("Clear");
		horizontalBox_3.add(ClearButton);
		ClearButton.setForeground(new Color(128, 0, 0));
		ClearButton.setBackground(new Color(250, 240, 230));
		ClearButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearCanvas();
				ClearAll();
			} 
		});
		PaintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mouseOverField = !mouseOverField;
				for(JButton[] J : bChart){
						for(JButton j : J){
							j.setBorderPainted(!j.isBorderPainted());
						}
					}
			}
		});
		
		verticalStrut = Box.createVerticalStrut(20);
		PlaybackControl.add(verticalStrut);
		verticalStrut.setBackground(new Color(0, 128, 128));
		verticalStrut.setMaximumSize(new Dimension(32767, 10));
		
		//
		// Playback Controls
		//
		horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new LineBorder(new Color(250, 240, 230), 4, true));
		horizontalBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		PlaybackControl.add(horizontalBox);
		
		PlayButton = new JButton("Play");
		PlayButton.setForeground(new Color(0, 0, 128));
		PlayButton.setBackground(new Color(250, 240, 230));
		PlayButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearCanvas();
				new Thread(() -> {
					DrawChart((Graphics2D) canvas.getGraphics());
					if(smoothPlay)
						MIDIplayer.playVoice(FugueBuilder.Sequence2SmoothVoice(Export(),1,currentScale));
					else
						MIDIplayer.playVoice(FugueBuilder.Sequence2Voice(Export(),1,currentScale));
				}).start();
				DrawChart((Graphics2D) canvas.getGraphics());
			}
		});
		horizontalBox.add(PlayButton);
		
		LoopButton = new JButton("Loop");
		LoopButton.setForeground(new Color(0, 0, 128));
		LoopButton.setBackground(new Color(250, 240, 230));
		LoopButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		LoopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearCanvas();
				new Thread(() -> {
						DrawChart((Graphics2D) canvas.getGraphics());
						if(smoothPlay)
							MIDIplayer.playVoice(FugueBuilder.Sequence2LoopedSmoothVoice(Export(),1,4));
						else
							MIDIplayer.playVoice(FugueBuilder.Sequence2LoopedVoice(Export(),1,4));
						}).start();
			}
		});
		
		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		horizontalBox.add(LoopButton);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
		StopButton = new JButton("Stop");
		StopButton.setForeground(new Color(0, 0, 128));
		StopButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		StopButton.setBackground(new Color(250, 240, 230));
		StopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MIDIplayer.playVoice("");
			}
		});
		horizontalBox.add(StopButton);
		
		horizontalStrut_4 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_4);
		
		smoothButton = new JRadioButton("Smooth");
		smoothButton.setForeground(new Color(0, 0, 128));
		smoothButton.setBackground(new Color(250, 240, 230));
		smoothButton.setFont(new Font("Hameagel", Font.PLAIN, 20));
		smoothButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				smoothPlay = !smoothPlay;
			}
		});
		horizontalBox.add(smoothButton);
		
		labels[0] = lblC;
		labels[1] = lblCS;
		labels[2] = lblD;
		labels[3] = lblDS;
		labels[4] = lblE;
		labels[5] = lblF;
		labels[6] = lblFS;
		labels[7] = lblG;
		labels[8] = lblGS;
		labels[9] = lblA;
		labels[10] = lblAS;
		labels[11] = lblB;
		
	}

}
