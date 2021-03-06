package seniorProject;

import java.util.ArrayList;

public class map {
	//commit testw2
	//Variables
	private Course subject; //Courrent course
	private ArrayList<map> Iam; //List of Course that requires the courrent one
	private ArrayList<map> prerequisites; //List of Course that its required to take courrent course
	private boolean iam_concurrent;
	private boolean prerequisites_concurrent;
	private boolean printed = false;

	//Constructor initial root
	public map() {
		subject = null;
		Iam = new ArrayList<map>();
		prerequisites = null;
		iam_concurrent = false;
		prerequisites_concurrent = false;

	}

	//Constructor for other nodes
	public map(Course c) {
		subject = c;
		Iam = new ArrayList<map>();
		prerequisites = new ArrayList<map>();
		iam_concurrent = false;
		prerequisites_concurrent = false;
	}

	//Get list of prerequisites
	public ArrayList<Course> getPreq() {
		ArrayList<Course> ret = new ArrayList<Course>();
		for(map m: prerequisites) {
			ret.add(m.getCourse());
		}
		return ret;
	}

	//Returns if all courses have been taken
	public boolean done() {
		//If im not the root
		if(subject != null) {
			//If its diferent from 2, then it measn that it hasnt been taken
			if (subject.getStatus() != 2) return false;
		}
		//No more connections and course taken
		else if(Iam.isEmpty()) return true;
		
		//For each conection, search if there is a false in done()
		for(map c: Iam) {
			if(c.done() == false) {
				return false;
			}
		}
		
		//Everything ok, return true
		return true;
		
	}
	
	//Get list of Iam
	public ArrayList<Course> getIam() {
		ArrayList<Course> ret = new ArrayList<Course>();
		for(map m: Iam) {
			ret.add(m.getCourse());
		}
		return ret;
	}
	
	//Search if a course is in Iam
	public map search(Course c) {
		map ret = null;

		if( prerequisites == null ) { //it is the root - then search every one of Iam
			for (map m: Iam) {
				map t = m.search(c);
				if (t != null) {
					return t;
				}
			}
			return ret;
		}

		else { //every other element
			if (subject == null) { //if the subject is null, then return that
				return ret;
			}
			else if (subject.getName().equals(c.getName())) { //if it is the couse we are looking for, return it self
				return this;
			}
			else { //search every iam
				for (map m: Iam) {
					map t = m.search(c);
					if (t != null) {
						return t;
					}
				}
				return ret;
			}

		}

	}

	//Prints all the conections
	public void toPrint() {
		if(subject != null)
			System.out.print(subject.getName());
		else
			System.out.print("Root");
		
		this.printed = true;
		
		System.out.print(" -> ");

		for(map m: Iam) {
			System.out.print(m.getCourse().getName() + " ");
		}

		System.out.println();
		for(map m: Iam) {
			if(!m.printed)
				m.toPrint();
		}

	}
	
	
	//Returns if a course can take
	//Aka, al prerequisites are met
	public boolean canTake() {
		boolean take = true;

		//Is the root, not valid
		if (subject == null) {
			return false;
		}
		
		//For each prereq
		for(map m: prerequisites) {
			Course c = m.getCourse(); //Get course
			
			//No prerequiste
			if(c == null) {
				return true;
			}
			
			//Get status and check
			int stat = c.getStatus();
			if (stat == 0 || stat == 3) {
				return false;
			}
		}
		
		return take;
	}

	//Add a course to the Iam
	public void add(map root, Course c) {

		//System.out.println("------");
		//try {System.out.println("This: " + this.getCourse().getName()); }
		//catch (Exception e) {}
		//System.out.println("Course: " + c.getName());


		//Searching for the course
		map temp = root.search(c);
		//if(temp != null) System.out.println("1 temp: " + temp.getCourse().getName());
		//System.out.println("c: " + c.getName());
		
		if (temp == null) temp = new map(c); //If not found, then create one
		//System.out.println("2 temp: " + temp.getCourse().getName());
		//System.out.println("After search: " + temp.getCourse().getName());


		//Connect prerequiste <-> iam between courses
		if(Iam.contains(temp)) return;
		temp.addPre(this);
		Iam.add(temp);
	}

	//Connect to prerequistes
	private void addPre(map m) {
		this.prerequisites.add(m);
	}

	public Course getCourse() {
		return subject;
	}

	private void setCourse(Course c) {
		subject = c;
	}
}
