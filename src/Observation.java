/**
 * @author Sébastien
 *
 */
public class Observation {
	private int ID;
	private int priority;
	private Night[] nuits;
	
	public Observation(int ID, int priority, Night[] nuits) {
		this.ID = ID;
		this.priority = priority;
		this.nuits = nuits;
	}
}
