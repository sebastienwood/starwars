
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
	public Individu mutate(double taux_mutation) {
		int[] nuits = this.getStarsNights();
		for(int i = 0; i<etoiles.length;i++) {
			if(Math.random()<= taux_mutation) {
				nuits[i] = etoiles[i].randomNight();
			}
		}
		return new Individu(this.ID,nuits,this.etoiles);
	}

	/**
	 * Crossover between this individual and another
	 * @param i2: the other individual
	 * @return a new generation of individual
	 */
	public Individu crossover(Individu i2, double taux_uniforme) {
		int[] nuits = this.getStarsNights();
		for(int i = 0; i<nuits_choisies.length;i++){
			if(Math.random()<=taux_uniforme) {
				nuits[i] = i2.getStarNight(i);
			}
		}
		return new Individu(this.ID,nuits,this.etoiles);
	}
}
