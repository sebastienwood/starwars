public class Executable {

	public static void main(String[] args) {
		// TODO Launch multiple threads and manage them (1thread/heuristic)
		Genetic sol = new Genetic("ins_400_71_1", 10000);
		
		/*Pilot the treads*/
		sol.evolve();
		System.out.println(sol.getValue());
		sol.evolve();
		System.out.println(sol.getValue());
	}

}
