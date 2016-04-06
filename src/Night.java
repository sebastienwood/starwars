/**
 * 
 * @author Sébastien
 *
 */
public class Night {
	private int ID;
	private int debut;
	private int fin;
	
	/**
	 * Constructor of a night (AKA observation window for a star)
	 * @param id: ID of the night
	 * @param deb: beginning of the night
	 * @param fin: end of the night
	 */
	public Night(int id, int deb, int fin) {
		this.ID = id;
		this.debut = deb;
		this.fin = fin;
	}
	
	/**
	 * Accessor to night ID
	 * @return: the ID of the night
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Accessor to night beginning time
	 * @return: the beginning time of the night
	 */
	public int getDebut() {
		return this.debut;
	}
	
	/**
	 * Accessor to night end time
	 * @return: the end time of the night
	 */
	public int getFin() {
		return this.fin;
	}
	
	/**
	 * Accessor to night duration
	 * @return: the duration of the night
	 */
	public int getDuree() {
		return this.fin-this.debut;
	}
}
