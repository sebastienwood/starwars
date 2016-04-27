import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Individu extends Schedule {

	public Individu(int ID) {
		super(ID);
	}
	
	/*
	 * TODO: fill method
	 */
	public void mutate(int taux_mut, LinkedList<Etoile> unemployed) {
		
	}

	/**
	 * Crossover between this individual and another
	 * @param i2: the other individual
	 * @return a new generation of individual
	 */
	public void crossover(Individu i2) {
		Individu newOne = new Individu(this.getID());
		
		return null;
	}
}
