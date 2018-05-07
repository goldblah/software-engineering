package seniorProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Output {

	public void createNewUserFile(String name, String ID, String password, 
			ArrayList<String> major, ArrayList<String> minor, String startSemester, int currentSemester,
			ArrayList<String> classes) throws IOException{
		String fileName = "neededFiles/" + ID + ".txt";
		File file = new File(fileName);
		PrintWriter out = new PrintWriter(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		out.println("NAME: " + name);
		out.println("ID: "+ID);
		out.println("PASSWORD: "+password);
		out.print("MAJOR: ");

		for(String m: major){
			out.print(m);
			out.print(" ");
		}
		out.println("");

		out.print("MINOR: ");
		for(String m: minor){
			out.print(m);
			out.print(" ");
		}
		out.println("");

		out.println("START: " + startSemester);

		out.println("CURRENT: " + currentSemester);

		out.println("CLASSES:");
		for(String c: classes){
			out.println(c);
		}

		out.close();
	}

	public void appendNameToStudentFile(String user, String name){

	}

	public void appendIDToStudentFile(String user, String ID){

	}

	public void appendPasswordToStudentFile(String user, String password){

	}

	public void appendMajorToStudentFile(String user, ArrayList<String> major){

	}

	public void appendMinorToStudentFile(String user, ArrayList<String> minor){

	}

	public void appendStartToStudentFile(String user, String start){

	}

	public void appendCurrentToStudentFile(String user, String current){

	}

	public boolean findInFile(String filename, String toBeFound) throws FileNotFoundException{
		String fileName = "neededFiles/" + filename;
		boolean found = false;
		if(!filename.contains(".txt")){
			fileName = filename + ".txt";
		}
		File file = new File(fileName);
		Scanner in = new Scanner(file);

		
		int lineNum = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			lineNum++;
			if(line.contains(toBeFound)) { 
				found = true;
				break;
			} else {
				found =  false;
			}
		}
		
		return found;
	}
	
	public void appendClassesToStudentFile(String user, ArrayList<String> classes) throws IOException{
		String fileName = "neededFiles/" + user;
		if(!user.contains(".txt")){
			fileName = user + ".txt";
		}
		
		File file = new File(fileName);
		if(file.exists()){
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);

			for(String s: classes){
				if((findInFile(fileName, s))==false){
					bw.write("\n" + s);
				}
			}
			bw.close();
		}
	}
}
