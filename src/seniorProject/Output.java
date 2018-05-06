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

	public void writeStudentInfoFile(String name, String ID, String password, 
			ArrayList<String> major, ArrayList<String> minor, String startSemester, int currentSemester,
			ArrayList<String> classes) throws IOException{
		String fileName = ID + ".txt";

		File f = new File(fileName);
		FileOutputStream fos = new FileOutputStream(f);
		PrintWriter pw = new PrintWriter(fos);

		//pw.write(str);
		pw.flush();
		fos.close();
		pw.close();

	}

	public void writeStudentInfoFileTest(String ID) throws IOException{
		String fileName = ID + ".txt";
		File file = new File(fileName);
		PrintWriter out = new PrintWriter(fileName);
		System.out.println(fileName);
		System.out.println(file.exists());
		if (!file.exists()) {
			System.out.println("Makeing New File");
            file.createNewFile();
        }
		out.println("hello");
		out.close();
	}

}
