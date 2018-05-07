package seniorProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Student_Tester {
	
	public static void test(boolean[] t) {
		int fails = 0;
		
		for(boolean b: t) {
			if(!b) fails++;
		}
		
		if (fails != 0) System.out.println(fails + " error(s)");
		else System.out.println("OK!");
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Student s = new Student();
		
		//runs the following methods to determine if they run fine
		//s.setCompletionStatus();
		//s.findClassesWPrereqs();

		//System.out.println("----  START  ----");
		
		/*for (Course c: s.majorCourses){
			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getStatus());
			System.out.println(c.getGrade());
			System.out.println();
		}*/
		
		s.retrieveInput("neededFiles/Input.txt");
		//s.generateSchedule("111111111");
		//s.generateMap();
		//ArrayList<Semester> t = s.getSemesters();
		//for(Semester se: t) {
			//se.toPrint();
			//System.out.println();
		//}
	}
	
}
