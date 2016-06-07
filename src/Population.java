import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 * Population class for genetic algorithm
 * Schedule represent an individual
 * Genetic is the main algorithm
 */
public class Population {

	/* Un individu représente un schedule*/
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
		int bestvalue = best.getValue();
		while(i.hasNext()) {
			Individu s = i.next();
			int challenger = s.getValue();
			if(challenger> bestvalue) {
				best = s;
				bestvalue = challenger;
			}
		}
		return best;
	}
	
	public Individu tournoi(int size) {
		Population p = new Population();
		for(int i = 0; i<size;i++) {
			p.addIndividual(population.get((int) Math.random()*population.size()));
		}
		System.out.println(p.getAlpha().toString());
		return p.getAlpha();
	}
}
