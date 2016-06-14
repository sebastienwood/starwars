/**
 * 
 * @author Sébastien
 *
 */
public class Night {
	private int ID;
	private int debut;
	private int fin;
	private int duree;
	
	/**
	 * Constructor of a night (AKA observation window for a star)
	 * @param id: ID of the night
	 * @param deb: beginning of the night
	 * @param fin: end of the night
	 * @param duree: min duration of actual observation
	 */
	public Night(int id, int deb, int fin, int duree) {
		this.ID = id;
		this.debut = deb;
		this.fin = fin;
		this.duree = duree;
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
		return this.duree;
	}
	
	public int[] planCentered() {
		int[] data = new int[2]; data[0] = debut; data[1] = fin;
		boolean turn = true;
		while(data[1]-data[0] != duree) {
			if(turn) {
				turn = !turn;
				data[1]--;
			} else {
				turn = !turn;
				data[0]++;
			}
		}
		
		return data;
	}
}
