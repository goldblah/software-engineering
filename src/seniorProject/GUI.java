package seniorProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUI extends JFrame {
	
	public GUI(){
		new JFrame();
		setLayout(new FlowLayout());
		setBounds(0,0,800,650);
		GUIPanel i = new GUIPanel();
		add(i);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*@Override
	public void paintComponents(Graphics g){
		super.paint(g);
		setForeground(Color.BLACK);
	}*/

	public static void main(String[] args) throws FileNotFoundException {
		GUI g = new GUI();
	
	}

}
