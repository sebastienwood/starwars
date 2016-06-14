import java.util.*;



/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class ScheduleQ {

	protected int ID;
	protected LinkedList<Observation> planning;
	private int NbEtoiles;
	
	
	public ScheduleQ(int n) {
		this.NbEtoiles=n;
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
		return this.NbEtoiles;
	}
	
	
	/**
	 * une méthode pour créer des observations à partir des données fournies par la classe Filehandler
	 * @param stars les données fournies par Filehandler
	 * @return la liste de toutes les observations possibles et faisables pour notre problème
	 */
	public LinkedList<Observation> Organize(Etoile[] stars) {
		LinkedList<Observation> observations = new LinkedList<>();
		for (int i=0;i<stars.length;i++) {          //pour chaque étoile
			for (int j=0;j<stars[i].getNbNight();j++) {            //pour chaque nuit où l'étoile apparait
				Observation o = new Observation ( stars[i], stars[i].getNight(j).getID()) ;  
				observations.add(o);
			}
		}
		return observations;
	}
	
	/**
	 * une méthode pour initialiser les phéromones
	 * @param observations la liste d'observations à initialiser
	 */
	public void setProbas(LinkedList<Observation> observations ) {
		Iterator<Observation> iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		int[] tab = new int[NbNuits+1];		// construction d'un tableau qui compte pour chaque nuit le nombre d'étoiles pouvant être observées cette nuit là
		iter= observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			tab[o.getIDnuit()]+=1;
		}
//		double[] tableau= new double[NbNuits+1];
		for(int i=0;i<observations.size();i++) {
			observations.get(i).setProba(1.0/tab[observations.get(i).getIDnuit()]);
//			System.out.println(observations.get(i).getProba());
//			tableau[observations.get(i).getIDnuit()]+=observations.get(i).getProba();
		}
	
	}
	
	public void setProbasAccordingToInterest (LinkedList<Observation> observations ) {
		Iterator<Observation> iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		double[] tab= new double[NbNuits+1];
		iter = observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			tab[o.getIDnuit()]+=o.getProba();
		}
		for(int j=0;j<observations.size();j++) {
			observations.get(j).setProba(observations.get(j).getProba()/tab[observations.get(j).getIDnuit()]);
		}
	}
	
	/**
	 * a method which represents the spreading of pheromons on the paths taken by the best ants
	 * it also represents the natural decrease of the amount of pheromons into the wild
	 * @param observations the list of all of our observations
	 * @param bestway the best path found by our ants
	 * @param x the amount of pheromons we want to add on the best path (between 0 and 1)
	 * @param y the reduction of pheromons into the wild
	 */
	public void spreadPheromons(LinkedList<Observation> observations, LinkedList<Observation> bestway,double x, double y) {
		int[] tab = new int[observations.size()];
		for(int i=0;i<tab.length;i++) {
			tab[i]=0;
		}
		Iterator<Observation> iter = bestway.iterator();
		while(iter.hasNext()) {
			Observation o=iter.next();
			tab[o.getIDetoile()]=1;
		}
		for(int i=0;i<observations.size();i++) {
			if(tab[observations.get(i).getIDetoile()]==1) {
				observations.get(i).setProba(observations.get(i).getProba()+x);
			}
			observations.get(i).setProba(observations.get(i).getProba()-y);
		}
	}
	
	public LinkedList<Observation> fourmi(LinkedList<Observation> observations) {
		
		LinkedList<Observation> blacklist = new LinkedList<>(); // contient les observations des étoiles déjà observées
		LinkedList<Observation> chemin = new LinkedList<>(); // les observations que l'on va effectuer
		int NbNuitMax=1;
		Iterator<Observation> iter= observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuitMax) {
				NbNuitMax=o.getIDnuit();
			}
		}
		//pour chaque nuit
		for (int i=0;i<=NbNuitMax;i++) {
			LinkedList<Observation> dispo = new LinkedList<>();
			iter= observations.iterator(); // si ca marche pas change le nom de l'iterator et redéclare un nouvel itérateur
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
			while(dispo.isEmpty()==false) {
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
					if (choisie.estCompatible(o) ) {
						compatibles.add(o);
					}
				}
				dispo=compatibles;
			}
		}
		return chemin;	
	}
	
	/**
	 * une méthode pour évaluer l'intéret global d'un planning
	 * @param observations le planning d'observations
	 * @return l'intérêt du planning
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
	 * a method to send a lot of ants into the wild to fetch food
	 * @param observations the differents observations possible
	 * @param n the number of ants sent
	 * @return the best path found by our ants 
	 */
	public LinkedList<Observation> LetTheAntsOut(LinkedList<Observation> observations, int n) {
		LinkedList<Observation> bestway=new LinkedList<Observation>();
		LinkedList<Observation> currentant = new LinkedList<Observation>();
		for (int i=0;i<n;i++) {
			currentant=this.fourmi(observations);
			if (this.getGlobalInterest(currentant)>this.getGlobalInterest(bestway)) {
				bestway=currentant;
			}	
		}
		return bestway;
	}
	
	/**
	 * 
	 * @param stars the list of the stars we want to observe
	 * @param n the number of ants/generation
	 * @param k the number of generations
	 * @param x 
	 * @param y
	 * @return
	 */
	public LinkedList<Observation> AntAlgorithm(Etoile[] stars, int n, int k, double x, double y) {
		LinkedList<Observation> observations= this.Organize(stars);
		this.setProbas(observations);
		LinkedList<Observation> bestway = new LinkedList<>();
		for(int i=0;i<k;i++) {
			LinkedList<Observation> bestgen = this.LetTheAntsOut(observations, n);
			if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
				bestway=bestgen;
			}
			System.out.println(this.getGlobalInterest(bestway));
			this.spreadPheromons(observations, bestgen, x, y);
		}
		this.planning=bestway;
		return bestway;
	}
	
	public boolean check(LinkedList<Observation> observations) {
		boolean answer=true;
		boolean[] tab = new boolean[NbEtoiles+1];
		Iterator<Observation> iter=observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(tab[o.getIDetoile()]==false) {
				tab[o.getIDetoile()]=true;
			}
			else answer=false;	
			}
		iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			Observation o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		int[] nuits = new int[NbNuits+1];
		iter = observations.iterator();
		while(iter.hasNext()) {
			Observation o = iter.next();
			nuits[o.getIDnuit()]++;
		}
//		for(int i=0;i<nuits.length;i++) {
//			System.out.println(nuits[i]);
//		}
		return answer;
	}
	
	public int[] communicateNights() {
		int[] tab = new int[NbEtoiles];
		for(int i=0;i<tab.length;i++) {
			tab[i]=-1;
		}
		int NbNuits=0;
		Iterator<Observation> iter = this.planning.iterator();
		while(iter.hasNext()) {
			Observation o=iter.next();
			tab[o.getIDetoile()]=o.getIDnuit();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();
		}
		Random rand = new Random();
		
		for(int i=0;i<tab.length;i++) {
			if(tab[i]==-1) {
				tab[i]= rand.nextInt(NbNuits); 
				
			}
		}
		return tab;
	}
	
	public LinkedList<Observation> AntColonyFor(double t, Etoile[] stars, int n, int k, double x, double y) {
		long time=System.currentTimeMillis();
		LinkedList<Observation> observations= this.Organize(stars);
		this.setProbas(observations);
		LinkedList<Observation> bestway = new LinkedList<>();
		LinkedList<Observation> bestgen = this.LetTheAntsOut(observations, n);
		if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
			bestway=bestgen;
		}
		System.out.println(this.getGlobalInterest(bestway));
		this.spreadPheromons(observations, bestgen, x, y);
		long timeout=System.currentTimeMillis();
		while(timeout-time<t*3600*1000) {
			bestgen = this.LetTheAntsOut(observations, n);
			if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
				bestway=bestgen;
			}
			System.out.println(this.getGlobalInterest(bestway));
			this.spreadPheromons(observations, bestgen, x, y);
			timeout=System.currentTimeMillis();
		}
		this.planning=bestway;
		return bestway;
	}

	public LinkedList<Schedule> SendAnts(double n, String txt) {
		LinkedList<Schedule> ants = new LinkedList<>();
		Etoile[] data= Filehandler.read(txt);
		for(int i=0;i<n;i++) {
			LinkedList<Observation> observations= this.AntAlgorithm(data, 5, 5, 0.001, 0.0005);
			int[] tab=this.communicateNights();
			Schedule sc= new Schedule(i, tab, data);
			ants.add(sc);
		}
		return ants;
	}
	
	public Schedule BestSchedule(double n, String txt) {
		Etoile[] data= Filehandler.read(txt);
		this.AntAlgorithm(data, 1,1,0.001, 0.0005);
		int[] tableau=this.communicateNights();
		Schedule bestSchedule= new Schedule(1, tableau, data);
		int bestInterest=0;
		for(int i=0;i<n;i++) {
			LinkedList<Observation> observations= this.AntAlgorithm(data, 5, 5, 0.001, 0.0005);
			if(this.getGlobalInterest(observations)>bestInterest) {
				bestInterest=this.getGlobalInterest(observations);
				int[] tab=this.communicateNights();
				Schedule sc= new Schedule(i, tab, data);
				bestSchedule=sc;
			}
		}
		return bestSchedule;
	}
	
	public LinkedList<Observation> UpdateAlpha(Schedule sc) {
		LinkedList<Observation> observations= new LinkedList<>();
		Etoile[] stars= sc.getStars();
		int[] nights=sc.getStarsNights();
		for(int i=0;i<stars.length;i++) {
			Observation o= new Observation(stars[i],nights[i]);
			observations.add(o);
		}
		this.planning=observations;
		
		return observations;
		
	}
	
}
