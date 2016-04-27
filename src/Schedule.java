import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class Schedule {

	protected int ID;
	protected LinkedList<Plan> planning;
	
	/**
	 * Constructor for a schedule
	 * @param plans: a linkedlist of Plan
	 */
	public Schedule(int ID, LinkedList<Plan> plans) {
		this.ID = ID;
		this.planning = plans;
	}
	
	public Schedule(int ID) {
		this.ID = ID;
		this.planning = new LinkedList<Plan>();
	}
	
	/**
	 * A method to add a plan in the planning
	 * @param p: the plan to add
	 * @return true if not feasible
	 */
	public boolean addPlan(Plan p) {
		if(this.isCompatible(p)) {
			planning.add(p);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Accessor to the ID of the schedule
	 * @return the ID of the schedule
	 */
	protected int getID() {
		return this.ID;
	}
	
	protected int getSize() {
		return this.planning.size();
	}
	
	/**
	 * A method to check if the current schedule is valid
	 * @return true if the schedule is valid
	 */
	protected boolean isValid() {
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan p = i.next();
			if(!this.isCompatible(p)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A method to compute the value of the proposed schedule
	 * TODO: check if we achieve the minimal time for a star
	 * @return the value of the schedule. If not valid return 0
	 */
	public int computeValue() {
		if(this.isValid()) {
			int value = 0;
			Iterator<Plan> i = planning.iterator();
			while(i.hasNext()) {
				Plan p = i.next();
				value += p.getValue();
			}
			return value;
		} else {
			return 0;
		}
	}
	
	/**
	 * A method to check if the schedule is compatible with a given plan
	 * 	 Check:
	 * -same star not twice
	 * -all plan are compatible with each other
	 * @param p: the plan to check
	 * @return true if the plan is compatible
	 */
	protected boolean isCompatible(Plan p) {
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan pl = i.next();
			if(!p.equals(pl)) { //check if we dont iterate over the same plan
				if(!pl.isCompatible(p) || pl.getStar() == p.getStar()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean hasStar(int starID) {
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan p = i.next();
			if(p.getStar() == starID) {
				return true;
			}
		}
		return false;
	}
	
	public boolean tryAdding(Etoile o) {
		/*
		 * Recherche d'un plan compatible
		 * 
		 */
		Iterator<Plan> i = planning.iterator();
		while(i.hasNext()) {
			Plan p = i.next();
			
		}
	}
}
