package seniorProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUIPanel extends JPanel implements ActionListener{
	boolean correctPassword;
	GroupLayout layout;
	JPanel startPanel;
	JPanel newUserPanel;
	JPanel schedulePanel;
	boolean newUser;
	boolean backButton;
	String userPassword;
	String user;
	String studentStartSemester;
	int studentCurrentSemester;
	String studentName;
	ArrayList<String> majorArray;
	ArrayList<String> minorArray;
	ArrayList<String> classesTaken;
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(correctPassword == false && newUser == false && backButton == true){
			startPanel.setFocusable(true);
			startPanel.setVisible(true);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(false);
			schedulePanel.setFocusable(false);
			add(startPanel, BorderLayout.SOUTH);
			revalidate();
		} else if(correctPassword == true){
			startPanel.setFocusable(false);
			startPanel.setVisible(false);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(true);
			schedulePanel.setFocusable(true);
			add(schedulePanel, BorderLayout.SOUTH);
			revalidate();
		} else if (newUser == true){
			//remove(startPanel);
			newUserPanel = newUserScreen();
			newUserPanel.setFocusable(true);
			newUserPanel.setVisible(true);
			startPanel.setVisible(false);
			startPanel.setFocusable(false);
			add(newUserPanel, BorderLayout.SOUTH);
			revalidate();
		}

	}//end paintComponent
	
	

	public GUIPanel(){
		correctPassword = false;
		newUser = false;
		backButton = false;
		setPreferredSize(new Dimension(800,625));
		setLayout(new BorderLayout());
		startPanel = startScreen();
		newUserPanel = newUserScreen();
		schedulePanel = scheduleScreen();
		//add(newUserPanel, BorderLayout.SOUTH);
		add(startPanel, BorderLayout.SOUTH);
		//add(schedulePanel, BorderLayout.SOUTH);
	}
	
	private JPanel scheduleScreen(){
		JPanel scheduleScreen = new JPanel();
		scheduleScreen.setPreferredSize(new Dimension(800,100));
		JButton generateSchedule = new JButton("Generate Schedule");
		generateSchedule.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//generate schedule
				invalidate();
				revalidate();
				repaint();
			}
		});
		JButton optionalCourse = new JButton("Choose Optional Course");
		optionalCourse.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//choose optional course
				invalidate();
				revalidate();
				repaint();
			}
		});
		JButton logout = new JButton("Logout");
		logout.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//logout and save
				backButton = true;
				correctPassword = false;
				invalidate();
				revalidate();
				repaint();
			}
		});
		scheduleScreen.add(generateSchedule);
		scheduleScreen.add(optionalCourse);
		scheduleScreen.add(logout);
		return scheduleScreen;
	}
	
	private JPanel newUserScreen(){
		JPanel newUserP = new JPanel();
		newUserP.setLayout(new GridLayout(0,2));
		newUserP.setPreferredSize(new Dimension(800,375));
		
		JLabel name = new JLabel("Name:", SwingConstants.RIGHT);
		JTextField nameField = new JTextField(20);
		nameField.setBounds(40,40,200,40);
		newUserP.add(name);
		newUserP.add(nameField);
		
		JLabel ID = new JLabel ("ID:", SwingConstants.RIGHT);
		JTextField IDField = new JTextField(20);
		newUserP.add(ID);
		newUserP.add(IDField);
		
		JLabel password = new JLabel("Password", SwingConstants.RIGHT);
		JTextField passwordField = new JTextField(20);
		newUserP.add(password);
		newUserP.add(passwordField);
		
		JLabel major = new JLabel ("Major:", SwingConstants.RIGHT);
		JTextField majorField = new JTextField(20);
		newUserP.add(major);
		newUserP.add(majorField);
		
		JLabel minor = new JLabel ("Minor:", SwingConstants.RIGHT);
		JTextField minorField = new JTextField(20);
		newUserP.add(minor);
		newUserP.add(minorField);
		
		JLabel startSemester = new JLabel("Semester You Started:", SwingConstants.RIGHT);
		JTextField startSemesterField = new JTextField(20);
		newUserP.add(startSemester);
		newUserP.add(startSemesterField);
		
		JLabel currentSemester = new JLabel("What semester are you currently on?", SwingConstants.RIGHT);
		JTextField currentSemesterField = new JTextField(20);
		newUserP.add(currentSemester);
		newUserP.add(currentSemesterField);
		
		JLabel classes = new JLabel("Please enter the courses you have taken.", SwingConstants.RIGHT);
		JTextArea classesField = new JTextArea();
		newUserP.add(classes);
		newUserP.add(classesField);
		
		JButton enter = new JButton("Save Information");
		enter.setPreferredSize(new Dimension(200,40));
		enter.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//send to output class to write file
				studentName = nameField.getText();
				user = IDField.getText();
				userPassword = passwordField.getText();
				studentStartSemester = startSemesterField.getText();
				studentCurrentSemester = Integer.parseInt(currentSemesterField.getText().trim());
				newUser = false;
				backButton = true;
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(200,400));
		back.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//send back to main screen
				nameField.setText("");
				IDField.setText("");
				passwordField.setText("");
				majorField.setText("");
				minorField.setText("");
				startSemesterField.setText("");
				currentSemesterField.setText("");
				classesField.setText("");
				newUser = false;
				backButton = true;
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		newUserP.add(enter);
		newUserP.add(back);
		return newUserP;
	}

	private JPanel startScreen(){
		JPanel temp = new JPanel();
		temp.setPreferredSize(new Dimension(800,200));
		JLabel username = new JLabel("Username:");
		JTextField user = new JTextField(20);
		JLabel password = new JLabel ("Password:");
		JTextField pass = new JTextField(20);
		temp.add(username);
		temp.add(user);
		temp.add(password);
		temp.add(pass);

		JButton ru = new JButton("Returning User Log In");
		ru.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//go to log in
				user.setEditable(false);
				pass.setEditable(false);
				String givenUserName = user.getText();
				String givenPassword = pass.getText();
				String actualPassword = null;
				try {
					actualPassword = Input.getPassword(givenUserName);
				} catch (IOException e2) {
					//ignore this
				}
				if(givenPassword.equals(actualPassword)){
					correctPassword = true;
				}

				if(correctPassword){
					invalidate();
					revalidate();
					repaint();
				}
			}
		});
		ru.setPreferredSize(new Dimension(200,40));

		JButton nu = new JButton("Create New User");
		nu.setPreferredSize(new Dimension(200,40));
		nu.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newUser = true;
				invalidate();
				revalidate();
				repaint();
			}
		});

		temp.add(ru);
		temp.add(nu);
		return temp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
