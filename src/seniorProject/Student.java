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
	
	map m = new map();
	
	public ArrayList<Semester> getSemesters() {
		return semesters;
	}

	public void generateSchedule() {
		//set completion status
		setCompletionStatus();
		//separate classes into those with prereqs and those without
		findClassesWPrereqs();
		//generate the base map for the schedule
		//traverse the map to generate the schedule, store the courses in semester containers based on which semester they will occur in
	}

	private void checkSemesters(){

	}
	
	private void generateMap(){
		//Add those withoud prereq
		for(Course c: coursesWOPrereqs) {
			m.add(m, c);
		}
		
		//Add those with prerequistes
		for(Course c: coursesWPrereqs) {
			addWPrerq(c);
		}
	}
	
	private void addWPrerq(Course c) {
		
		//If prereq is empty, just add
		if (c.getPrereqs().isEmpty()) {
			m.add(m, c);
			return;
		}
		
		for(String p: c.getPrereqs()) {
			
			Course temp = helperSearch(p);
			
			addWPrerq(temp);
			
			m.search(temp).add(m, c);
		}
	}
	
	private Course helperSearch(String cs) {
		Course ret = null;
		
		//Search in coruses wo prereq
		for(Course c: coursesWOPrereqs) {
			if(c.getName().equals(cs)) {
				return c;
			}
		}
		
		//Search in courses with prereq
		for(Course c: coursesWPrereqs) {
			if(c.getName().equals(cs)) {
				return c;
			}
		}
		
		
		//Search in general ed
		for(Course c: genEdCourses) {
			if(c.getName().equals(cs)) {
				return c;
			}
		}
		
		return ret;
	}
	

	/**
	 * Checks to see which classes have been completed
	 * 0 -> not taken
	 * 1 -> in progress
	 * 2 -> taken, passed
	 * 3 -> taken, failed
	 */
	private void setCompletionStatus(){
		for (Course c: classesTaken){
			for(Course b: majorCourses){
				if(c.getName().equalsIgnoreCase(b.getName())){
					if (!c.getGrade().equalsIgnoreCase("D") && !c.getGrade().equalsIgnoreCase("F")){
						b.setStatus(2);
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
	private void findClassesWPrereqs(){
		for(Course c: majorCourses){
			if(c.getName().contains("_OptionalCourse")){
				for(Course v: ((OptionalCourse) c).getCourses()){
					if(!v.getPrereqs().isEmpty() && !v.getPrereqs().contains("")){
						coursesWPrereqs.add(v);
					} else {
						coursesWOPrereqs.add(v);
					}
				}
			}else if(!c.getPrereqs().isEmpty() && !c.getPrereqs().contains("")){
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

	public static void main(String[] args) throws FileNotFoundException {
		Student s = new Student();
		s.setCompletionStatus();
		s.findClassesWPrereqs();

		/*for (Course c: s.classesTaken){
			System.out.println(c.getName());
			System.out.println(c.getCH());
			System.out.println(c.getPriority());
			System.out.println(c.getStatus());
			System.out.println(c.getGrade());
			System.out.println();
		}*/
	}
}
