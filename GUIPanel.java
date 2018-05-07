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
import javax.swing.JComboBox;
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
	JPanel chooseOptionalCourses;
	boolean newUser;
	boolean backButton;
	boolean addNewClass;
	boolean chooseOC;
	String userPassword;
	String permanentUser;
	String studentStartSemester;
	int studentCurrentSemester;
	String studentName;
	ArrayList<String> majorArray;
	ArrayList<String> minorArray;
	ArrayList<String> classesTaken;
	ArrayList<String> optionalCourses;
	ArrayList<String> optionalCourseOptions;
	Student s;
	private String schedule = "";
	boolean generateSchedules;
	//String[] major = {"Accounting", "Agricultural Business", "Agricultural Education", "Agricultural Sciences", "Animal and Dairy Science: Production and Management", "Animal and Dairy Science: Pre-Vet", "BAAS in Animal Science", "BS in Archaeology", "BA in Archaeology", "Forensic Anthropology", "Graphic Design", "Visual Arts", "Economics and Finance", "BS in Aviation Science", "BAAS in Aviation Science", "Biochemistry " };


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(generateSchedules == true){
			g.setColor(Color.BLACK);
			int x = -110;
			int y = 0;
			int maxLength = 0;
			String[] pieces = schedule.split("\n");
			for(String s: pieces){
				if(s.contains("Semester")){
					x+=125;
					y = 0;
				}
				if(y+20 > getHeight()-140){
					y = 0;
					x +=100;
				} else {
					y+=20;
				}
				if(x+100 > getWidth()-20){
					x = 0;
					y = 100;
				}

				g.drawString(s, x, y);
			}
			for(String p: pieces){
				x = 15;
				y = 300;
				int num;
				if(p.contains("Optional")){
					ArrayList<String> temp = new ArrayList<>();
					num = s.getOptionalCourseNum(p);
					temp = s.getOptionalCourseInfo(p);
					String message = "You must take " + num + " course(s) to fulfill the " + p +" requirement.";
					g.drawString(message, x, y);
					for(String t: temp){
						if(y+20 > getHeight()){
							y = 300;
						} else {
							y = y + 20;
						}
						g.drawString(t, x, y);
					}
				}
			}
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
		}else if(correctPassword == true && addNewClass == false){
			g.setColor(Color.BLACK);
			g.drawString("Welcome, "+ studentName, 15, 15);
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
		} else if(correctPassword == false && newUser == false && backButton == true){
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
		}  /*else if(chooseOC == true && generateSchedules == false){
			chooseOptionalCourses = chooseOptionalCourse();
			chooseOptionalCourses.setBackground(Color.WHITE);
			startPanel.setFocusable(false);
			startPanel.setVisible(false);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(true);
			schedulePanel.setFocusable(true);
			addNewPanel.setVisible(false);
			addNewPanel.setFocusable(false);
			chooseOptionalCourses.setVisible(true);
			chooseOptionalCourses.setFocusable(true);
			add(schedulePanel, BorderLayout.SOUTH);
			add(chooseOptionalCourses, BorderLayout.CENTER);
			revalidate();
		}*/else if(correctPassword == true && addNewClass == false && backButton == true){
			g.setColor(Color.BLACK);
			g.drawString("Welcome, "+ studentName, 15, 15);
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
		} 

	}//end paintComponent

	/*public JPanel chooseOptionalCourse(){
		JPanel choose = new JPanel();
		ArrayList<String> selectedInfo = new ArrayList<>();
		choose.setPreferredSize(new Dimension(800,200));
		for(String i: optionalCourses){
			System.out.println(i);
			JLabel label = new JLabel(i);
			choose.add(label);
			optionalCourseOptions.addAll(s.getOptionalCourseInfo(i));
			String[] arrayString = optionalCourseOptions.toArray(new String[optionalCourseOptions.size()]);
			JComboBox patternList = new JComboBox(arrayString);
			patternList.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
					String selectedClass = (String)cb.getSelectedItem();
					selectedInfo.add(selectedClass);
				}

			});
			choose.add(patternList);
			optionalCourseOptions = new ArrayList<>();
		}
		JButton enter = new JButton("Save Selections");
		enter.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//write the info to the bottom of their file
				chooseOC = false;
				for (Course c: s.majorCourses){
					for(String i: optionalCourses){
						for(String g: selectedInfo){
							if(c.getName().equals(i)){
								int index = s.majorCourses.indexOf(c);
								s.majorCourses.remove(c);
								try {
									s.majorCourses.add(s.getClassInfo(i));
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
			});
		System.out.println(optionalCourseOptions);
		return choose;
		}*/

		public GUIPanel(Student s) throws FileNotFoundException, IOException{
			optionalCourses = new ArrayList<>();
			optionalCourseOptions = new ArrayList<>();
			//chooseOptionalCourses = chooseOptionalCourse();
			this.s = s;
			correctPassword = false;
			newUser = false;
			backButton = false;
			addNewClass = false;
			generateSchedules = false;
			chooseOC = false;
			setPreferredSize(new Dimension(800,625));
			setLayout(new BorderLayout());
			startPanel = startScreen();
			newUserPanel = newUserScreen();
			schedulePanel = scheduleScreen();
			addNewPanel = addNewClassesScreen();
			studentName = s.studentName;
			//add(newUserPanel, BorderLayout.SOUTH);
			add(startPanel, BorderLayout.SOUTH);
			//add(chooseOptionalCourses, BorderLayout.CENTER);
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
			addNewClasses.add(scrollPane, BorderLayout.CENTER);
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
					
					try {
						s.appendClassesToStudentFile(permanentUser, classesTaken);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
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
					try {
						s.generateSchedule(permanentUser);
						schedule = s.toPrint();
					} catch (FileNotFoundException e1) {
						//ignore
					}
					generateSchedules = true;
					invalidate();
					revalidate();
					repaint();
				}
			});

			/*JButton optionalCourse = new JButton("Choose Optional Course");
			optionalCourse.setPreferredSize(new Dimension(175, 20));
			optionalCourse.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//choose optional course

					if(schedule.contains("Optional")){
						String[] pieces = schedule.split("\n");
						for(String s: pieces){
							if(s.contains("Optional")){
								optionalCourses.add(s);
							}
						}
					}
					chooseOC = true;
					generateSchedules = false;
					invalidate();
					revalidate();
					repaint();
				}
			});*/

			JButton addNewClasses = new JButton("Add New Classes");
			addNewClasses.setPreferredSize(new Dimension(175, 20));
			addNewClasses.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//add new classes to the text file
					generateSchedules = false;
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
					reset();
					invalidate();
					revalidate();
					repaint();
				}
			});
			scheduleScreen.add(generateSchedule);
			//scheduleScreen.add(optionalCourse);
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
							permanentUser = IDField.getText();
							studentStartSemester = startSemesterField.getText();
							if (!currentSemesterField.getText().equals("")) {
								studentCurrentSemester = Integer.parseInt(currentSemesterField.getText().trim());
							}

							//get courses entered by user
							String temp = classesField.getText();
							if(temp.contains(", ")){
								String[] pieces = temp.split(", ");
								for (String s: pieces){
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
								s.writeNewStudent(studentName, permanentUser, userPassword, majorArray, minorArray, 
										studentStartSemester, studentCurrentSemester, classesTaken);
								s.retrieveInput(permanentUser);
								studentName = s.studentName;
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

					if (user.getText().trim().matches(".*\\d+.*") == false || user.getText().trim().length() != 9){
						JOptionPane.showMessageDialog(new JFrame(), "ID Number is not in the correct format,\nplease try again.", "Incorrect ID",
								JOptionPane.ERROR_MESSAGE);
					}
					String givenUserName = user.getText().trim();

					String givenPassword = new String(pass.getPassword());
					String actualPassword = null;
					String fileName = "neededFiles/" + givenUserName + ".txt";
					File f = new File(fileName);

					if(f.exists()){
						permanentUser = givenUserName;
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
							studentName = s.studentName;
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
		
		public void reset(){
			removeAll();
			updateUI();
			
			startPanel = startScreen();
			newUserPanel = newUserScreen();
			schedulePanel = scheduleScreen();
			addNewPanel = addNewClassesScreen();
			optionalCourses = new ArrayList<>();
			optionalCourseOptions = new ArrayList<>();
			majorArray = new ArrayList<>();
			minorArray = new ArrayList<>();
			classesTaken = new ArrayList<>();
			studentName = s.studentName;
			
			correctPassword = false;
			newUser = false;
			backButton = false;
			addNewClass = false;
			generateSchedules = false;
			
			setPreferredSize(new Dimension(800,625));
			setLayout(new BorderLayout());
			add(startPanel, BorderLayout.SOUTH);
			
			startPanel.setFocusable(true);
			startPanel.setVisible(true);
			newUserPanel.setVisible(false);
			newUserPanel.setFocusable(false);
			schedulePanel.setVisible(false);
			schedulePanel.setFocusable(false);
			addNewPanel.setVisible(false);
			addNewPanel.setFocusable(false);
			
			invalidate();
			revalidate();
			repaint();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
