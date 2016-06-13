/**
 * 
 */

/**
 * @author Sébastien
 * The class master encapsulate all the metaheuristics representation of the problems, manage switching from one representation to another, and thus is fit for strategies implementation
 */
public class Master {

	private final Etoile[] data;
	private Schedule alpha;
	
	private Genetic GA;
	private Recuit SA;
	private Colonie ACO;
	
	public Master(String src) {
		this.data = Filehandler.read(src);
	}
	
	
}
