import java.util.LinkedList;

/**
 * @author Sébastien
 *
 */
public class Telescope {

	private LinkedList<Observation> planning;
	
	public Telescope() {
		this.planning = new LinkedList<Observation>();
	}
	
	/**
	 * Add an observation to the planning
	 * @param o: the observation to add
	 * @return false if the observation is incompatible
	 */
	public boolean add(Observation o) {
		return false;
	}
}
