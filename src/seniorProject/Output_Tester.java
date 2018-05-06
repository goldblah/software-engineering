package seniorProject;

import java.io.IOException;
import java.util.ArrayList;

public class Output_Tester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> major = new ArrayList<>();
		major.add("CS");
		major.add("BIOCHEMISTRY");
		ArrayList<String> minor = new ArrayList<>();
		minor.add("N/A");
		ArrayList<String> classes = new ArrayList<>();
		classes.add("CS102 A");
		classes.add("CS120 A");
		classes.add("CS123 A");
		classes.add("CS220 A");
		classes.add("CS234 A");
		classes.add("CS301 A");
		classes.add("CS357 A");
		Output o = new Output();
		o.createNewUserFile("Hayley Goldblatt","123456789","changeme123", major, minor, "FALL2014", 8, classes);

	}

}
