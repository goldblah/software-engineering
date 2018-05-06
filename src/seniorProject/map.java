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
	
	public boolean canTake() {
		boolean take = true;
		
		//Probably not necesarry:
		//if (subject.getEitherOr()) {
		//	return eitherCanTake();
		//}
		
		//Is the root, not valid
		if (subject == null) {
			return false;
		}
		
		for(map m: prerequisites) {
			Course c = m.getCourse();
			
			//No prerequiste
			if(c == null) {
				return true;
			}
			
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
