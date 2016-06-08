
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
	
	private boolean critMetropolis(double delta, double temp) {
		boolean crit = false;
		if(delta>=0) {
			crit = true;
		}
		else
		{
			if(Math.random()<=Math.exp(-delta/temp)) {
				crit = true;
				System.out.println("metropolis de merde");
			}
		}
		return crit;
	}
	
	public int activate() {
		while(temp>conditionarret_temp) {
			int best = solution.getValue();
			Schedule newOne = new Schedule(solution);
			newOne.randomChange();
			System.out.println(solution.toString());
			System.out.println(newOne.toString());
			if(this.critMetropolis(newOne.getValue()-best, temp)) {
				solution = newOne;
			}
			temp *= (1-taux_ref);
		}
		return solution.getValue();
	}
}
