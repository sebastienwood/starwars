import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Sébastien
 * The class master encapsulate all the metaheuristics representation of the problems, manage switching from one representation to another, and thus is fit for strategies implementation
 */
public class Master {

	private Etoile[] data;
	private LinkedList<Schedule> alphas;
	
	private Genetic GA;
	private Recuit SA;
	private ScheduleQ ACO;
	
	/**
	 * Constructor of the Master class, initialize each representation
	 * @param src: the instance we work on
	 */
	public Master(String src) {
		this.data = Filehandler.read(src);
		//TODO: optimize for 2min
		this.GA = new Genetic(data,500);
		this.SA = new Recuit(GA.getAlpha());
		this.ACO = new ScheduleQ(data);
	}
	
	public void reinit() {
		this.GA = new Genetic(data,500);
		this.SA = new Recuit(GA.getAlpha());
		this.ACO = new ScheduleQ(data);
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
	
	/*Stratégies, mode d'emploi
	 * Appeler les heuristiques dans l'ordre souhaité
	 * Récupérer la valeur du dernier algorithme lancé dans la stratégie, l'ajouter dans la liste des précédents alphas
	 * -> Nécessaire pour les tabous
	 * Réinitialiser les problèmes
	 */
	public void strat_2h_Adele1() {
		this.ACO_activate((double)(40/60));
		this.GA_activate((double)(40/60));
		this.SA_activate((double)(40/60));
		alphas.add(SA.getSchedule());
		this.reinit();
	}
	
	public void strat_2h_Adele2() {
		this.ACO_activate(0.5);
		this.GA_activate(0.5);
		this.SA_activate(1);
		alphas.add(SA.getSchedule());
		this.reinit();
	}
}
