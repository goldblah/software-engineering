package seniorProject;

import java.io.FileNotFoundException;

public class Input_Tester {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		Input i = new Input("Input.txt");

		for (Course c: i.classesTaken){
			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getStatus());
			System.out.println(c.getGrade());
		}

	}

}
