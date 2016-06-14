import java.util.Iterator;
import java.util.LinkedList;

import opInterface.*;

/**
 * 
 * @author Sébastien
 *
 */
public class OptimizedNightPlanner {

	/**
	 * A method to compute the value of a night, planning the stars with the first finished
	 * @param donnees : tableau de tableaux representants les donnees d'une etoile pour une nuit
	 */
	public static int value_FF(LinkedList<Night> data, LinkedList<Integer> importance, LinkedList<Integer> starID) {
        
		Night[] donnees = new Night[data.size()];
		int[] imp = new int[data.size()];
		int[] id = new int[data.size()];
		for(int ind = 0;ind<data.size();ind++) {
			donnees[ind] = data.get(ind);
			imp[ind] = importance.get(ind);
			id[ind] = starID.get(ind);
		}
		
		/*Trier par ordre de fin croissante*/
		for(int i = 0; i<donnees.length-1;i++) {
			for(int j = i+1; j<donnees.length;j++) {
				if(donnees[i].getFin() > donnees[j].getFin()) {
					Night n1 = donnees[i];
					Night n2 = donnees[j];
					donnees[j] = n1;
					donnees[i] = n2;
					
					Integer i1 = imp[i];
					Integer i2 = imp[j];
					imp[j] = i1;
					imp[i] = i2;
					
					Integer i3 = id[i];
					Integer i4 = id[j];
					id[j] = i3;
					id[i] = i4;
				}
			}
		}
		
		/*Prendre ceux qui sont compatibles*/
		LinkedList<Integer> bons = new LinkedList<Integer>();
		bons.add(0);
		int currenttime = donnees[0].getDebut()+donnees[0].getDuree();
		for(int i = 1; i<donnees.length;i++) {
			int duration = donnees[i].getDuree();
			int begin = donnees[i].getDebut();
			int finish = donnees[i].getFin();
			
			if(currenttime <= begin) {
				bons.add(i);
				currenttime = begin+duration;
			} else if(currenttime+duration<=finish) {
				bons.add(i);
				currenttime += duration;
			}
		}
//		int last = 0;
//		for(int i = 1;i<donnees.length;i++) {
//			if(donnees[last].getFin()<donnees[i].getDebut()) {
//				bons.add(i);
//				last= i;
//			}
//		}
//		/*Calcul de la duree totale de la nuit 'bons' */
//		/* + Calcul du debut au plus tot du planning*/
//		int dureeTotaleBons = 0;
//		int debutAuPlusTotPlanning = 10^10;
//		int finAuPlusTardPlanning = 0;
//		Iterator<Integer> iter = bons.iterator();
//		while(iter.hasNext()){
//			int myInt = iter.next();
//			dureeTotaleBons += donnees[myInt].getDuree();
//			if (debutAuPlusTotPlanning > donnees[myInt].getDebut()){
//				debutAuPlusTotPlanning = donnees[myInt].getDebut();
//			}
//			if (finAuPlusTardPlanning < donnees[myInt].getFin()){
//				finAuPlusTardPlanning = donnees[myInt].getFin();
//			}
//		}
//		
//		
//		/*Verifie si la duree totale de la nuit ne depasse pas la plage maximale d observation */
//		if (dureeTotaleBons > finAuPlusTardPlanning - debutAuPlusTotPlanning){
//			bons.remove(0);
//		}
		
		/*Compter leur valeur*/
		int valeur = 0;
		Iterator<Integer> i = bons.iterator();
		while(i.hasNext()) {
			int index = i.next();
			valeur += imp[index];
			//System.out.println("Etoile "+id[index]+" Deb "+donnees[index].getDebut()+" Fin "+donnees[index].getFin());
		}
		return valeur;
	}
	
	public static double value_mv(LinkedList<Night> listeEtoilesDuneNuit, LinkedList<Integer> importance){
		/*
		 * Jai besoin dun acces au debut au plus tot, fin au plus tard, debut au plus tard et fin au plus tot, importance
		 * 
		 */
		int nbEtoiles = listeEtoilesDuneNuit.size();
		int[] debutObs = new int [nbEtoiles]; //debut au plus tot de lobservation dune etoile
		int[] finObs = new int [nbEtoiles]; //fin au plus tard de lobervation dune etoile
		int[] dureeObs = new int [nbEtoiles];
		int[] valueObs = new int [nbEtoiles];
		int debutAuPlusTotPlanning = 10^10;
		int finAuPlusTardPlanning = 0;
		int debutAuPlusTardPlanning = 10^10;
		int finAuPlusTotPlanning = 0;
		Iterator<Night> iter = listeEtoilesDuneNuit.iterator();
		int i = 0;
		while(iter.hasNext() && i < nbEtoiles){
			Night currentNight = iter.next();
			debutObs[i] = currentNight.getDebut();
			finObs[i] = currentNight.getFin();
			dureeObs[i] = currentNight.getDuree();
			if (debutAuPlusTotPlanning >= currentNight.getDebut()){
				debutAuPlusTotPlanning = currentNight.getDebut();
			}
			if (debutAuPlusTardPlanning >= currentNight.getFin() - currentNight.getDuree()){
				debutAuPlusTardPlanning = currentNight.getFin() - currentNight.getDuree();
			}
			if (finAuPlusTotPlanning <= currentNight.getDebut() + currentNight.getDuree()){
				finAuPlusTotPlanning =  currentNight.getDebut() + currentNight.getDuree();
			}
			if (finAuPlusTardPlanning <= currentNight.getFin()){
				finAuPlusTardPlanning = currentNight.getFin();
			}
			i++;
		}
		Iterator<Integer> it = importance.iterator();
		int j = 0;
		while(it.hasNext() && j < nbEtoiles){
			int currentValue = it.next();
			valueObs[j] = currentValue;
			j++;
		}
		
		double valeurOptimale = 0;
		//Lecture du probleme
	    OplSolver probleme = new OplSolver("Modele.mod");
	    // traduction des correspondances entre les noms dans le modele et les donnees
	    OplData donnees = new OplData(probleme.getOplEnv());
	    // correspondance des donnees entre .mod et ce qu'il y a dans java
        donnees.add("NbEtoiles", nbEtoiles);
        donnees.add("debut", debutObs);
        donnees.add("fin", finObs);
        donnees.add("debutAuPlusTot", debutAuPlusTotPlanning);
        donnees.add("debutAuPlusTard", debutAuPlusTardPlanning);
        donnees.add("finAuPlusTot", finAuPlusTotPlanning);
        donnees.add("finAuPlusTard", finAuPlusTardPlanning);
        donnees.add("dureeObs", dureeObs);
        donnees.add("valeurEtoile", valueObs);
        
	        // resolution du probleme
	        int status = probleme.solve(donnees);
	        
	        // recuperation de la solution optimale
	        if (status == 0) {
	                valeurOptimale = probleme.getOptValue();
	                System.out.println("La valeur de l'objectif est : "+valeurOptimale);
	        }
			else {
	                System.out.println("Aucune solution disponible");
	                probleme.printData();
	        }
	        probleme.end();
	        return valeurOptimale;
	        
	}
}
