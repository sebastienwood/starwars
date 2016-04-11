/**
 * 
 */

/**
 * @author Sébastien
 * A plan is an association of an observation and a night
 */
public class Plan {

	private Observation obs;
	private Night night;
	
	/**
	 * Constructor of a plan
	 * @param o: observation done
	 * @param n: night when the observation is done
	 */
	public Plan(Observation o, Night n) {
		this.obs = o;
		this.night = n;
	}
	
	public int getNight() {
		return night.getID();
	}
	
	public int getStar() {
		return obs.getID();
	}
	
	public int getValue() {
		return obs.getPriority();
	}
	
	public int getDeb() {
		return night.getDebut();
	}
	
	public int getFin() {
		return night.getFin();
	}
	
	public int getDur() {
		return night.getDuree();
	}
	
	/**
	 * A method to check if 2 plan are compatible
	 * TODO: include the 1/2 condition
	 * @param p: the plan to compare to
	 * @return true if the plans are compatible
	 */
	public boolean isCompatible(Plan p) {
		boolean compatibility = true;
		if(this.night.getID() == p.getNight() && (this.night.getDebut()>=p.getFin() || this.night.getFin()<=p.getDeb())) {
			compatibility = false;
		}
		return compatibility;
	}
}
