package seniorProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;


public class Output {

	public void createNewUserFile(String name, String ID, String password, 
			ArrayList<String> major, ArrayList<String> minor, String startSemester, int currentSemester,
			ArrayList<String> classes) throws IOException{
		String fileName = ID + ".txt";
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


}
