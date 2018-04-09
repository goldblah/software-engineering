package seniorProject;

import java.util.ArrayList;

public class OptionalCourse extends Course{
	private ArrayList<Course> listOfCourses;
	int numClassesToTake;
	
	public void setName(String i){
		super.setName(i);
	}

	public OptionalCourse() {
		listOfCourses = new ArrayList<>();
	}
	
	public void setCourse(Course n){
		listOfCourses.add(n);
	}
	
	public void setCourse(ArrayList<Course> c){
		listOfCourses = c;
	}
	
	public ArrayList<Course> getCourses(){
		return listOfCourses;
	}
	
	public void setNumClasses(int i){
		numClassesToTake = i;
	}
	
	public int getNumClasses(){
		return numClassesToTake;
	}
}
