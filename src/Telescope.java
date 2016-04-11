import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class Telescope {

	private LinkedList<Plan> planning;
	
	public Telescope() {
		this.planning = new LinkedList<Plan>();
	}
	
	/**
	 * Add an observation to the planning
	 * @param o: the observation to add
	 */
	public void add(Plan p) {
		this.planning.add(p);
	}
	
	/**
	 * A method to check if the current schedule is valid
	 * Check:
	 * -same star not twice
	 * -all plan are compatible with each other
	 * @return true if the schedule is valid
	 */
	public boolean isValid() {
		boolean valid = true;
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan p = i.next();
			Iterator<Plan> i2 = planning.iterator();
			while(i2.hasNext()) {
				Plan p2 = i2.next();
				if(p.getStar() == p2.getStar() || !p.isCompatible(p2)) {
					valid = false;
					break;
				}
			}
			if(!valid) { break; }
		}
		return valid;
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
