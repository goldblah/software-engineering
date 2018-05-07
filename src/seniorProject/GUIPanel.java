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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUIPanel extends JPanel implements ActionListener{
	boolean correctPassword;
	GroupLayout layout;
	JPanel startPanel;
	JPanel newUserPanel;
	JPanel schedulePanel;
	JPanel addNewPanel;
	boolean newUser;
	boolean backButton;
	boolean addNewClass;
	String userPassword;
	String user;
	String studentStartSemester;
	int studentCurrentSemester;
	String studentName;
	ArrayList<String> majorArray;
	ArrayList<String> minorArray;
	ArrayList<String> classesTaken;
	Student s;
	//String[] major = {"Accounting", "Agricultural Business", "Agricultural Education", "Agricultural Sciences", "Animal and Dairy Science: Production and Management", "Animal and Dairy Science: Pre-Vet", "BAAS in Animal Science", "BS in Archaeology", "BA in Archaeology", "Forensic Anthropology", "Graphic Design", "Visual Arts", "Economics and Finance", "BS in Aviation Science", "BAAS in Aviation Science", "Biochemistry " };


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
			addNewPanel.setVisible(false);
			addNewPanel.setFocusable(false);
			add(startPanel, BorderLayout.SOUTH);
			revalidate();
		} else if(correctPassword == true && addNewClass == false){
			startPanel.setFocusable(false);
			startPanel.setVisible(false);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(true);
			schedulePanel.setFocusable(true);
			addNewPanel.setVisible(false);
			addNewPanel.setFocusable(false);
			add(schedulePanel, BorderLayout.SOUTH);
			revalidate();
		} else if (newUser == true){
			newUserPanel = newUserScreen();
			newUserPanel.setFocusable(true);
			newUserPanel.setVisible(true);
			startPanel.setVisible(false);
			startPanel.setFocusable(false);
			addNewPanel.setVisible(false);
			addNewPanel.setFocusable(false);
			add(newUserPanel, BorderLayout.SOUTH);
			revalidate();
		} else if(correctPassword == true && addNewClass == true){
			startPanel.setFocusable(false);
			startPanel.setVisible(false);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(false);
			schedulePanel.setFocusable(false);
			addNewPanel.setVisible(true);
			addNewPanel.setFocusable(true);
			add(addNewPanel, BorderLayout.SOUTH);
			revalidate();
		}

	}//end paintComponent



	public GUIPanel() throws FileNotFoundException, IOException{
		s = new Student();
		correctPassword = false;
		newUser = false;
		backButton = false;
		setPreferredSize(new Dimension(800,625));
		setLayout(new BorderLayout());
		startPanel = startScreen();
		newUserPanel = newUserScreen();
		schedulePanel = scheduleScreen();
		addNewPanel = addNewClassesScreen();
		//add(newUserPanel, BorderLayout.SOUTH);
		add(startPanel, BorderLayout.SOUTH);
		//add(schedulePanel, BorderLayout.SOUTH);
		//add(addNewPanel, BorderLayout.SOUTH);
		startPanel.setFocusable(true);
		startPanel.setVisible(true);
		majorArray = new ArrayList<>();
		minorArray = new ArrayList<>();
		classesTaken = new ArrayList<>();
	}
	
	private JPanel addNewClassesScreen(){
		JPanel addNewClasses = new JPanel();
		JPanel buttonPanel = new JPanel();
		addNewClasses.setLayout(new BorderLayout());
		addNewClasses.setPreferredSize(new Dimension(800,300));
		
		JLabel classes = new JLabel("Please enter any new classes you have taken:", SwingConstants.LEFT);
		JTextArea classesField = new JTextArea();
		classesField.setPreferredSize(new Dimension(400, 200));
		classesField.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(classesField);
		
		addNewClasses.add(classes, BorderLayout.NORTH);
		addNewClasses.add(scrollPane, BorderLayout.CENTER)
		;
		JButton enter = new JButton("Save Information");
		enter.setPreferredSize(new Dimension(175, 20));
		enter.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//write the info to the bottom of their file
				addNewClass = false;
				
				String temp = classesField.getText();
				if(temp.contains(", ")){
					String[] pieces = temp.split(", ");
					for (String s: pieces){
						classesTaken.add(s);
					}
				} else {
					classesTaken.add(temp);
				}
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(175, 20));
		back.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//go back to returning user
				addNewClass = false;
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		buttonPanel.add(enter);
		buttonPanel.add(back);
		addNewClasses.add(buttonPanel, BorderLayout.SOUTH);
		return addNewClasses;
	}

	private JPanel scheduleScreen(){
		JPanel scheduleScreen = new JPanel();
		scheduleScreen.setPreferredSize(new Dimension(800,100));
		
		JButton generateSchedule = new JButton("Generate Schedule");
		generateSchedule.setPreferredSize(new Dimension(175, 20));
		generateSchedule.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//generate schedule
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		JButton optionalCourse = new JButton("Choose Optional Course");
		optionalCourse.setPreferredSize(new Dimension(175, 20));
		optionalCourse.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//choose optional course
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		JButton addNewClasses = new JButton("Add New Classes");
		addNewClasses.setPreferredSize(new Dimension(175, 20));
		addNewClasses.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add new classes to the text file
				addNewClass = true;
				invalidate();
				revalidate();
				repaint();
			}
		});
		
		JButton logout = new JButton("Logout");
		logout.setPreferredSize(new Dimension(175, 20));
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
		scheduleScreen.add(addNewClasses);
		scheduleScreen.add(logout);
		return scheduleScreen;
	}

	private JPanel newUserScreen(){
		JPanel newUserP = new JPanel();
		newUserP.setLayout(new GridLayout(0,2));
		newUserP.setPreferredSize(new Dimension(800,375));

		JLabel name = new JLabel("Name:", SwingConstants.RIGHT);
		JTextField nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(100, 20));
		//nameField.setBounds(40,40,200,40);
		newUserP.add(name);
		newUserP.add(nameField);

		JLabel ID = new JLabel ("ID:", SwingConstants.RIGHT);
		JTextField IDField = new JTextField();
		IDField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(ID);
		newUserP.add(IDField);

		JLabel password = new JLabel("Password:", SwingConstants.RIGHT);
		JPasswordField passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(password);
		newUserP.add(passwordField);
	

		JLabel verifyPassword = new JLabel("Verify Password:", SwingConstants.RIGHT);
		JPasswordField verifyPasswordField = new JPasswordField();
		verifyPasswordField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(verifyPassword);
		newUserP.add(verifyPasswordField);

		JLabel major = new JLabel ("Major:", SwingConstants.RIGHT);
		JTextField majorField = new JTextField();
		majorField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(major);
		newUserP.add(majorField);

		JLabel minor = new JLabel ("Minor:", SwingConstants.RIGHT);
		JTextField minorField = new JTextField();
		minorField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(minor);
		newUserP.add(minorField);

		JLabel startSemester = new JLabel("Semester You Started:", SwingConstants.RIGHT);
		JTextField startSemesterField = new JTextField();
		startSemesterField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(startSemester);
		newUserP.add(startSemesterField);

		JLabel currentSemester = new JLabel("What semester are you currently on?", SwingConstants.RIGHT);
		JTextField currentSemesterField = new JTextField();
		currentSemesterField.setPreferredSize(new Dimension(100, 20));
		newUserP.add(currentSemester);
		newUserP.add(currentSemesterField);

		JLabel classes = new JLabel("Please enter the courses you have taken.", SwingConstants.RIGHT);
		JTextArea classesField = new JTextArea();
		classesField.setPreferredSize(new Dimension(400, 75));
		classesField.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(classesField);
		newUserP.add(classes);
		newUserP.add(scrollPane);

		JButton enter = new JButton("Save Information");
		enter.setPreferredSize(new Dimension(200,40));
		enter.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//send to output class to write file
				userPassword = new String(passwordField.getPassword());
				String verifyPassword = new String(verifyPasswordField.getPassword());

				while(nameField.getText().matches(".*\\d+.*") == true || nameField.getText().length() < 2){
					String tempAnswer = JOptionPane.showInputDialog("Name is not in the correct format,\nplease try again.");
					nameField.setText(tempAnswer);
				} while (IDField.getText().matches(".*\\d+.*") == false || IDField.getText().length() != 9){
					String tempAnswer = JOptionPane.showInputDialog("Id Number is not in the correct format,\nplease try again.");
					IDField.setText(tempAnswer);
				} while (majorField.getText().matches("[a-zA-Z]+") == false || majorField.getText().length() < 2){
					String tempAnswer = JOptionPane.showInputDialog("The major you listed is not in the correct format,\nplease try again.");
					majorField.setText(tempAnswer);
				} while (minorField.getText().matches(".*\\d+.*") == true || minorField.getText().length() < 2){
					String tempAnswer = JOptionPane.showInputDialog("The minor you listed is not in the correct format,\nplease try again.");
					minorField.setText(tempAnswer);
				}while (startSemesterField.getText().length() < 4){
					String tempAnswer = JOptionPane.showInputDialog("The semester you listed is not in the correct format,\nplease try again.");
					startSemesterField.setText(tempAnswer);
				}
				if(!userPassword.equals(verifyPassword)){
					String errorMessage = "Passwords Do Not Match\n Please Try Again";
					JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Password",
							JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
					verifyPasswordField.setText("");
				} else {
					//check to make sure all information is the correct character type

					String confirmMessage = "Does this look correct?\nName: " + nameField.getText() + 
							"\nUser ID: " + IDField.getText() + "\nSemester you started: " + startSemesterField.getText() +
							"\nYour current semester number: " + currentSemesterField.getText() + "\nYour major: " + 
							majorField .getText() + "\nYour minor: " + minorField.getText() + "\nClasses you have already taken: "
							+ classesField.getText() + "\nIf you select 'No', your responses will be deleted.";
					int answer = JOptionPane.showConfirmDialog(new JFrame(), confirmMessage, "Confirm",JOptionPane.YES_NO_OPTION);
					if(JOptionPane.YES_OPTION == answer){
						studentName = nameField.getText();
						user = IDField.getText();
						studentStartSemester = startSemesterField.getText();
						if (!currentSemesterField.getText().equals("")) {
							studentCurrentSemester = Integer.parseInt(currentSemesterField.getText().trim());
						}

						//get courses entered by user
						String temp = classesField.getText();
						System.out.println(temp);
						if(temp.contains(", ")){
							String[] pieces = temp.split(", ");
							for (String s: pieces){
								System.out.println(s);
								classesTaken.add(s);
							}
						} else {
							classesTaken.add(temp);
						}

						//get student major
						temp = majorField.getText();
						if(temp.contains(", ")){
							String[] pieces = temp.split(", ");
							for (String s: pieces){
								majorArray.add(s);
							}
						} else {
							majorArray.add(temp);
						}

						//get student minor
						temp = minorField.getText();
						if(temp.contains(", ")){
							String[] pieces = temp.split(", ");
							for (String s: pieces){
								minorArray.add(s);
							}
						} else {
							minorArray.add(temp);
						}
						try {
							s.writeNewStudent(studentName, user, userPassword, majorArray, minorArray, 
									studentStartSemester, studentCurrentSemester, classesTaken);
							s.retrieveInput(user);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						newUser = false;
						correctPassword = true;
						invalidate();
						revalidate();
						repaint();
					} else {
						nameField.setText("");
						IDField.setText("");
						startSemesterField.setText("");
						currentSemesterField.setText("");
						minorField.setText("");
						majorField.setText("");
						classesField.setText("");
						verifyPasswordField.setText("");
						passwordField.setText("");
					}

				}
			}
		});

		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(200,40));
		back.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//send back to main screen
				nameField.setText("");
				IDField.setText("");
				passwordField.setText("");
				verifyPasswordField.setText("");
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
		JPasswordField pass = new JPasswordField(20);

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
				
				while (user.getText().trim().matches(".*\\d+.*") == false || user.getText().trim().length() != 9){
					String tempAnswer = JOptionPane.showInputDialog("Id Number is not in the correct format,\nplease try again.");
					user.setText(tempAnswer);
				}
				String givenUserName = user.getText().trim();
				
				String givenPassword = new String(pass.getPassword());
				String actualPassword = null;
				String fileName = givenUserName + ".txt";
				File f = new File(fileName);
				
				if(f.exists()){
					try {
						actualPassword = s.getPassword(fileName);
					} catch (IOException e2) {
						//ignore this
					}
					if(givenPassword.equals(actualPassword)){
						correctPassword = true;
					}

					if(correctPassword){
						try {
							s.retrieveInput(fileName);
						} catch (FileNotFoundException e1) {
							//ignore
						}
						pass.setText("");
						user.setText("");
						user.setEditable(true);
						pass.setEditable(true);
						invalidate();
						revalidate();
						repaint();
					} else {
						String errorMessage = "Username or Password is Incorrect\n Please Try Again\n";
						JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Dialog",
								JOptionPane.ERROR_MESSAGE);
						pass.setText("");
						user.setText("");
						user.setEditable(true);
						pass.setEditable(true);
					}
				} else {
					String errorMessage = "There is No Such User\nCreate a New Account Or Try Again";
					JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Dialog",
							JOptionPane.ERROR_MESSAGE);
					pass.setText("");
					user.setText("");
					user.setEditable(true);
					pass.setEditable(true);
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
