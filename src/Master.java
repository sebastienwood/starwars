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
	
	private Recuit SA;
	
	private ScheduleQ ACO;
	
	/**
	 * Constructor of the Master class, initialize each representation
	 * @param src: the instance we work on
	 */
	public Master(String src) {
		this.data = Filehandler.read(src);
		this.alphas = new LinkedList<Schedule>();
	}
	
	private void initGA(int pop) {
		this.GA = new Genetic(data, pop);
	}
	
	private void initACO(int pop) {
		this.ACO = new ScheduleQ(data, pop);
	}
	
	private void initSA(Schedule indiv) {
		this.SA = new Recuit(indiv);
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
	
	private void fromACOtoGA(int pop, int nb_fourmi) {
		this.GA = new Genetic(data, pop, ACO.createAnts(nb_fourmi, false));
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
			this.fromACOtoGA(100,10);
				System.out.println("GA running");
				this.GA_activate((double)40/60);
				this.fromGAtoSA();
					this.SA_activate((double)40/60);
			
		alphas.add(SA.getSchedule());
	}
	
	public void strat_2h() {
	
	double t = System.currentTimeMillis()+2*3600*1000;
	while(System.currentTimeMillis()<t) {
		this.initACO(10);
		this.fromACOtoGA(100, 10);
		this.GA_activate(0.05);
		this.fromGAtoSA();
		this.SA_activate(0.025);
		alphas.add(SA.getSchedule());
	}
	System.out.println(this.bestAlpha().getValue(false));
}
	
	public void strat_2h_Adele2() {
		this.initACO(100);
			this.ACO_activate(0.5);
			this.fromACOtoGA(100,10);
				this.GA_activate(0.5);
				this.fromGAtoSA();
					this.SA_activate(1);
		alphas.add(SA.getSchedule());

	}
	
	public int strat_2m_ALL() {
		double temps_deb = System.currentTimeMillis();
		int nb_fourmi;
		if(this.data.length<=600) {
			nb_fourmi = 10;
			this.initACO(10);
		} else {
			nb_fourmi = 1;
			this.initACO(1);
		}
		System.out.println("FromACOtoGA");
		this.fromACOtoGA(100,nb_fourmi);
		System.out.println("GA activate");
		double temps_adele = (double)0.5/60;
		double temps_ecoule = (System.currentTimeMillis()-temps_deb)/(3600*1000);
		double temps_restant = (double)2/60-temps_adele-temps_ecoule;
		this.GA_activate(temps_restant);
		this.fromGAtoSA();
		this.SA_activate((double)0.5/60);
		alphas.add(SA.getSchedule());
		return(SA.getSchedule().getValue(false));
	}
	
	public int strat_2m_Quentin() {
		double temps_deb = System.currentTimeMillis();
		int nb_fourmi;
		if(this.data.length<=600) {
			nb_fourmi = 15;
			this.initACO(15);
		} else {
			nb_fourmi = 1;
			this.initACO(1);
		}
		System.out.println("FromACOtoGA");
		this.ACO.live(0.5/60);
		this.GA= new Genetic(data,100,this.ACO.getAlpha());
		System.out.println("GA activate");
		double temps_adele = (double)0.5/60;
		double temps_ecoule = (System.currentTimeMillis()-temps_deb)/(3600*1000);
		double temps_restant = (double)2/60-temps_adele-temps_ecoule;
		this.GA_activate(temps_restant);
		this.fromGAtoSA();
		this.SA_activate((double)0.5/60);
		
		alphas.add(SA.getSchedule());
		return SA.getSchedule().getValue(false);
	}
}
