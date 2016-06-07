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
	
	public Schedule(int ID, int[] nuits, Etoile[] etoiles) {
		this.ID = ID;
		this.nuits_choisies = nuits;
		this.etoiles = etoiles;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String toString() {
		String str = "";
		for(int i = 0; i<nuits_choisies.length;i++){
			str += nuits_choisies[i]+" ";
		}
		return str;
	}
	
	public int getStarNight(int i) {
		return nuits_choisies[i];
	}
	
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
