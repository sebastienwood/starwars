import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 * A class to represent a telescope schedule
 * AKA Individual in Genetic algorithm
 */
public class Schedule {

	protected int ID;
	protected LinkedList<Plan> planning;
	protected int value;
	
	/**
	 * Constructor for a schedule
	 * @param plans: a linkedlist of Plan
	 */
	public Schedule(int ID, LinkedList<Plan> plans) {
		this.ID = ID;
		this.planning = plans;
		this.computeValue();
	}
	
	/**
	 * Accessor to the ID of the schedule
	 * @return the ID of the schedule
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Accessor to the value of the schedule
	 * @return the value of the schedule
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * A method to check if the current schedule is valid
	 * Check:
	 * -same star not twice
	 * -all plan are compatible with each other
	 * @return true if the schedule is valid
	 */
	protected boolean isValid() {
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
	 * @return the value of the schedule. If not valid return 0
	 */
	protected void computeValue() {
		if(this.isValid()) {
			int value = 0;
			Iterator<Plan> i = planning.iterator();
			while(i.hasNext()) {
				Plan p = i.next();
				value += p.getValue();
			}
			this.value = value;
		} else {
			this.value = 0;
		}
	}
}
