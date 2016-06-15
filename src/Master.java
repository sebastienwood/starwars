import java.util.Iterator;
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
	private int GA_pop;
	
	private Recuit SA;
	
	private ScheduleQ ACO;
	private int ACO_pop;
	
	
	/**
	 * Constructor of the Master class, initialize each representation
	 * @param src: the instance we work on
	 */
	public Master(String src) {
		this.data = Filehandler.read(src);
	}
	
	private void initGA(int pop) {
		this.GA = new Genetic(data, pop);
		this.GA_pop = pop;
	}
	
	private void initACO(int pop) {
		this.ACO = new ScheduleQ(data, pop);
		this.ACO_pop = pop;
	}
	
	private void initSA(Schedule indiv) {
		this.SA = new Recuit(indiv);
	}
	
	private void reinit() {
		this.reinitGA();
		this.reinitACO();
		this.reinitSA(this.bestAlpha());
	}
	
	private void reinitGA() {
		this.GA = new Genetic(data,GA_pop);
	}
	
	private void reinitSA(Schedule indiv) {
		this.SA = new Recuit(indiv);
	}
	
	private void reinitACO() {
		this.ACO = new ScheduleQ(data, ACO_pop);
	}
	
	private void GA_activate(double timeinH) {
		/*Run the GA*/
		GA.live(timeinH);
	}
	
	private void fromGAtoSA() {
		SA = new Recuit(GA.getAlpha());
	}
	
	private void fromGAtoACO() {
		ACO.updateAlpha(GA.getAlpha());
	}
	
	private void SA_activate(double timeinH) {
		/*Run the SA*/
		SA.live(timeinH);
	}
	
	private void ACO_activate(double timeinH) {
		/*Run the ACO*/
		ACO.live(timeinH);
	}
	
	private void fromACOtoGA() {
		this.GA.changePop(ACO.createAnts(GA_pop, false));
	}
	
	private void fromACOtoSA() {
		SA = new Recuit(ACO.getAlpha());
	}
	
	public Schedule bestAlpha() {
		int bestvalue = 0;
		Schedule bestschedule = null;
		Iterator<Schedule> i = alphas.iterator();
		while(i.hasNext()) {
			Schedule s = i.next();
			if(s.getValue()>bestvalue) {
				bestvalue = s.getValue();
				bestschedule = s;
			}
		}
		return bestschedule;
	}
	
	/*Stratégies, mode d'emploi
	 * Appeler les heuristiques dans l'ordre souhaité
	 * Récupérer la valeur du dernier algorithme lancé dans la stratégie, l'ajouter dans la liste des précédents alphas
	 * -> Nécessaire pour les tabous
	 * Réinitialiser les problèmes
	 */
	public void strat_2h_Adele1() {
		this.initACO(100);
		this.initGA(100);
		System.out.println("ACO & GA activated");
			this.ACO_activate((float)40/60);
			System.out.println("FromACOtoGA");
			this.fromACOtoGA();
				System.out.println("GA running");
				this.GA_activate((double)40/60);
				this.fromGAtoSA();
					this.SA_activate((double)40/60);
			
		alphas.add(SA.getSchedule());
		this.reinit();
	}
	
	public void strat_2h_Adele2() {
		this.initACO(100);
			this.ACO_activate(0.5);
			this.fromACOtoGA();
				this.GA_activate(0.5);
				this.fromGAtoSA();
					this.SA_activate(1);
		alphas.add(SA.getSchedule());
		this.reinit();
	}
}
