import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Sébastien
 * Represent a potential observation AKA a star, with a value (priority) and nights when it is feasible
 */
public class Etoile {
	private int ID;
	private int priority;
	private LinkedList<Night> nuits;
	
	/**
	 * Constructor of an observation (AKA a star to study)
	 * @param ID: ID of the observation
	 * @param priority: priority of the observation
	 * @param nuits: nights when the observation is feasible
	 */
	public Etoile(int ID, int priority, LinkedList<Night> nuits) {
		this.ID = ID;
		this.priority = priority;
		this.nuits = nuits;
	}
	
	/**
	 * Accessor to the ID of the observation
	 * @return the ID of the observation
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Accessor to the priority of the observation
	 * @return: the priority of the observation
	 */
	public int getPriority() {
		return this.priority;
	}
	
	/**
	 * Test if the observation is feasible a specified night
	 * @param night: the night we want to test
	 * @return true if feasible
	 */
	public boolean feasibleAt(int night) {
		Iterator<Night> i = nuits.iterator();
		while(i.hasNext()) {
			Night n = i.next();
			if(n.getID() == night) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Accessor to a night
	 * @param night: number of the night 
	 * @return the night specified, null if non feasible
	 */
	public Night getNight(int night) {
		Iterator<Night> i = nuits.iterator();
		while(i.hasNext()) {
			Night n = i.next();
			if(n.getID() == night) {
				return n;
			}
			if(n.getID() > night) {
				return null;
			}
		}
		return null;
	}
}
