package seniorProject;

import java.util.ArrayList;

public class Course {
	private String name;
	private int status;
	protected ArrayList<String> prerequisite;
	private int semester;
<<<<<<< HEAD
	private int creditHours; 
=======
	private int creditHours;
>>>>>>> branch 'master' of https://github.com/goldblah/software-engineering
	private int priority;
	private String grade;

	public Course(String name){
		this.name = name;
		prerequisite = new ArrayList<>();
	}

	public Course(){
		prerequisite = new ArrayList<>();
		grade = null;
	}
<<<<<<< HEAD
	
=======

>>>>>>> branch 'master' of https://github.com/goldblah/software-engineering
	public void setCH(int i){
		this.creditHours = i;
	}
<<<<<<< HEAD
	
=======

>>>>>>> branch 'master' of https://github.com/goldblah/software-engineering
	public int getCH(){
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

	public ArrayList<String> getPrereqs(){
		return prerequisite;
	}

	public void setPrereq(ArrayList<String> prereqs){
		prerequisite = prereqs;
	}

	public void setPrereq(String p){
		prerequisite.add(p);
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
