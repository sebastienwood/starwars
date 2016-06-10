import java.util.LinkedList;

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
		int change = (int)(Math.random()*nuits_choisies.length);
		nuits_choisies[change] = etoiles[change].randomNight();
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
}
