package seniorProject;

import java.io.FileNotFoundException;

public class Input_Tester {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		Input i = new Input();
		i.retrieveInput("Input.txt");
		i.retrieveInput("234567890.txt");
		/*for (Course c: i.classesTaken){


		for (Course c: i.classesTaken){

			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getStatus());
			System.out.println(c.getGrade());
		}*/
		//i.checkForChanges("Input.txt");
		
		i.getClassInfo("MATH119");
		
			//test
		}

	}

