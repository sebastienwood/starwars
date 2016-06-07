import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author Sébastien
 *
 */
public class OptimizedNightPlanner {

	/**
	 * A method to compute the value of a night, planning the stars with the first finished
	 * @param donnees : tableau de tableaux representants les donnees d'une etoile pour une nuit
	 */
	public static int value_FF(LinkedList<Night> data, LinkedList<Integer> importance) {		
		Night[] donnees = new Night[data.size()];
		int[] imp = new int[data.size()];
		for(int ind = 0;ind<data.size();ind++) {
			donnees[ind] = data.get(ind);
			imp[ind] = importance.get(ind);
		}
		
		/*Trier par ordre de fin croissante*/
		for(int i = 0; i<donnees.length-1;i++) {
			for(int j = i+1; j<donnees.length;j++) {
				if(donnees[i].getFin() < donnees[j].getFin()) {
					Night n1 = donnees[i];
					Night n2 = donnees[j];
					donnees[j] = n1;
					donnees[i] = n2;
					Integer i1 = imp[i];
					Integer i2 = imp[j];
					imp[j] = i1;
					imp[i] = i2;
				}
			}
		}
		
		/*Prendre ceux qui sont compatibles*/
		LinkedList<Integer> bons = new LinkedList<Integer>();
		bons.add(0);
		int last = 0;
		for(int i = 1;i<donnees.length;i++) {
			if(donnees[last].getFin()<donnees[i].getDebut()) {
				bons.add(i);
			}
		}
		
		/*Compter leur valeur*/
		int valeur = 0;
		Iterator<Integer> i = bons.iterator();
		while(i.hasNext()) {
			int index = i.next();
			valeur += imp[index];
		}
		
		return valeur;
	}
}
