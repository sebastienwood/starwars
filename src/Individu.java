
/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Individu extends Schedule {

	public Individu(int ID, int[] nuits, Etoile[] etoiles) {
		super(ID, nuits,etoiles);
	}
	
	/**
	 * A method to evolve an individual given a probability that a gene will change (for each gene)
	 * @param taux_mutation: a chance that a gene will change
	 */
	public void mutate(double taux_mutation) {
		for(int i = 0; i<etoiles.length;i++) {
			if(Math.random()<= taux_mutation) {
				nuits_choisies[i] = etoiles[i].randomNight();
			}
		}
	}

	/**
	 * Crossover between this individual and another
	 * @param i2: the other individual
	 * @return a new generation of individual
	 */
	public void crossover(Individu i2, double taux_uniforme) {
		for(int i = 0; i<nuits_choisies.length;i++){
			if(Math.random()<=taux_uniforme) {
				this.nuits_choisies[i] = i2.getStarNight(i);
			}
		}
	}
}
