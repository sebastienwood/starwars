import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Genetic {
	
	/*Paramètres de l'algorithme*/
	private double taux_mutation; 
	private int taille_tournoi;
	private double taux_uniforme;
	
	private Population pop;
	private Etoile[] data;
	//TODO: maybe get alpha to a variable stored in memory instead of looking for him each time
	
	/**
	 * Constructor for the Genetic algorithm with customized parameters
	 * @param src: source file
	 * @param popsize: initial pop size wished
	 * @param taux_mutation: mutation rate wished
	 * @param taille_tournoi: tournament size wished
	 */
	public Genetic(String src, int popsize, double taux_mutation, int taille_tournoi, double taux_uniforme) {
		this.data = Filehandler.read(src);
		this.taille_tournoi = taille_tournoi;
		this.taux_mutation = taux_mutation;
		this.taux_uniforme = taux_uniforme;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			pop.addIndividual(this.breed(i));
		}
	}
	
	/**
	 * Generic constructor
	 * @param src: the source file
	 * @param popsize: initial pop size wished
	 */
	public Genetic(String src, int popsize) {
		this.data = Filehandler.read(src);
		this.taille_tournoi = 5;
		this.taux_mutation = 0.015;
		this.taux_uniforme = 0.5;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			Schedule newOne = this.breed(i);
			pop.addIndividual(newOne);
		}
	}
	
	public Genetic(Etoile[] data, int popsize) {
		this.data = data;
		this.taille_tournoi = 5;
		this.taux_mutation = 0.015;
		this.taux_uniforme = 0.5;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			Schedule newOne = this.breed(i);
			pop.addIndividual(newOne);
		}
	}
	
	public Genetic(Etoile[] data, int popsize, Schedule alpha) {
		this.data = data;
		this.taille_tournoi = 5;
		this.taux_mutation = 0.015;
		this.taux_uniforme = 0.5;
		this.pop = new Population();
		
		pop.addIndividual(alpha);
		
		for(int i = 1; i< popsize; i ++) {
			Schedule newOne = this.breed(i);
			//System.out.println(newOne.toString());
			pop.addIndividual(newOne);
		}
	}
	
	public void changePop(LinkedList<Schedule> list) {
		pop.clear();
		Iterator<Schedule> i = list.iterator();
		while(i.hasNext()) {
			Schedule n = i.next();
			pop.addIndividual(n);
		}
	}
	
	/**
	 * A method to breed a new individual
	 * @param ind: the nb associated with the individual
	 * @return the new individual
	 */
	private Schedule breed(int ind) {
		int[] nuit_choisie = new int[data.length];
		for(int i = 0;i<data.length;i++) {
			nuit_choisie[i] = data[i].randomNight();
		}
		return new Schedule(ind, nuit_choisie, data);
	}
	
	/**
	 * A method to evolve the whole population 
	 */
	public void evolve() {
		/*
		 * Garder le meilleur individu
		 * Pour toute la population, faire des croisements
		 * Faire des mutations sur la nouvelle population
		 */
		Population newGen = new Population();
		newGen.addIndividual(pop.getAlpha());
		newGen.addIndividual(this.breed(0));
		
		for(int i=2;i<pop.getSize();i++) {
			Schedule i1 = pop.tournoi(taille_tournoi);
			Schedule i2 = pop.tournoi(taille_tournoi);
			Schedule newOne = i1.crossover(i2, taux_uniforme);
			Schedule mutated = newOne.mutate(taux_mutation);
			newGen.addIndividual(mutated);
		}
		pop = newGen;
	}
	
	/**
	 * Return the best value of the population
	 * @return the best value of the population
	 */
	public int getValue(boolean FF) {
			if(FF) {
				return this.getAlpha().getValue();
			} else {
				return this.getAlpha().getValue(false);
			}
	}

	/**
	 * A methode to give life to the population for a given time in hours
	 * @param i: a time in hours
	 */
	public void live(double i) {
		double fin = System.currentTimeMillis()+(i*3600*1000);
		int gen = 0;
		while(System.currentTimeMillis()<fin) {
			this.evolve();
			gen++;
			//System.out.println(pop.getAlpha().toString());
			System.out.println(gen+" "+(fin-System.currentTimeMillis())+" "+this.getValue(true));
		}
		System.out.println(this.getValue(false));		
	}

	public Schedule getAlpha() {
		return pop.getAlpha();
	}
	
	public void updateAlpha(Schedule newAlpha) {
		pop.switchRandom(newAlpha);
	}
}
