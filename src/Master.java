/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Master {

	private Etoile[] data;
	
	public Master(String src) {
		this.data = Filehandler.read(src);
	}
	
	public Genetic initializeGA(int popsize) {
		return new Genetic(data, popsize);
	}
	
	public void 
	
	public void switchtoGA(Schedule alpha) {
		
	}
}
