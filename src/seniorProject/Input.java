package seniorProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {

	private String filename;//file that is going to be parsed
	private String idNum;//student's id number
	ArrayList<Course> classesTaken;//classes the student has taken
	private String name;//student's name
	ArrayList<String> major;//major(s) the student has
	ArrayList<String> minor;//minor(s) the student has
	String startSemester;//when the student started at the university
	int currentSemester;//current semester student is on
	ArrayList<Course> majorCourses;//classes required by the major
	private int numClasses;//number of optional courses required for the major
	ArrayList<Course> genEdCourses;

	/**
	 * Constructor
	 * @param fileName
	 * @throws FileNotFoundException 
	 */
	public Input(String fileName) throws FileNotFoundException{
		filename = fileName;
		getStudentInfo();
		getMajorClassInfo();
		getGenEdInfo();
	}

	/**
	 * gets pertinent student information from the input file given to the system
	 * Must be a .txt file
	 * @throws FileNotFoundException
	 * @author hayleygoldblatt
	 */
	public void getStudentInfo() throws FileNotFoundException{
		String line = null;
		classesTaken = new ArrayList<>();
		major = new ArrayList<String>();
		minor = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);

			//Begins parsing the student input file and collects the required information
			while((line = br.readLine()) != null) {
				if(line.toUpperCase().contains("NAME")){
					String[] pieces = line.split(": ");
					name = pieces[1];
				} else if(line.toUpperCase().contains("ID")){
					String[] pieces = line.split(": ");
					pieces[1] = pieces[1].trim();
					if(pieces[1].length() == 9){
						idNum = pieces[1].trim();
					} else{
						System.out.println("Incorrect ID number, please resubmit your input file");
					}
				} else if(line.toUpperCase().contains("MAJOR")){
					String[] pieces = line.split(": ");
					if(pieces[1].contains(" ")){
						pieces = pieces[1].split(" ");
						for(String s : pieces){
							major.add(s);
						}
					} else {
						major.add(pieces[1]);
					}
				} else if (line.toUpperCase().contains("MINOR")){
					String[] pieces = line.split(": ");
					if(pieces[1].contains(" ")){
						pieces = pieces[1].split(" ");
						for(String s : pieces){
							minor.add(s);
						}
					} else {
						minor.add(pieces[1]);
					}
				} else if (line.toUpperCase().contains("START")){
					String[] pieces = line.split(": ");
					startSemester = pieces[1];
				} else if (line.toUpperCase().contains("CURRENT")){
					String[] pieces = line.split(": ");
					currentSemester = Integer.parseInt(pieces[1]);
				} else{
					while((line = br.readLine()) != null){
						String[] pieces = line.split(" ");
						Course temp = getClassInfo(pieces[0]);
						temp.setGrade(pieces[1]);
						classesTaken.add(temp);
						
					}
				}

			}
			br.close();
		} catch (IOException e) {
			System.out.println("Unable to open file '" + filename + "'");
		}
	}
	
	//make class for parsing minor information
	
	/**
	 * Checks the input file to make sure there is no changes to the students information
	 * @author hayleygoldblatt
	 */
	public void checkForChanges(String f){
		String line = null;
		String pieces[] = null;
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null) {
				//check for changes in the file to verify all information is the same
				if(line.toUpperCase().contains("NAME")){
					pieces = line.split(": ");
					if (!pieces[1].equalsIgnoreCase(name)){
						name = pieces[1];
						System.out.println("New name");
					}
				} else if(line.toUpperCase().contains("ID")){
					pieces = line.split(": ");
					if (!pieces[1].equalsIgnoreCase(idNum)){
						idNum = pieces[1];
						System.out.println("New ID");
					}
				} else if(line.toUpperCase().contains("MAJOR")){
					pieces = line.split(": ");
					if (pieces[1].contains(" ")){
						pieces = pieces[1].split(" ");
						for(String s: pieces){
							if(!major.contains(s)){
								major.add(s);
								System.out.println("New major");
							}
						}
					}	
				} else if(line.toUpperCase().contains("MINOR")){
					pieces = line.split(": ");
					if (pieces[1].contains(" ")){
						pieces = pieces[1].split(" ");
						for(String s: pieces){
							if(!minor.contains(s)){
								minor.add(s);
								System.out.println("New minor");
							}
						}
					}
				} else if(line.toUpperCase().contains("START")){
					pieces = line.split(": ");
					if (!pieces[1].equalsIgnoreCase(startSemester)){
						startSemester = pieces[1];
						System.out.println("New start semester");
					}
					
				} else if(line.toUpperCase().contains("CURRENT")){
					pieces = line.split(": ");
					if (Integer.parseInt(pieces[1].trim()) != currentSemester){
						currentSemester = Integer.parseInt(pieces[1].trim());
						System.out.println("New current semester");
					}
					
				} else {
					while((line = br.readLine()) != null){
						boolean taken = false;
						pieces = line.split(" ");
						Course temp = getClassInfo(pieces[0]);
						for(Course c: classesTaken){
							if(c.getName().equalsIgnoreCase(temp.getName())){
								taken = true;
								break;
							}
						}
						if (taken == false){
							temp.setGrade(pieces[1]);
							classesTaken.add(temp);
							System.out.println("New classes");
						}
						
					}
					
				}
				
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Unable to open file '" + filename + "'");
		}
	}

	/**
	 * Pulls all the class info for courses that need to be taken for a major
	 * @throws FileNotFoundException
	 * @author hayleygoldblatt
	 */
	public void getMajorClassInfo() throws FileNotFoundException{
		majorCourses = new ArrayList<>();
		String line = null;

		//begins to parse the major information file
		if (major.get(0) != null){
			filename = major.get(0).toLowerCase() + "_major.txt" ;
			try {
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
				while ((line = br.readLine())!= null){

					//cycles through the majors the student has and finds information for it
					for(String m: major){
						//decides if the line being parsed is for an optional course or a required class
						if(line.contains(m) || !line.contains(":")){
							majorCourses.add(getClassInfo(line));
						} else if(!line.contains("Major") && line.contains(":")){
							majorCourses.add(getOpClassInfo(line));
						}
					}
				}
				br.close();//closes the file reader
			} catch (IOException e) {
				//only carried out if the file cannot be found
				System.out.println("Unable to open file '" + filename + "'");
			}
		}
	}
	
	/**
	 * gets information from the general education files
	 * @throws FileNotFoundException
	 */
	public void getGenEdInfo() throws FileNotFoundException{
		genEdCourses = new ArrayList<>();
		filename = "gen_ed.txt";
		String line = null;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine())!= null){
				//decides if the line being parsed is for an optional course or a required class
				if(!line.contains("General Education Core") && !line.contains(":")){
					//System.out.println(line);
					genEdCourses.add(getClassInfo(line));
				} else if(!line.contains("General Education Core") && line.contains(":")){
					//System.out.println(line);
					genEdCourses.add(getOpClassInfo(line));
				}
			}
			br.close();//closes the file reader
		} catch (IOException e) {
			//only carried out if the file cannot be found
			System.out.println("Unable to open file '" + filename + "'");
		}
		
		/*for(Course c: genEdCourses){
			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getPrereqs());
			try{
				for(Course a: ((OptionalCourse) c).getCourses()){
					System.out.println(a.getName());
				}
			} catch(ClassCastException e){
				System.out.println("Cannot do this");
			}
			System.out.println();
		}*/
		
	}

	/**
	 * Class to pull information for what is classified as an optional course
	 * @param line line to pull information from
	 * @return the optional course containing all the courses that can work
	 * @throws FileNotFoundException
	 */
	public OptionalCourse getOpClassInfo(String line) throws FileNotFoundException{
		OptionalCourse op = new OptionalCourse();
		String[] pieces = null;
		if(line.matches(".*\\d+.*")){
			pieces = line.split(": ");
			numClasses = Integer.parseInt(pieces[0]);
			for(String s: pieces){
				if(s.contains("OR")){
					pieces = s.split(" OR ");
				}
			}//end for loop
		}
		
		//Sets the optional classes into a temporary ArrayList
		ArrayList<String> temp = new ArrayList<String>(Arrays.asList(pieces));
		for(int i = 0; i < temp.size(); i ++){
			String b = temp.get(i);
			String placeholder = null;
			
			//checks if there are other classes that need to split apart
			if(b.contains(", ")){
				placeholder = b;
				pieces = placeholder.split(", ");
				for(String s: pieces){
					if(s.matches(".*\\d+.*")){
						op.setCourse(getClassInfo(s));
					} else {
						String name = "Any " + s + " Course";
						Course a = new Course(name);
						op.setCourse(a);
					}
				}
			} else {
				if(b.matches(".*\\d+.*")){
					op.setCourse(getClassInfo(b));
				} else {
					String name = "Any " + b + " Course";
					Course a = new Course(name);
					op.setCourse(a);
				}
			}
		}

		//Finds the name of the Optional Course
		Pattern p = Pattern.compile("[A-Z]+|\\d+");
		Matcher a = p.matcher(pieces[0]);
		ArrayList<String> allMatches = new ArrayList<>();
		while (a.find()) {
			allMatches.add(a.group());
		}

		//Sets all the information for the optional course
		op.setNumClasses(numClasses);
		op.setName(allMatches.get(0) + "_OptionalCourse1");

		return op;
	}

	/**
	 * Gets class information for a provided class
	 * @param className; class to find information for (ART270 for example)
	 * @return a course object that contains the prerequisites, credit hours and name
	 * @throws FileNotFoundException
	 */
	public Course getClassInfo(String className) throws FileNotFoundException{
		className = className.toUpperCase();
		Pattern p = Pattern.compile("[A-Z]+|\\d+");
		Matcher m = p.matcher(className);
		ArrayList<String> allMatches = new ArrayList<>();
		String line = null;
		Course c = new Course(className);

		while (m.find()) {
			allMatches.add(m.group());
		}
		
		String fn = allMatches.get(0) + ".txt";
		try {
			FileReader fr = new FileReader(fn);
			LineNumberReader r = new LineNumberReader(fr);
			while ((line = r.readLine()) != null) {
				if ((r.getLineNumber()-1) % 5 == 0) {
					line = line.trim();
					if(line.equalsIgnoreCase(className)){
						
						//parsing the credit hours
						line = r.readLine().trim();
						c.setCH(line);

						//parsing the prereqs
						line = r.readLine();
						line = line.trim();
						String[] pieces = line.split("; ");
						
						for(String s: pieces){
							//System.out.println(s);
							if(!s.contains(", ") && !s.contains("OR") && !s.equals("") && !s.contains("CE")){
								//Course temp = getClassInfo(s);
								//c.setPrereq(temp);
								c.setPrereq(s);
							}  
						}
						
						ArrayList<String> temp= new ArrayList<>();
						for(String s: pieces){
							if(s.contains("CE") || s.contains(", ") || s.contains("OR")){
								temp.add(s);
							}
						}
						
						for (String s: temp){
							if (s.contains("CE") && s.contains("OR")){
								pieces = s.split(": ");
								for (String i: pieces){
									if(i.contains(" OR ")){
										String[] pieces1 = i.split(" OR ");
										for(String e: pieces1){
											//Course temporary = getClassInfo(i);
											//temporary.setCE(true);
											//c.setPrereq(temporary);
											c.setPrereq(e);
											c.setCE(true);
											c.setEitherOr(true);
										}
									}
								}
							} else if(s.contains(", ") && !s.equals(" ")){
								pieces = s.split(", ");
								for(String d: pieces){
									//c.setPrereq(getClassInfo(d));
									c.setPrereq(d);
								}
								//break;
							} else if (s.contains("CE")){
								pieces = s.split(": ");
								for(String g: pieces){
									if(!g.contains("CE")){
										//Course temporary = getClassInfo(g);
										//temporary.setCE(true);
										//c.setPrereq(temporary);
										c.setPrereq(g);
										c.setCE(true);
									}
								}
							} else if(s.contains(" OR ") && !s.equals(" ")){
								pieces = s.split(" OR ");
								for(String d: pieces){
									//Course temporary = getClassInfo(d);
									//c.setPrereq(temporary);
									c.setPrereq(d);
									c.setEitherOr(true);
								}
								//break;
							}
						}
						
						//parsing the priority
						line = r.readLine();
						line = line.trim();
						switch (line){
						case "F, S": c.setPriority(0);
						break;
						case "F": c.setPriority(1);
						break;
						case "S": c.setPriority(2);
						break;
						case "Odd F": c.setPriority(3);
						break;
						case "Odd S": c.setPriority(4);
						break;
						case "Even F": c.setPriority(5);
						break;
						case "Even S": c.setPriority(6);
						break;
						default: c.setPriority(0);
						break;
						}	
					}
				}
				c.setStatus(0);
			}
		}
		catch (IOException e) {
			System.out.println("Unable to open file '" + fn + "'");
		}
		//System.out.println(c.getPrereqs());
		return c;
	}
	
	public Course setCE(){
		return null;
	}
	
	public static String getPassword(String givenUser) throws IOException{
		String tempFileName = givenUser + ".txt";
		FileReader fr;
		String line = null;
		try {
			fr = new FileReader(tempFileName);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null) {
				if(line.contains("Password")){
					String[] pieces = line.split(": ");
					return pieces[1];
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return "No such user";
		}
		return "No such user";
	}
}
