package seniorProject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class Student {
	private String studentIdNum;
	private ArrayList<Semester> semesters;
	ArrayList<String> major;//major(s) the student has
	ArrayList<String> minor;//minor(s) the student has
	String startSemester;//when the student started at the university
	int currentSemester;//current semester student is on
	ArrayList<Course> classesTaken;//classes the student has taken
	ArrayList<Course> genEdCourses;
	ArrayList<Course> majorCourses;//classes required by the major
	ArrayList<Course> coursesWPrereqs;//classes with prereqs
	ArrayList<Course> coursesWOPrereqs;//classes without prereqs
	ArrayList<Course> moreCourses = new ArrayList<Course>();
	
	map m = new map();
	
	Input i = new Input("input.txt");
	
	boolean debugMode = true;
	
	
	public ArrayList<Semester> getSemesters() {
		return semesters;
	}

	public void generateSchedule() {
		
		if(debugMode) {
			System.out.println("Generate Schedule called");
		}
		
		//set completion status
		setCompletionStatus();
		//separate classes into those with prereqs and those without
		findClassesWPrereqs();
		//generate the base map for the schedule
		//traverse the map to generate the schedule, store the courses in semester containers based on which semester they will occur in
	}

	private void checkSemesters(){

	}
	
	public void generateMap() throws FileNotFoundException{
		
		if(debugMode) {
			System.out.println("Generate map called");
			
			//System.out.println(helperSearch("MATH107").getName());
			//return;
		}
		
		//Add those withoud prereq
		for(Course c: coursesWOPrereqs) {
			if(debugMode) {
				System.out.println("Adding: " + c.getName());
			}
			
			m.add(m, c);
		}
		
		if(debugMode) {
			System.out.println("Printing map so far:");
			m.toPrint();
		}
		
		//Add those with prerequistes
		for(Course c: coursesWPrereqs) {
			addWPrerq(c);
		}
	}
	
	private void addWPrerq(Course c) throws FileNotFoundException {
		
		if(debugMode) {
			System.out.println("AddWPrereq called with course " + c.getName());
		}
		
		//If prereq is empty, just add
		if (c.getPrereqs().isEmpty()) {
			
			if(debugMode) {
				System.out.println("No prerequiste, adding..");
			}
			
			m.add(m, c);
			return;
		}
		
		
		for(String p: c.getPrereqs()) {
			
			if(p.trim().isEmpty()) {
				m.add(m,  c);
				return;
			}
			
			Course temp = helperSearch(p);
			
			if(debugMode) {
				System.out.println("Found prereq string: " + p);
				System.out.println("Found prereq coruse: " + temp.getName());
				
			}
			
			addWPrerq(temp);
			
			m.search(temp).add(m, c);
		}
	}
	
	private Course helperSearch(String cs) throws FileNotFoundException {
		
		if(debugMode) {
			System.out.println("helperSearch called with string " + cs);
		}
		
		Course ret = null;
		
		//Search in coruses wo prereq
		for(Course c: coursesWOPrereqs) {
			if(c.getName().equals(cs)) {
				
				if(debugMode) {
					System.out.println("  Found course in WO Prereq " + c.getGrade());
				}
				
				return c;
			}
		}
		
		//Search in courses with prereq
		for(Course c: coursesWPrereqs) {
			
			if(c.getName().equals(cs)) {
				if(debugMode) {
					System.out.println("  Found course in With Prereq: " + c.getName());
				}
				
				return c;
			}
		}
		
		
		//Search in general ed
		for(Course c: genEdCourses) {
			try{
				//System.out.println(((OptionalCourse) c).getCourses());
				for(Course a: ((OptionalCourse) c).getCourses()){
					if(a.getName().equals(cs)) {
						if(debugMode) {
							System.out.println("  Found course in gen ed: " + a.getName());
						}
						return a;
					}
				}
			} catch(ClassCastException e){
				//System.out.println("Cannot do this");
			}
			
			if(c.getName().equals(cs)) {
				if(debugMode) {
					System.out.println("  Found course in gen ed: " + c.getName());
				}
				return c;
			}
			
		}
		
		if(!moreCourses.isEmpty()) {
			
			for(Course c: moreCourses) {
				if(c.getName().equals(cs)) {
					if(debugMode) {
						System.out.println("  Found course in more courses: " + c.getName());
					}
					return c;
				}
			}
		}
		
		if(debugMode) {
			System.out.println(" NOT FOUND - Creating one " + cs);
		}
		
		ret = i.getClassInfo(cs);
		moreCourses.add(ret);
		return ret;
	}
	

	/**
	 * Checks to see which classes have been completed
	 * 0 -> not taken
	 * 1 -> in progress
	 * 2 -> taken, passed
	 * 3 -> taken, failed
	 */
	public void setCompletionStatus(){
		for (Course c: classesTaken){
			for(Course b: majorCourses){
				if(c.getName().equalsIgnoreCase(b.getName())){
					if (!c.getGrade().equalsIgnoreCase("D") && !c.getGrade().equalsIgnoreCase("F")){
						b.setStatus(2);
						//add grade to course
					} else if (c.getGrade().equalsIgnoreCase("D") || c.getGrade().equalsIgnoreCase("F")){
						b.setStatus(3);
					}
					break;
				}
			}	
		}
	}

	/**
	 * Finds which classes have prereqs which do not and separate them into arraylists
	 * Treats OptionalCourse categories as separated classes
	 */
	
	//dont need to know all of the general ed files!!!!!!!!!
	public void findClassesWPrereqs(){
		for(Course c: majorCourses){
<<<<<<< HEAD
			//System.out.println(c.getName());
=======
>>>>>>> branch 'master' of https://github.com/goldblah/software-engineering
			/*if(c.getName().contains("_OptionalCourse")){
				for(Course v: ((OptionalCourse) c).getCourses()){
					if(!v.getPrereqs().isEmpty() && !v.getPrereqs().contains("")){
						coursesWPrereqs.add(v);
					} else {
						coursesWOPrereqs.add(v);
					}
				}
<<<<<<< HEAD
			}*/if(!c.getPrereqs().isEmpty() && !c.getPrereqs().contains("")){
=======
			}else*/ 
			if(!c.getPrereqs().isEmpty() && !c.getPrereqs().contains("")){
>>>>>>> branch 'master' of https://github.com/goldblah/software-engineering
				coursesWPrereqs.add(c);
			} else {
				coursesWOPrereqs.add(c);
			}
		}
	}

	public Student() throws FileNotFoundException{
		Input i = new Input("Input.txt");
		this.startSemester = i.startSemester;
		this.currentSemester = i.currentSemester;
		this.classesTaken = i.classesTaken;
		this.genEdCourses = i.genEdCourses;
		this.majorCourses = i.majorCourses;
		this.major = i.major;
		this.minor = i.minor;
		coursesWPrereqs = new ArrayList<>();
		coursesWOPrereqs = new ArrayList<>();
	}
}
