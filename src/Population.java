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
		int bestvalue = best.getValue(true);
		while(i.hasNext()) {
			Individu s = i.next();
			int challenger = s.getValue(true);
			if(challenger> bestvalue) {
				best = s;
				bestvalue = challenger;
			}
		}
		return best;
	}
	
	/**
	 * A method to confront Individual that keeps the best
	 * @param size: the number of individual confronting
	 * @return the one with the best value
	 */
	public Individu tournoi(int size) {
		Population p = new Population();
		for(int i = 0; i<size;i++) {
			p.addIndividual(population.get((int) Math.random()*population.size()));
		}
		return p.getAlpha();
	}

	public boolean sameAlpha(Population newGen) {
		if(this.getAlpha() == newGen.getAlpha()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean samePop(Population newGen) {
		Iterator<Individu> i = population.iterator();
		while(i.hasNext()) {
			Individu ind = i.next();
			if(!newGen.has(ind)) {
				return false;
			}
		}
		return true;
	}

	private boolean has(Individu in) {
		Iterator<Individu> i = population.iterator();
		while(i.hasNext()) {
			Individu ind = i.next();
			if(ind.equals(in)) {
				return true;
			}
		}
		return false;
	}

	public void print() {
		Iterator<Individu> i = population.iterator();
		while(i.hasNext()) {
			Individu ind = i.next();
			System.out.println(ind.toString());
		}
	}
}
