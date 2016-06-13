import java.util.LinkedList;
import java.util.Random;

/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class Schedule {

	protected int ID;
	protected int[] nuits_choisies;
	protected Etoile[] etoiles;
	/*Pour chaque etoiles, une nuit (dans laquelle l'étoile est visible !) est associée*/
	
	/**
	 * Constructor of a schedule
	 * @param ID: ID of the schedule
	 * @param nuits: the night we do the observation of the star
	 * @param etoiles: the star database
	 */
	public Schedule(int ID, int[] nuits, Etoile[] etoiles) {
		this.ID = ID;
		this.nuits_choisies = nuits;
		this.etoiles = etoiles;
	}
	
	public Schedule(Schedule solution) {
		this.ID = solution.getID()+1;
		this.nuits_choisies = solution.getStarsNights().clone();
		this.etoiles = solution.getStars();
	}

	/**
	 * Return the ID of the schedule
	 * @return the ID of the schedule
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Give a string representation of a schedule
	 * @return a string representation of the schedule
	 */
	public String toString() {
		String str = "";
		for(int i = 0; i<nuits_choisies.length;i++){
			str += nuits_choisies[i]+" ";
		}
		return str;
	}
	
	/**
	 * Return the night chosen for a star
	 * @param i: the star we want to know more about
	 * @return the night chosen for this star
	 */
	public int getStarNight(int i) {
		return nuits_choisies[i];
	}
	
	public int[] getStarsNights() {
		return nuits_choisies.clone();
	}
	
	public Etoile[] getStars() {
		return etoiles;
	}
	
	/**
	 * Change a night chosen for a star
	 */
	public void randomChange() {
		Random rand = new Random();
		int change = rand.nextInt(nuits_choisies.length);
		nuits_choisies[change] = etoiles[change].randomNight();
	}
	
	public void print() {
		int valeur = 0;
		int valeurmax = 0;
		for(int i = 0; i<etoiles.length;i++) {
			valeurmax+=etoiles[i].getPriority();
		}
		
		int nuitact = 0;
		while(valeur<valeurmax) {
			System.out.println("Nuit "+nuitact);
			for(int i = 0; i <nuits_choisies.length;i++) {
				if(nuits_choisies[i] == nuitact) {
					System.out.println("Etoile "+i);
					valeur+=etoiles[i].getPriority();
				}
			}
			nuitact++;
		}
	}
	
	/**
	 * Return the value of the schedule
	 * @return the value of the schedule
	 */
	public int getValue(boolean FF) {
		int valeur = 0;
		int valeurmax = 0;
		int valeurPlanifiee = 0;
		for(int i = 0; i<etoiles.length;i++) {
			valeurmax+=etoiles[i].getPriority();
		}
		
		int nuit_actuelle = 0;
		while(valeur<valeurmax) {
			/*Constituer les linked list utiles: night importance starID*/
			LinkedList<Night> nights = new LinkedList<Night>();
			LinkedList<Integer> imp = new LinkedList<Integer>();
			LinkedList<Integer> starID = new LinkedList<Integer>();
			/*Chercher les etoiles de la nuit*/
			for(int i = 0; i <nuits_choisies.length;i++) {
				if(nuits_choisies[i] == nuit_actuelle) {
					nights.add(etoiles[i].getNight(nuit_actuelle));
					imp.add(etoiles[i].getPriority());
					valeur += etoiles[i].getPriority();
					starID.add(etoiles[i].getID());
				}
			}
			if(!nights.isEmpty()) {
				valeurPlanifiee += OptimizedNightPlanner.value_FF(nights, imp, starID);
			}
			nuit_actuelle++;
		}
		
		return valeurPlanifiee;
	}
	
	/**
	 * A method to evolve an individual given a probability that a gene will change (for each gene)
	 * @param taux_mutation: a chance that a gene will change
	 */
	public Schedule mutate(double taux_mutation) {
		int[] nuits = this.getStarsNights();
		for(int i = 0; i<etoiles.length;i++) {
			if(Math.random()<= taux_mutation) {
				nuits[i] = etoiles[i].randomNight();
			}
		}
		return new Schedule(this.ID,nuits,this.etoiles);
	}

	/**
	 * Crossover between this individual and another
	 * @param i2: the other individual
	 * @return a new generation of individual
	 */
	public Schedule crossover(Schedule i2, double taux_uniforme) {
		int[] nuits = this.getStarsNights();
		for(int i = 0; i<nuits_choisies.length;i++){
			if(Math.random()<=taux_uniforme) {
				nuits[i] = i2.getStarNight(i);
			}
		}
		return new Schedule(this.ID,nuits,this.etoiles);
	}
}
