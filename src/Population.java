import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 * Population class for genetic algorithm
 * Schedule represent an individual
 * Genetic is the main algorithm
 */
public class Population {

	private LinkedList<Individu> population;
	
	/**
	 * Constructor for the genetic method
	 * @param src: name of the file stored in rsc folder
	 */
	public Population() {
		this.population = new LinkedList<Individu>();
	}
	
	/**
	 * Add an individual to the population
	 * @param s: schedule to add
	 */
	public void addIndividual(Individu s) {
		population.add(s);
	}
	
	/**
	 * Accessor to the best schedule of the population
	 * @return the best schedule regarding to the value
	 */
	private Individu getAlpha() {
		Iterator<Individu> i = population.iterator();
		Individu best = i.next();
		while(i.hasNext()) {
			Individu s = i.next();
			if(s.getValue()> best.getValue()) {
				best = s;
			}
		}
		return best;
	}
	
	public void evolve() {
		
	}
}
