package seniorProject;

import java.util.ArrayList;

public class Course {
	private String name;
	private int status;
	protected ArrayList<Course> prerequisite;
	private int semester;
	private String creditHours; 
	private int priority;
	private String grade;
	private boolean concurrentEnrollment;
	ArrayList<Course> eitherOr;

	public Course(String name){
		this.name = name;
		prerequisite = new ArrayList<>();
	}

	public Course(){
		prerequisite = new ArrayList<>();
		grade = null;
	}
	
	public void setCE(boolean ce){
		concurrentEnrollment = ce;
	}
	
	public boolean getCE(){
		return concurrentEnrollment;
	}
	
	public void setCH(String i){
		this.creditHours = i;
	}
	
	public String getCH(){
		return creditHours;
	}

	public void setStatus (int s){
		status = s;
	}

	public int getStatus(){
		return status;
	}

	public void setName(String n){
		name = n;
	}

	public String getName(){
		return name;
	}

	public ArrayList<Course> getPrereqs(){
		return prerequisite;
	}

	public void setPrereq(ArrayList<Course> prereqs){
		prerequisite = prereqs;
	}

	public void setPrereq(Course p){
		prerequisite.add(p);
	}
	
	public void setEitherOr(ArrayList<Course> eo){
		eitherOr = eo;
	}
	
	public ArrayList<Course> getEitherOr(){
		return eitherOr;
	}
	
	public void setEitherOr(Course eo){
		eitherOr.add(eo);
	}

	public void setPriority(int p){
		this.priority = p;
	}

	public int getPriority(){
		return priority;
	}

	public String getGrade(){
		return grade;
	}

	public void setGrade(String g){
		grade = g;
	}
}
