import java.util.LinkedList;

/**
 * @author Sébastien
 *
 */
public class Genetic {

	private LinkedList<Schedule> population;
	private Observation[] data;
	
	/**
	 * Constructor for the genetic method
	 * @param src: name of the file stored in rsc folder
	 */
	public Genetic(String src) {
		this.data = Filehandler.read(src);
		this.population = new LinkedList<Schedule>();
	}
	
	/**
	 * Methods for the genetic algorithm
	 * For each star pick a random night when the observation is feasible
	 * @param pop: size of the initial population
	 */
	public void genetic_initial(int pop) {
		for(int i = 0; i<pop;i++) {
			LinkedList<Plan> p = new LinkedList<Plan>();
			for(int j = 0; j <data.length;j++) {
				p.add(data[j].randomPlan());
			}
			population.add(new Schedule(p));
		}
	}
	
	private void emjambement() {
		
	}
	
	private void mutation() {
		
	}
}
