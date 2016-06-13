import java.util.*;



/**
 * @author Sébastien
 * A class to represent a telescope schedule
 */
public class ScheduleQ {

	protected int ID;
	protected LinkedList<ObservationQ> planning;
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
	public LinkedList<ObservationQ> Organize(Etoile[] stars) {
		LinkedList<ObservationQ> observations = new LinkedList<>();
		for (int i=0;i<stars.length;i++) {          //pour chaque étoile
			for (int j=0;j<stars[i].getNbNight();j++) {            //pour chaque nuit où l'étoile apparait
				ObservationQ o = new ObservationQ ( stars[i], stars[i].getNight(j).getID()) ;  
				observations.add(o);
			}
		}
		return observations;
	}
	
	/**
	 * une méthode pour initialiser les phéromones
	 * @param observations la liste d'observations à initialiser
	 */
	public void setProbas(LinkedList<ObservationQ> observations ) {
		Iterator<ObservationQ> iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		int[] tab = new int[NbNuits+1];		// construction d'un tableau qui compte pour chaque nuit le nombre d'étoiles pouvant être observées cette nuit là
		iter= observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			tab[o.getIDnuit()]+=1;
		}
//		double[] tableau= new double[NbNuits+1];
		for(int i=0;i<observations.size();i++) {
			observations.get(i).setProba(1.0/tab[observations.get(i).getIDnuit()]);
//			System.out.println(observations.get(i).getProba());
//			tableau[observations.get(i).getIDnuit()]+=observations.get(i).getProba();
		}
	
	}
	
	public void setProbasAccordingToInterest (LinkedList<ObservationQ> observations ) {
		Iterator<ObservationQ> iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		double[] tab= new double[NbNuits+1];
		iter = observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
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
	public void spreadPheromons(LinkedList<ObservationQ> observations, LinkedList<ObservationQ> bestway,double x, double y) {
		int[] tab = new int[observations.size()];
		for(int i=0;i<tab.length;i++) {
			tab[i]=0;
		}
		Iterator<ObservationQ> iter = bestway.iterator();
		while(iter.hasNext()) {
			ObservationQ o=iter.next();
			tab[o.getIDetoile()]=1;
		}
		for(int i=0;i<observations.size();i++) {
			if(tab[observations.get(i).getIDetoile()]==1) {
				observations.get(i).setProba(observations.get(i).getProba()+x);
			}
			observations.get(i).setProba(observations.get(i).getProba()-y);
		}
	}
	
	public LinkedList<ObservationQ> fourmi(LinkedList<ObservationQ> observations) {
		
		LinkedList<ObservationQ> blacklist = new LinkedList<>(); // contient les observations des étoiles déjà observées
		LinkedList<ObservationQ> chemin = new LinkedList<>(); // les observations que l'on va effectuer
		int NbNuitMax=1;
		Iterator<ObservationQ> iter= observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			if(o.getIDnuit()>NbNuitMax) {
				NbNuitMax=o.getIDnuit();
			}
		}
		//pour chaque nuit
		for (int i=0;i<=NbNuitMax;i++) {
			LinkedList<ObservationQ> dispo = new LinkedList<>();
			iter= observations.iterator(); // si ca marche pas change le nom de l'iterator et redéclare un nouvel itérateur
			//on remplit la liste avec les étoiles observables cette nuit-là
			while(iter.hasNext()) {
				ObservationQ o = iter.next();
				if(o.getIDnuit()==i) {
					dispo.add(o);
				}
			}
			//on retire toutes les étoiles déja observées
			Iterator<ObservationQ> iterDispo= dispo.iterator();
			LinkedList<ObservationQ> dispoNonBlacklistee = new LinkedList<>();
			while(iterDispo.hasNext()) {
				ObservationQ o=iterDispo.next();
				Iterator<ObservationQ> iterBlacklist =blacklist.iterator();
				boolean estBlacklistee=false;
				while(iterBlacklist.hasNext() && estBlacklistee==false) {
					ObservationQ Obs = iterBlacklist.next();
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
				ObservationQ choisie= dispo.get(a); 
				chemin.add(choisie); // on remplit ensuite la liste en se basant sur les phéromones
				blacklist.add(choisie); // on ajoute l'observation à la liste des obs déjà observées
				dispo.remove(choisie);
				iterDispo=dispo.iterator();
				LinkedList<ObservationQ> compatibles = new LinkedList<>();
				//on enleve toutes les observations qui ne sont pas compatibles avec les observations déjà choisies
				while(iterDispo.hasNext()) { 
					ObservationQ o = iterDispo.next();
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
	public double getGlobalInterest(LinkedList<ObservationQ> observations) {
		int interet=0;
		Iterator<ObservationQ> iter= observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
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
	public LinkedList<ObservationQ> LetTheAntsOut(LinkedList<ObservationQ> observations, int n) {
		LinkedList<ObservationQ> bestway=new LinkedList<ObservationQ>();
		LinkedList<ObservationQ> currentant = new LinkedList<ObservationQ>();
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
	public LinkedList<ObservationQ> AntAlgorithm(Etoile[] stars, int n, int k, double x, double y) {
		LinkedList<ObservationQ> observations= this.Organize(stars);
		this.setProbas(observations);
		LinkedList<ObservationQ> bestway = new LinkedList<>();
		for(int i=0;i<k;i++) {
			LinkedList<ObservationQ> bestgen = this.LetTheAntsOut(observations, n);
			if (this.getGlobalInterest(bestgen)>=this.getGlobalInterest(bestway)) {
				bestway=bestgen;
			}
			System.out.println(this.getGlobalInterest(bestway));
			this.spreadPheromons(observations, bestgen, x, y);
		}
		this.planning=bestway;
		return bestway;
	}
	
	public boolean check(LinkedList<ObservationQ> observations) {
		boolean answer=true;
		boolean[] tab = new boolean[NbEtoiles+1];
		Iterator<ObservationQ> iter=observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			if(tab[o.getIDetoile()]==false) {
				tab[o.getIDetoile()]=true;
			}
			else answer=false;	
			}
		iter = observations.iterator();
		int NbNuits=0;
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			if(o.getIDnuit()>NbNuits) NbNuits=o.getIDnuit();  //calcul du nombre de nuits total
		}
		int[] nuits = new int[NbNuits+1];
		iter = observations.iterator();
		while(iter.hasNext()) {
			ObservationQ o = iter.next();
			nuits[o.getIDnuit()]++;
		}
//		for(int i=0;i<nuits.length;i++) {
//			System.out.println(nuits[i]);
//		}
		return answer;
	}
	
	public int[] communicateNights() {
		int[] tab = new int[this.planning.size()];
		for(int i=0;i<this.planning.size();i++) {
			tab[i]=planning.get(i).getIDnuit();
		}
		return tab;
	}
	
	public int[] communicateStars() {
		int[] tab = new int[this.planning.size()];
		for(int i=0;i<this.planning.size();i++) {
			tab[i]=planning.get(i).getIDetoile();
		}
		return tab;
	}
	
}
