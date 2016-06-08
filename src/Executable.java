public class Executable {

	public static void main(String[] args) {

		Genetic sol = new Genetic("ins_400_71_1", 1000);
		
		/*Pilot the treads*/
		sol.live(0.01);
	}

}
