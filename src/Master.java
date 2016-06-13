/**
 * 
 */

/**
 * @author Sébastien
 * The class master encapsulate all the metaheuristics representation of the problems, manage switching from one representation to another, and thus is fit for strategies implementation
 */
public class Master {

	private Etoile[] data;
	private Schedule alpha;
	
	private Genetic GA;
	private Recuit SA;
	private Colonie ACO;
	
	/**
	 * Constructor of the Master class, initialize each representation
	 * @param src: the instance we work on
	 */
	public Master(String src) {
		this.data = Filehandler.read(src);
		this.GA = new Genetic(data,500);
		this.SA = new Recuit(GA.getAlpha());
		this.ACO = new Colonie(data.length);
	}
	
	public void GA_activate(double timeinH) {
		/*Run the GA*/
		GA.live(timeinH);
		/*Update the others alphas*/
		SA.updateAlpha(GA.getAlpha());
		ACO.updateAlpha(GA.getAlpha());
	}
	
	public void SA_activate(double timeinH) {
		/*Run the SA*/
		SA.live(timeinH);
		/*Update the others alphas*/
		GA.updateAlpha(SA.getSchedule());
		ACO.updateAlpha(SA.getSchedule());
	}
	
	public void ACO_activate(double timeinH) {
		/*Run the ACO*/
		ACO.live(timeinH);
		/*Update the others alphas*/
		GA.updateAlpha(ACO.getAlpha());
		SA.updateAlpha(ACO.getAlpha());
	}
}
