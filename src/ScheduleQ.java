import java.util.*;



/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class ScheduleQ {

	private int ID;
	private Etoile[] data;
	
	/*The set of feasible observations*/
	private LinkedList<Observation> planning;
	/*The best set of observations*/
	private LinkedList<Observation> bestAnt;
	
	/*Parameters of the ACO*/
	private double pheromons_added;
	private double pheromons_fadding;
	private int nb_generations;
	private int nb_ants;
	
	public ScheduleQ(Etoile[] data, int pop) {
		this.data = data;
		this.pheromons_added = 0.001;
		this.pheromons_fadding = 0.0005;
		this.nb_ants = pop;
		this.organize();
		this.setProbas();
	}
	
	public void changePheromonsFadding(double rate) {
		this.pheromons_fadding = rate;
	}
	/**
	 * Accessor to the ID of the schedule
	 * @return the ID of the schedule
	 */
	protected int getID() {
		return this.ID;
	}
	
	protected int getSize() {
		return this.planning.size();
	}
	
	public int getNbNight() {
		return this.data.length;
	}
	
	/**
	 * une méthode pour créer des observations à partir des données fournies par la classe Filehandler
	 * @param stars les données fournies par Filehandler
	 * @return la liste de toutes les observations possibles et faisables pour notre problème
	 */
	private void organize() {
		LinkedList<Observation> observations = new LinkedList<>();
		for (int i=0;i<data.length;i++) {          //pour chaque étoile
			for (int j=0;j<data[i].getNbNight();j++) {            //pour chaque nuit où l'étoile apparait
				Observation o = new Observation (data[i], data[i].getNight(j).getID()) ;  
				observations.add(o);
			}
		}
		this.planning = observations;
	}
	
	/**
	 * une méthode pour initialiser les phéromones
	 * @param observations la liste d'observations à initialiser
	 */
	private void setProbas() {
		Iterator<Observation> iter = planning.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		int[] tab = new int[NbNuits+1];		// construction d'un tableau qui compte pour chaque nuit le nombre d'étoiles pouvant être observées cette nuit là
		iter= planning.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			tab[o.getIDnuit()]+=1;
		}
//		double[] tableau= new double[NbNuits+1];
		for(int i=0;i<planning.size();i++) {
			planning.get(i).setProba(1.0/tab[planning.get(i).getIDnuit()]);
//			System.out.println(observations.get(i).getProba());
//			tableau[observations.get(i).getIDnuit()]+=observations.get(i).getProba();
		}
	
	}
	
	private void setProbasAccordingToInterest() {
		Iterator<Observation> iter = planning.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		double[] tab= new double[NbNuits+1];
		iter = planning.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			tab[o.getIDnuit()]+=o.getProba();
		}
		for(int j=0;j<planning.size();j++) {
			planning.get(j).setProba(planning.get(j).getProba()/tab[planning.get(j).getIDnuit()]);
		}
	}
	
	/**
	 * a method which represents the spreading of pheromons on the paths taken by the best ants
	 * it also represents the natural decrease of the amount of pheromons into the wild
	 * @param bestway the best path found by our ants
	 */
	public void spreadPheromons(LinkedList<Observation> bestway) {
		int[] tab = new int[planning.size()];
		for(int i=0;i<tab.length;i++) {
			tab[i]=0;
		}
		Iterator<Observation> iter = bestway.iterator();
		while(iter.hasNext()) {
			Observation o=iter.next();
			tab[o.getIDetoile()]=1;
		}
		for(int i=0;i<planning.size();i++) {
			if(tab[planning.get(i).getIDetoile()]==1) {
				planning.get(i).setProba(planning.get(i).getProba()+pheromons_added);
			}
			planning.get(i).setProba(planning.get(i).getProba()-pheromons_fadding);
		}
	}
	
	/**
	 * Simulate the behavior of an ant
	 * @return the path of the ant
	 */
	private LinkedList<Observation> fourmi() {
		LinkedList<Observation> blacklist = new LinkedList<>(); // contient les observations des étoiles déjà observées
		LinkedList<Observation> chemin = new LinkedList<>(); // les observations que l'on va effectuer
		int NbNuitMax=1;
		Iterator<Observation> iter= planning.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuitMax) {
				NbNuitMax=o.getIDnuit();
			}
		}
		//pour chaque nuit
		for (int i=0;i<=NbNuitMax;i++) {
			LinkedList<Observation> dispo = new LinkedList<>();
			iter= planning.iterator(); // si ca marche pas change le nom de l'iterator et redéclare un nouvel itérateur
			//on remplit la liste avec les étoiles observables cette nuit-là
			while(iter.hasNext()) {
				Observation o = iter.next();
				if(o.getIDnuit()==i) {
					dispo.add(o);
				}
			}
			//on retire toutes les étoiles déja observées
			Iterator<Observation> iterDispo= dispo.iterator();
			LinkedList<Observation> dispoNonBlacklistee = new LinkedList<>();
			while(iterDispo.hasNext()) {
				Observation o=iterDispo.next();
				Iterator<Observation> iterBlacklist =blacklist.iterator();
				boolean estBlacklistee=false;
				while(iterBlacklist.hasNext() && estBlacklistee==false) {
					Observation Obs = iterBlacklist.next();
					if (Obs.getIDetoile()== o.getIDetoile()) {
						estBlacklistee=true;
					}
				}
				if (estBlacklistee==false) dispoNonBlacklistee.add(o);
			}
			dispo=dispoNonBlacklistee;
//			System.out.println(dispo.size());
			while(!dispo.isEmpty()) {
				// on choisit une observation en fonction de la proba associée (phéromones)
				Random rand= new Random();
				double proba=rand.nextDouble();
				double compteur =0.0;
				int a =0;
				boolean chosen = false; 
				while(chosen == false) {
					compteur += dispo.get(a).getProba();
					if (compteur >= proba) {
						chosen = true;
					}
					else if(a==dispo.size()-1) {
						chosen=true;
					}
					else {
						a++;
					}
				}
				Observation choisie= dispo.get(a); 
				chemin.add(choisie); // on remplit ensuite la liste en se basant sur les phéromones
				blacklist.add(choisie); // on ajoute l'observation à la liste des obs déjà observées
				dispo.remove(choisie);
				iterDispo=dispo.iterator();
				LinkedList<Observation> compatibles = new LinkedList<>();
				//on enleve toutes les observations qui ne sont pas compatibles avec les observations déjà choisies
				while(iterDispo.hasNext()) { 
					Observation o = iterDispo.next();
					if (choisie.estCompatible(o)) {
						compatibles.add(o);
					}
				}
				dispo=compatibles;
			}
		}
		return chemin;	
	}
	
	/**
	 * une méthode pour évaluer l'intéret global d'un chemin
	 * @param chemin emprunte par une fourmi
	 * @return l'intérêt du chemin
	 */
	public int getGlobalInterest(LinkedList<Observation> observations) {
		int interet=0;
		Iterator<Observation> iter= observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			interet+=o.getInterest();
		}
		return interet;
	}
	
	/**
	 * Simulate the behavior of ants on one generation
	 * @return the best path found by our ants 
	 */
	public LinkedList<Observation> letTheAntsOut() {
		LinkedList<Observation> bestway=new LinkedList<Observation>();
		LinkedList<Observation> currentant = new LinkedList<Observation>();
		for (int i=0;i<nb_ants;i++) {
			currentant=this.fourmi();
			if (this.getGlobalInterest(currentant)>this.getGlobalInterest(bestway)) {
				bestway=currentant;
			}	
		}
		return bestway;
	}
	
	/**
	 * Simulate the behavior of ants on multiple generations
	 * @return the best path found by our ants
	 */
	public LinkedList<Observation> antAlgorithm(int nb_gen) {
		this.setProbas();
		LinkedList<Observation> bestway = new LinkedList<>();
		for(int i=0;i<nb_gen;i++) {
			LinkedList<Observation> bestgen = this.letTheAntsOut();
			if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
				bestway=bestgen;
			}
			this.spreadPheromons(bestgen);
		}
		if(this.getGlobalInterest(bestway)>this.getGlobalInterest(bestAnt)) {
			this.bestAnt = bestway;
		}
		return bestway;
	}
	
	/**
	 * For a given path, indicate the night each star is watched
	 * @return
	 */
	private int[] communicateNights(LinkedList<Observation> path) {
		int[] tab = new int[data.length];
		for(int i=0;i<tab.length;i++) {
			tab[i]=-1;
		}
		Iterator<Observation> iter = path.iterator();
		while(iter.hasNext()) {
			Observation o=iter.next();
			tab[o.getIDetoile()]=o.getIDnuit();
		}

		for(int i=0;i<tab.length;i++) {
			if(tab[i]==-1) {
				tab[i]= data[i].randomNight(); 
				
			}
		}
		return tab;
	}
	
	/**
	 * A method to make the ACO live for a time
	 * @param t: a time in hour
	 */
	public void live(double t) {
		t *= 3600*1000;
		t += System.currentTimeMillis();
		this.setProbas();
		LinkedList<Observation> bestway = new LinkedList<>();
		LinkedList<Observation> bestgen = this.letTheAntsOut();
		if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
			bestway=bestgen;
			bestAnt = bestway;
		}
		System.out.println("ACO "+this.getGlobalInterest(bestway));
		this.spreadPheromons(bestgen);
		
		while(System.currentTimeMillis()<t) {
			bestgen = this.letTheAntsOut();
			if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
				bestway=bestgen;
				bestAnt = bestway;
			}
			System.out.println("ACO "+this.getGlobalInterest(bestway));
			this.spreadPheromons(bestgen);
		}
		if(this.getGlobalInterest(bestway)>this.getGlobalInterest(bestAnt)) {
			this.bestAnt = bestway;
		}
	}

	/**
	 * Create a whole population of ants for the GA
	 * @param n: the number of ants we wish
	 * @return the ants as schedules
	 */
	public LinkedList<Schedule> createAnts(int n, boolean reinit) {
		LinkedList<Schedule> ants = new LinkedList<>();
		int memory = this.nb_ants;
		this.nb_ants = n;
		if(reinit) { 
			this.setProbas(); 
		}
		
		for(int i=0;i<n;i++) {
			LinkedList<Observation> observations= this.antAlgorithm(1);
			int[] tab=this.communicateNights(observations);
			Schedule sc= new Schedule(i, tab, data);
			ants.add(sc);
			System.out.println(i);
		}
		this.nb_ants = memory;
		return ants;
	}
	
	/**
	 * Return the best ant we found this far
	 * @return the best ant we found, as a schedule
	 */
	public Schedule getAlpha() {
//		LinkedList<Observation> ant = this.antAlgorithm(1,1,0.001, 0.0005);
//		int[] nuits_choisies=this.communicateNights(ant);
//		Schedule bestSchedule= new Schedule(1, nuits_choisies, data);
//		int bestInterest=this.getGlobalInterest(ant);
//		
//		for(int i=0;i<n;i++) {
//			LinkedList<Observation> observations= this.antAlgorithm(5, 5, 0.001, 0.0005);
//			if(this.getGlobalInterest(observations)>bestInterest) {
//				bestInterest=this.getGlobalInterest(observations);
//				int[] tab=this.communicateNights(observations);
//				bestSchedule= new Schedule(i, tab, data);
//			}
//		}
//		
//		return bestSchedule
		
		return new Schedule(1, this.communicateNights(this.bestAnt), data);
	}
	
	/**
	 * From a schedule, update our best ant
	 * @param sc: schedule to transform to our best ant
	 */
	public void updateAlpha(Schedule sc) {
		LinkedList<Observation> observations= new LinkedList<>();
		Etoile[] stars= sc.getStars();
		int[] nights=sc.getStarsNights();
		for(int i=0;i<stars.length;i++) {
			Observation o= new Observation(stars[i],nights[i]);
			observations.add(o);
		}
		this.bestAnt = observations;
	}
}
