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
		return nuits_choisies;
	}
	
	public Etoile[] getStars() {
		return etoiles;
	}
	
	/**
	 * Change a night chosen for a star
	 */
	public void randomChange() {
		int change = (int)Math.random()*nuits_choisies.length;
		nuits_choisies[change] = etoiles[change].randomNight();
	}
	
	/**
	 * Return the value of the schedule
	 * @return the value of the schedule
	 */
	public int getValue() {
		LinkedList<Night> nuits = new LinkedList<Night>();
		LinkedList<Integer> importance = new LinkedList<Integer>();
		int prises = 0;
		int nuit = 0;
		int valeur = 0;
		
		while(prises<etoiles.length) {
			/*Pour chaque etoile, parcourir les nuits*/
			for(int i = 0;i<etoiles.length;i++) {
				if(nuits_choisies[i] == nuit) {
					prises++;
					nuits.add(etoiles[i].getNight(nuit));
					importance.add(etoiles[i].getPriority());
				}
			}
			/*Ajouter la valeur max de cette nuit*/
			if(!nuits.isEmpty()) {
				valeur += OptimizedNightPlanner.value_FF(nuits, importance);
			}
			/*Nettoyer la linkedlist et itérer*/
			nuits.clear();
			nuit++;
		}
		return valeur;
	}
}
