/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public abstract class Solver {

	protected Observation[] data;
	
	public Solver(String src) {
		data = Filehandler.read(src);
	}
}
