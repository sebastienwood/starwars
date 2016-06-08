public class Executable {

	public static void main(String[] args) {

		Genetic sol = new Genetic("ins_400_71_1", 100);
		
		/*Pilot the treads*/
		sol.live(0.005);
		
		Schedule alpha = sol.getAlpha();
		Recuit rec = new Recuit(alpha, 500, 0.01, 0.00000001);
		System.out.println(rec.activate());
	}

}
