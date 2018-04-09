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
	private ArrayList<String> classesTaken;//classes the student has taken
	private ArrayList<String> grade;//grades for classes the student has taken, corresponds to the classesTaken arraylist
	private String name;//student's name
	private ArrayList<String> major;//major(s) the student has
	private ArrayList<String> minor;//minor(s) the student has
	private String startSemester;//when the student started at the university
	private int currentSemester;//current semester student is on
	private ArrayList<Course> majorCourses;//classes required by the major
	private int numClasses;//number of optional courses required for the major

	/**
	 * Constructor
	 * @param fileName
	 */
	public Input(String fileName){
		filename = fileName;
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
		grade = new ArrayList<>();
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
					idNum = pieces[1];
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
					minor.add(pieces[1]);
				} else if (line.toUpperCase().contains("START")){
					String[] pieces = line.split(": ");
					startSemester = pieces[1];
				} else if (line.toUpperCase().contains("CURRENT")){
					String[] pieces = line.split(": ");
					currentSemester = Integer.parseInt(pieces[1]);
				} else{
					while((line = br.readLine()) != null){
						String[] pieces = line.split(" ");
						classesTaken.add(pieces[0]);
						grade.add(pieces[1]);
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
		String[] pieces = null;

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
							OptionalCourse op = new OptionalCourse();
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
							for(String b: temp){
								op.setCourse(getClassInfo(b));
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
							System.out.println(op.getCourses());
							majorCourses.add(op);
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
	 * Gets class information for a provided class
	 * @param className; class to find information for (ART270 for example)
	 * @return a course object that contains the prerequisites, credit hours and name
	 * @throws FileNotFoundException
	 */
	public Course getClassInfo(String className) throws FileNotFoundException{
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
					if(line.contains(className)){
						
						//parsing the credit hours
						line = r.readLine().trim();
						int temp = Integer.parseInt(line);
						c.setCH(temp);

						//parsing the prereqs
						line = r.readLine();
						line = line.trim();
						String[] pieces = line.split("; ");
						for(String s: pieces){
							if(!s.contains(", ") && s != ""){
								c.setPrereq(s);
							} else if(!s.contains("OR") && s != ""){
								c.setPrereq(s);
							}
						}
						for (String s: pieces){
							if(s.contains(", ") && s!= " "){
								pieces = s.split(", ");
								break;
							} else if(s.contains(" OR ") && s != " "){
								pieces = s.split(" OR ");
								break;
							}
						}
						for (String s: pieces){
							c.setPrereq(s);
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
			}
		}
		catch (IOException e) {
			System.out.println("Unable to open file '" + fn + "'");
		}
		return c;
	}

	/**
	 * Main class to test input class
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Input i = new Input("Input.txt");
		i.getStudentInfo();
		i.getMajorClassInfo();
	}
}
