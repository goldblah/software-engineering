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
	
	final int LIMIT_CREDIT_HOURS = 15;
	int current_priority = 0;
	
	public static int count = 0; //Debug info
	map m = new map(); //Map of the courses
	Input i = new Input("input.txt");
	boolean debugMode = false; //Change to false after release
	
	/**
	 * Change semester and represent it to te variable current_priority
	 */
	private void addToCurrentPriority() {
		//TODO
	}
	
	/**
	 * Given an priority it can say if thoose to matches, in order
	 * to determinate if a course can be taken in this semester
	 * @param p pirority of a semster
	 * @return true - it can be taken
	 * 		   fals - it can't
	 */
	private boolean matchPriority(int p) {
		//TODO
		return false;
	}
	
	public ArrayList<Semester> getSemesters() {
		return semesters;
	}
	

	public void generateSchedule() throws FileNotFoundException {
		
		//Debug info
		if(debugMode) {
			System.out.println("Generate Schedule called");
		}
		
		//set completion status
		setCompletionStatus();
		//separate classes into those with prereqs and those without
		findClassesWPrereqs();
		//generate the base map for the schedule
		//traverse the map to generate the schedule, store the courses in semester containers based on which semester they will occur in
		
		generateMap();
		
		boolean repete = true;
		
		while(repete) {
			//Get coruses from inside out
			//If the course is already taken, go to that course and get the Iam
			//Put all possilbe courses to a temp arraylist
			//Fill a semester with those courses
			//Change all those courses as completed
			//Repete until there are no more courses to add
			
			ArrayList<Course> possible = new ArrayList<Course>();
			int credit = 0;
			fillCourse(possible, m);
			Semester s = new Semester();
			orderArray(possible);
			
			
		}
	}
	
	/**
	 * It ordes an array list in accordane of the priority
	 * @param c An arraylist of courses
	 */
	private void orderArray(ArrayList<Course> c) {
		//TODO
	}
	
	private void fillCourse(ArrayList<Course> p, map m) {
		if(m.getIam() == null) return; 
		
		for(Course c: m.getIam()) {
			int stat = m.getCourse().getStatus();
			
			//Class not taken
			if( (stat == 0) || (stat == 3) ) {
				
				//Make sure prereq are completed
				if(m.search(c).canTake()) {
					int prio = m.getCourse().getPriority();
					
					//Can take this semester
					if(matchPriority(prio)) {
						p.add(c);
					}
				}
				
			}
			//Course taken, check the Iam
			else {
				fillCourse(p, m.search(c));
			}
		}
	}

	private void checkSemesters(){
		
	}
	
	/**
	 * Generates a map with all the courses the student has to take
	 * @throws FileNotFoundException
	 */
	private void generateMap() throws FileNotFoundException{
		
		//Debug info...
		if(debugMode) {
			System.out.println(++count + " Generate map called");
			
			//System.out.println(helperSearch("MATH104").getName());
			//return;
		}
		
		//Add those without prereq directly to the map
		for(Course c: coursesWOPrereqs) {
			if(debugMode) {
				System.out.println(++count + " Adding: " + c.getName() + " to " + " root");
			}
			
			
			m.add(m, c);
		}
		
		//Debug info
		if(debugMode) {
			System.out.println(++count + " Printing map so far:");
			m.toPrint();
		}
		
		//Add those with prerequistes
		for(Course c: coursesWPrereqs) {
			addWPrerq(c);
		}
	}
	
	/**
	 * Add courses with prerequisites to the map.
	 * It checks the prerequistes and recusivly addem to the map
	 * 
	 * @param c Course to add
	 * @throws FileNotFoundException
	 */
	private void addWPrerq(Course c) throws FileNotFoundException {
		
		//Debug info
		if(debugMode) {
			System.out.println(++count + " AddWPrereq called with course " + c.getName());
		}
		
		//If prereq is empty, just add
		if (c.getPrereqs().isEmpty()) {
			
			//Debug info
			if(debugMode) {
				System.out.println(++count + " No prerequiste, adding..");
			}
			if(debugMode) {
				System.out.println(++count + " Adding: " + c.getName() + " to root");
			}
			
			
			m.add(m, c);
			return;
		}
		
		
		// c.getCE() 
		//TODO
		
		
		if ( c.getEitherOr() ) {
			ArrayList<Course> options = new ArrayList<Course>();
			
			if(debugMode) {
				System.out.println(++count + " Course prerequiste are Either or "  + c.getName());
			}
			
			for(String p: c.getPrereqs()) {
				
				//If no prereq, just add it to the root and quit
				if(p.trim().isEmpty()) {
					if(debugMode) {
						System.out.println(++count + " Didn't have prereq "  + c.getName());
					}
					
					if(debugMode) {
						System.out.println(++count + " Adding: " + c.getName() + " to root");
					}
					
					
					m.add(m,  c);
					return;
				}
				
				//Get the prerequisite course ******
				Course temp = helperSearch(p);
				
				map t = m.search(temp);
				
				//Is already in the map, select that one
				if(t != null) {
					if(debugMode) {
						System.out.println(++count + "  A prereq was already in the map "  + t.getCourse().getName());
					}
					//Add it recursivly
					addWPrerq(t.getCourse());
					
					//Debug info
					if(debugMode) {
						System.out.println(++count + " Adding " + c.getName() + " to " + t.getCourse().getName());
					}
					
					//Connect courses
					m.search(t.getCourse()).add(m, c);
					return;
				}
				
				else {
					
					options.add(temp);
				}
			}
			
			if(debugMode) {
				System.out.println(++count + " No prereq was in the map, choosing: "  + options.get(0).getName());
			}
			//For now, add the first options
			Course toAdd = options.get(0);
			addWPrerq(toAdd);
			
			//Debug info
			if(debugMode) {
				System.out.println(++count + " Adding " + c.getName() + " to " + toAdd.getName());
			}
			
			m.search(toAdd).add(m, c);
			return;
		}
		
		/* Prerequistes are in string format.
		*  For each one:
		*   - Get the coruse with the name
		*   - Add it recursivly
		*   - Connect the prerequiste with the 'main' class
		*/
		for(String p: c.getPrereqs()) {
			
			//If no prereq, just add it to the root and quit
			if(p.trim().isEmpty()) {
				//Debug info
				if(debugMode) {
					System.out.println(++count + " Adding " + c.getName() + " to " + " root");
				}
				
				m.add(m,  c);
				return;
			}
			
			//Get the prerequisite course
			Course temp = helperSearch(p);
			
			//Debug info
			if(debugMode) {
				System.out.println(++count + " Found prereq string: " + p);
				System.out.println(++count + " Found prereq coruse: " + temp.getName());
				
			}
			
			//Add it recursivly
			addWPrerq(temp);
			
			//Debug info
			if(debugMode) {
				System.out.println(++count + " Adding: " + c.getName() + " to " + temp.getName());
			}
			
			//Connect courses
			m.search(temp).add(m, c);
		}
	}
	
	
	/**
	 * Searchs in eveary arraylist for a given course, and returns it or creates 
	 * a course with the given string
	 * 
	 * @param cs String of the course to search
	 * @return The course in the array
	 * @throws FileNotFoundException
	 */
	private Course helperSearch(String cs) throws FileNotFoundException {
		
		//Debug info
		if(debugMode) {
			System.out.println(++count + " helperSearch called with string " + cs);
		}
		
		Course ret = null;
		
		//Search in coruses wo prereq
		for(Course c: coursesWOPrereqs) {
			if(c.getName().equals(cs)) {
				
				//Debug info
				if(debugMode) {
					System.out.println(++count + "   Found course in WO Prereq " + c.getName());
				}
				
				return c;
			}
		}
		
		//Search in courses with prereq
		for(Course c: coursesWPrereqs) {
			
			if(c.getName().equals(cs)) {
				//Derbug info
				if(debugMode) {
					System.out.println(++count + "   Found course in With Prereq: " + c.getName());
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
						
						//Debug info
						if(debugMode) {
							System.out.println(++count + "   Found course in gen ed: " + a.getName());
						}
						
						return a;
					}
				}
			} catch(ClassCastException e){
				//System.out.println("Cannot do this");
			}
			
			if(c.getName().equals(cs)) {
				
				//Debug info
				if(debugMode) {
					System.out.println(++count + "   Found course in gen ed: " + c.getName());
				}
				
				return c;
			}
			
		}
		
		if(!moreCourses.isEmpty()) {
			
			for(Course c: moreCourses) {
				if(c.getName().equals(cs)) {
					
					//Debug info
					if(debugMode) {
						System.out.println(++count + "   Found course in more courses: " + c.getName());
					}
					
					return c;
				}
			}
		}
		
		//Debug info
		if(debugMode) {
			System.out.println(++count + "  NOT FOUND - Creating one " + cs);
		}
		
		
		//At this point, id didn't found the course in nither of the 
		// arrayList, se we call the getclassinfo on order to create a new class and add it
		// to a new arraylist, at the end we return the course
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
	public void findClassesWPrereqs(){
		for(Course c: majorCourses){
			/*if(c.getName().contains("_OptionalCourse")){
				for(Course v: ((OptionalCourse) c).getCourses()){
					if(!v.getPrereqs().isEmpty() && !v.getPrereqs().contains("")){
						coursesWPrereqs.add(v);
					} else {
						coursesWOPrereqs.add(v);
					}
				}
			}else*/ 
			if(!c.getPrereqs().isEmpty() && !c.getPrereqs().contains("")){
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
