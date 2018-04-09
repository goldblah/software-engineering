package seniorProject;

import java.util.ArrayList;

public class OptionalCourse extends Course{
	private ArrayList<String> listOfCourses;
	int numClassesToTake;
	
	public void setName(String i){
		super.setName(i);
	}

	public OptionalCourse() {
		listOfCourses = new ArrayList<>();
	}
	
	public void setCourse(String n){
		listOfCourses.add(n);
	}
	
	public void setCourse(ArrayList<String> c){
		listOfCourses = c;
	}
	
	public ArrayList<String> getCourses(){
		return listOfCourses;
	}
	
	public void setNumClasses(int i){
		numClassesToTake = i;
	}
	
	public int getNumClasses(){
		return numClassesToTake;
	}
}
