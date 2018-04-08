package seniorProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {

	private String filename;
	private String idNum;
	private ArrayList<String> classes;
	private ArrayList<String> grade;
	private String name;
	private ArrayList<String> major;
	private ArrayList<String> minor;
	private String startSemester;
	private int currentSemester;
	private ArrayList<Course> courses;

	public Input(String fileName){
		filename = fileName;
	}

	public void getStudentInfo() throws FileNotFoundException{
		String line = null;
		classes = new ArrayList<>();
		grade = new ArrayList<>();
		major = new ArrayList<String>();
		minor = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);

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
						classes.add(pieces[0]);
						grade.add(pieces[1]);
					}
				}

			}
			br.close();
		} catch (IOException e) {
			System.out.println("Unable to open file '" + filename + "'");
		}
	}

	public void getMajorClassInfo() throws FileNotFoundException{
		courses = new ArrayList<>();
		String line = null;
		if (major.get(0) != null){
			filename = major.get(0).toLowerCase() + "_major.txt" ;
			try {
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
				while ((line = br.readLine())!= null){
					//System.out.println(line);
					if(line.contains(major.get(0)) || !line.contains(":")){
						//System.out.println("true");
						getClassInfo(line);
					}
				}
				br.close();
			} catch (IOException e) {
				System.out.println("Unable to open file '" + filename + "'");
			}
		}
	}

	public Course getClassInfo(String className) throws FileNotFoundException{
		//System.out.println("Inside");
		Pattern p = Pattern.compile("[A-Z]+|\\d+");
		Matcher m = p.matcher(className);
		ArrayList<String> allMatches = new ArrayList<>();
		while (m.find()) {
		    allMatches.add(m.group());
		}
		//System.out.println(allMatches);
		String fn = allMatches.get(0) + ".txt";
		System.out.println(fn);
		/*try {
			FileReader fr = new FileReader(fn);
			BufferedReader br = new BufferedReader(fr);
			br.close();
		} catch (IOException e) {
			System.out.println("Unable to open file '" + filename + "'");
		}*/
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Input i = new Input("Input.txt");
		i.getStudentInfo();
		i.getMajorClassInfo();
	}
}
