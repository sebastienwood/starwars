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
	private LinkedList<Night> data;
	
	/**
	 * Constructor of an observation (AKA a star to study)
	 * @param ID: ID of the observation
	 * @param priority: priority of the observation
	 * @param nuits: nights when the observation is feasible
	 */
	public Etoile(int ID, int priority, LinkedList<Night> data) {
		this.ID = ID;
		this.priority = priority;
		this.data = data;
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
	 * Return the ID of a night when the star is visible
	 */
	public int randomNight() {
		Random rand = new Random();
		return data.get(rand.nextInt(data.size())).getID();
	}
	
	/**
	 * A method to access a specified ID night
	 * @param index : the n° of the night we want to access
	 * @return the night
	 */
	public Night getNightID(int index) {
		Night n = null;
		Iterator<Night> i = data.iterator();
		while(i.hasNext()) {
			n = i.next();
			if(n.getID() == index) {
				break;
			}
		}
		return n;
	}
	
	public int getNbNight() {
		return this.data.size();
	}
	
	public Night getNight(int night) {
		return this.data.get(night);
	}
}
