/**
 * 
 */

/**
 * @author Sébastien
 * A plan is an association of an observation and a night
 */
public class Plan {

	private Etoile obs;
	private int night;
	private int debutplan;
	private int finplan;
	
	/**
	 * Constructor of a plan
	 * @param o: observation done
	 * @param n: night when the observation is done
	 */
	public Plan(Etoile o, int night, int deb, int fin) {
		this.obs = o;
		this.night = night;
		this.debutplan = deb;
		this.finplan = fin;
	}
	
	public int getNight() {
		return this.night;
	}
	
	public int getStar() {
		return obs.getID();
	}
	
	public int getValue() {
		return obs.getPriority();
	}
	
	public int getDeb() {
		return this.debutplan;
	}
	
	public int getFin() {
		return this.finplan;
	}
	
	public int getDur() {
		return finplan-debutplan;
	}
	
	/**
	 * A method to check if 2 plan are compatible
	 * @param p: the plan to compare to
	 * @return true if the plans are compatible
	 */
	public boolean isCompatible(Plan p) {
		boolean compatibility = true;
		if(this.night == p.getNight() && (this.debutplan>=p.getFin() || this.finplan<=p.getDeb())) {
			compatibility = false;
		}
		return compatibility;
	}
}
