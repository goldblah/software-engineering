package seniorProject;

public class Map_tester {

	public static void main(String[] args) {
		//Adding courses
		Course c1 = new Course("CS101");
		Course c2 = new Course("CS204");
		Course c3 = new Course("CS304");
		Course mth1 = new Course("MATH101"); //Needs c3
		Course c4 = new Course("CS402"); //Needs mth1

		//Creating map
		map cs = new map();

		//Adding three courses
		cs.add(cs, c1);
		cs.add(cs, c2);
		cs.add(cs, c3);

		//Testing search method
		System.out.print("SEARCH: ");
		boolean search_test[] = new boolean[4];
		search_test[0] = c1.equals( cs.search(c1).getCourse() );
		search_test[1] = c2.equals( cs.search(c2).getCourse() );
		search_test[2] = c3.equals( cs.search(c3).getCourse() );
		search_test[3] = cs.search(c4) == null;

		test(search_test);

		//Add prerequisites
		cs.search(c3).add(cs, mth1);
		cs.search(mth1).add(cs, c4);
		cs.search(c2).add(cs, c4);
		
		cs.toPrint();
		
		cs.add(cs, c1);
		cs.add(cs, c1);
		cs.add(cs, c1);
		cs.add(cs, c1);
		
		System.out.println("Again");
		cs.toPrint();

	}

	public static void test(boolean[] t) {
		int fails = 0;

		for(boolean b: t) {
			if(!b) fails++;
		}

		if (fails != 0) System.out.println(fails + " error(s)");
		else System.out.println("OK!");
	}
}
