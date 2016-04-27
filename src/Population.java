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
	
	public int getSize() {
		return population.size();
	}
	
	/**
	 * Accessor to the best schedule of the population
	 * @return the best schedule regarding to the value
	 */
	public Individu getAlpha() {
		Iterator<Individu> i = population.iterator();
		Individu best = i.next();
		int bestvalue = best.computeValue();
		while(i.hasNext()) {
			Individu s = i.next();
			int challenger = s.computeValue();
			if(challenger> bestvalue) {
				best = s;
				bestvalue = challenger;
			}
		}
		System.out.println(best.getSize());
		return best;
	}
	
	public Individu tournoi(int size) {
		Population p = new Population();
		for(int i = 0; i<size;i++) {
			p.addIndividual(population.get((int) Math.random()*population.size()));
		}
		return p.getAlpha();
	}
}
