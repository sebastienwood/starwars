
public class Recuit {

	Schedule solution;
	double temp;
	double taux_ref;
	double conditionarret_temp;
	
	public Recuit(Schedule sol, double temp, double ref, double arrettemp) {
		this.solution = sol;
		this.temp = temp;
		this.taux_ref = ref;
		this.conditionarret_temp = arrettemp;
	}
	
	public Recuit(Schedule sol) {
		this.solution = sol;
		this.temp = 80;
		this.taux_ref = 0.1;
		this.conditionarret_temp = 5;
	}
	
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

	public int getValue(boolean FF) {
		if(FF) {
			return solution.getValue(true);
		} else {
			return solution.getValue(false);
		}
	}
}
