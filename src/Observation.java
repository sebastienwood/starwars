import java.util.LinkedList;

/**
 * @author Sébastien
 *
 */
public class Observation {
	private int ID;
	private int priority;
	private LinkedList<Night> nuits;
	
	/**
	 * Constructor of an observation (AKA a star to study)
	 * @param ID: ID of the observation
	 * @param priority: priority of the observation
	 * @param nuits: nights when the observation is feasible
	 */
	public Observation(int ID, int priority, LinkedList<Night> nuits) {
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
	 * Accessor to the number of night
	 * @return the number of night when the observation is feasible
	 */
	public int getNumberOfNight() {
		return this.nuits.size();
	}
	
	/**
	 * Accessor to a night
	 * @param ID: index of the night 
	 * @return the night specified
	 */
	public Night getNight(int ID) {
		return this.nuits.get(ID);
	}
}
