package seniorProject;

import java.io.FileNotFoundException;

public class Student_Tester {
	
	public static void test(boolean[] t) {
		int fails = 0;
		
		for(boolean b: t) {
			if(!b) fails++;
		}
		
		if (fails != 0) System.out.println(fails + " error(s)");
		else System.out.println("OK!");
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Student s = new Student();
		
		//runs the following methods to determine if they run fine
		s.setCompletionStatus();
		s.findClassesWPrereqs();

		System.out.println("----  START  ----");
		
		/*for (Course c: s.majorCourses){
			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getStatus());
			System.out.println(c.getGrade());
			System.out.println();
		}*/
		
		//s.generateMap();
		s.m.toPrint();
	}
	
}
