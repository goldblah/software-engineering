package seniorProject;

import java.util.ArrayList;

public class Course {
	private String name;
	private int status;
	private ArrayList<String> prerequisite;
	private int semester;
	
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
}
