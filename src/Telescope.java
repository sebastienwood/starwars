import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 *
 */
public class Telescope {

	private LinkedList<Plan> planning;
	
	public Telescope() {
		this.planning = new LinkedList<Plan>();
	}
	
	/**
	 * Add an observation to the planning
	 * @param o: the observation to add
	 * @return false if the observation is incompatible
	 */
	public boolean add(Plan p) {
		//TODO: process of adding a plan in the telescope schedule with review of all plan already on schedule
		return false;
	}
	
	/**
	 * A method to compute the value of the proposed schedule
	 * @return
	 */
	public int getValue() {
		int value = 0;
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan p = i.next();
			value += p.getValue();
		}
		return value;
	}
}
