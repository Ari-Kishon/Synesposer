package p1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;

public class UiTest extends JFrame {

	private JPanel Chart;
	/**
	 * @wbp.nonvisual location=181,229
	 */
	private final SynthGen synthGen = new SynthGen();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiTest frame = new UiTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UiTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 441);
		Chart = new JPanel();
		Chart.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Chart);
		Chart.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(416, 24, 214, 249);
		Chart.add(panel);
	}
}
