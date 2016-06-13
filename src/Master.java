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
	}
	
	
}
