package seniorProject;

import java.util.ArrayList;

public class Semester {
	private String nameSemester;
	private int semesterNum;
	private ArrayList<Course> course = new ArrayList<Course>();
	
	public String getSemester(){
		return nameSemester;
	}
	
	public void setSemester(String n){
		nameSemester = n;
	}
	
	public int getSemesterNum(){
		return semesterNum;
	}
	
	public void setSemesterNum(int x){
		semesterNum = x;
	}
	
	public void addCourses(Course c){
		//System.out.println("Adding " + c.getName() + " " + c.getPriority());
		course.add(c);
	}
	
	public void deleteCourse(String name){
		for (Course c: course){
			if (c.getName().equalsIgnoreCase(name)){
				course.remove(c);
			}
		}
	}
	
	public void deleteCourse(){
		course.clear();
	}
}
