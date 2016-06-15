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
	private LinkedList<Schedule> population;
	
	/**
	 * Constructor for the genetic method
	 * @param src: name of the file stored in rsc folder
	 */
	public Population() {
		this.population = new LinkedList<Schedule>();
	}
	
	/**
	 * Add an individual to the population
	 * @param s: schedule to add
	 */
	public void addIndividual(Schedule s) {
		population.add(s);
	}
	
	public int getSize() {
		return population.size();
	}
	
	public Schedule getIndividual(int i) {
		return population.get(i);
	}
	
	/**
	 * Accessor to the best schedule of the population
	 * @return the best schedule regarding to the value
	 */
	public Schedule getAlpha() {
		Iterator<Schedule> i = population.iterator();
		Schedule best = i.next();
		int bestvalue = best.getValue();
		while(i.hasNext()) {
			Schedule s = i.next();
			int challenger = s.getValue();
			//System.out.print(challenger+" ");
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
	public Schedule tournoi(int size) {
		Population p = new Population();
		for(int i = 0; i<size;i++) {
			p.addIndividual(population.get((int)(Math.random()*population.size())));
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
		Iterator<Schedule> i = population.iterator();
		while(i.hasNext()) {
			Schedule ind = i.next();
			if(!newGen.has(ind)) {
				return false;
			}
		}
		return true;
	}

	private boolean has(Schedule in) {
		Iterator<Schedule> i = population.iterator();
		while(i.hasNext()) {
			Schedule ind = i.next();
			if(ind.equals(in)) {
				return true;
			}
		}
		return false;
	}

	public void print() {
		Iterator<Schedule> i = population.iterator();
		while(i.hasNext()) {
			Schedule ind = i.next();
			System.out.println(ind.toString());
		}
	}

	public void switchRandom(Schedule newAlpha) {
		population.remove((int)(Math.random()*population.size()));
		population.add(newAlpha);
	}

	public void clear() {
		population.clear();	
	}
}
