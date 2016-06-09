public class Executable {

	public static void main(String[] args) {

		Genetic sol = new Genetic("ins_400_71_1", 3000);
		
		/*Pilot the treads*/
		sol.live(2);
		
		/*
		Schedule alpha = sol.getAlpha();
		Recuit rec = new Recuit(alpha, 80, 0.1, 5);
		System.out.println(rec.activate());
		*/
	}

}
