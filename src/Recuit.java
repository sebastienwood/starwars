
public class Recuit {

	Schedule solution;
	double temp;
	double taux_ref;
	double conditionarret_temp;
	
	/**
	 * Constructor of a SA full parametrized
	 * @param sol: schedule to improve
	 * @param temp: initial temperature
	 * @param ref: cooling rate
	 * @param arrettemp: stop temperature
	 */
	public Recuit(Schedule sol, double temp, double ref, double arrettemp) {
		this.solution = sol;
		this.temp = temp;
		this.taux_ref = ref;
		this.conditionarret_temp = arrettemp;
	}
	
	/**
	 * Default constructor of a recuit
	 * @param sol: schedule to improve
	 */
	public Recuit(Schedule sol) {
		this.solution = sol;
		this.temp = 80;
		this.taux_ref = 0.1;
		this.conditionarret_temp = 5;
	}
	
	/**
	 * A method to know if we aknoledge the new solution
	 * @param delta: delta in result
	 * @param temp: current temperature
	 * @return true if we accept the new schedule
	 */
	private boolean critMetropolis(double delta, double temp) {
		boolean crit = false;
		if(delta>=0) {
			crit = true;
		}
		else
		{
			if(Math.random()<=Math.exp(-delta/temp)) {
				crit = true;
			}
		}
		return crit;
	}
	
	/**
	 * Do one iteration of the SA algorithm
	 * @return the new value
	 */
	public int activate() {
		Schedule bestSchedule = solution;
		double temptemp = this.temp;
		while(temptemp>conditionarret_temp) {
			int best = solution.getValue(true);
			Schedule newOne = new Schedule(solution);
			newOne.randomChange();
			if(newOne.getValue(true) > bestSchedule.getValue(true)){
				bestSchedule = newOne;
			}
			if(this.critMetropolis(newOne.getValue(true)-best, temptemp)) {
				solution = newOne;
			}
			temptemp *= (1-taux_ref);
		}
		this.solution = bestSchedule;
		return bestSchedule.getValue(true);
	}

	/**
	 * A method to get the value of the SA
	 * @param FF: true if we want to approximate the value (a lot faster)
	 * @return the value of the schedule
	 */
	public int getValue(boolean FF) {
		if(FF) {
			return solution.getValue(true);
		} else {
			return solution.getValue(false);
		}
	}
	
	/**
	 * Accessor to the schedule
	 * @return the current schedule
	 */
	public Schedule getSchedule() {
		return this.solution;
	}

	/**
	 * Give life to the SA for a given time
	 * @param timeinH: a time in hour
	 */
	public void live(double timeinH) {
		double fin = System.currentTimeMillis()+(timeinH*3600*1000);
		int gen = 0;
		while(System.currentTimeMillis()<fin) {
			this.activate();
			gen++;
			//System.out.println(pop.getAlpha().toString());
			System.out.println(gen+" "+(fin-System.currentTimeMillis())+" "+this.getValue(true));
		}
		System.out.println(this.getValue(false));	
	}
	
	/**
	 * Change the current schedule by another
	 * @param s: the new schedule
	 */
	public void updateAlpha(Schedule s) {
		this.solution = s;
	}
}
