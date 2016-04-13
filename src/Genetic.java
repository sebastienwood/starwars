import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Genetic extends Solver {
	
	private Population pop;
	
	/**
	 * Constructor for the Genetic algorithm
	 * @param src: source file
	 * @param popsize: initial pop size wished
	 */
	public Genetic(String src, int popsize) {
		super(src);
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			LinkedList<Plan> p = new LinkedList<Plan>();
			for(int j = 0; j<data.length; j++) {
				p.add(data[j].randomPlan());
			}
			Individu s = new Individu(i, p);
			pop.addIndividual(s);
		}
	}
	
}
