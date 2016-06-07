import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Sébastien
 *
 */
public class Genetic extends Solver {
	
	/*Paramètres de l'algorithme*/
	private double taux_mutation; 
	private int taille_tournoi;
	private double taux_uniforme;
	
	private Population pop;
	
	/**
	 * Constructor for the Genetic algorithm with customized parameters
	 * @param src: source file
	 * @param popsize: initial pop size wished
	 * @param taux_mutation: mutation rate wished
	 * @param taille_tournoi: tournament size wished
	 */
	public Genetic(String src, int popsize, double taux_mutation, int taille_tournoi, double taux_uniforme) {
		super(src);
		this.taille_tournoi = taille_tournoi;
		this.taux_mutation = taux_mutation;
		this.taux_uniforme = taux_uniforme;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			pop.addIndividual(this.breed(i));
		}
	}
	
	public Genetic(String src, int popsize) {
		super(src);
		this.taille_tournoi = 5;
		this.taux_mutation = 0.015;
		this.taux_uniforme = 0.5;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			pop.addIndividual(this.breed(i));
		}
	}
	
	/**
	 * TODO: rework
	 * @param ind
	 * @return
	 */
	private Individu breed(int ind) {
		int[] nuit_choisie = new int[data.length];
		for(int i = 0;i<data.length;i++) {
			nuit_choisie[i] = data[i].randomNight();
		}
		
		return new Individu(ind, nuit_choisie, data);
	}
	
	/**
	 * TODO: fill method
	 */
	public void evolve() {
		/*
		 * Garder le meilleur individu
		 * Pour toute la population, faire des croisements
		 * Faire des mutations sur la nouvelle population
		 */
		LinkedList<Individu> newGen = new LinkedList<Individu>();
		newGen.add(pop.getAlpha());
		for(int i=1;i<pop.getSize();i++) {
			Individu i1 = pop.tournoi(taille_tournoi);
			Individu i2 = pop.tournoi(taille_tournoi);
			i1.crossover(i2, taux_uniforme);
			i1.mutate(taux_mutation);
			newGen.add(i1);
		}
	}
	
	public int getValue() {
		return pop.getAlpha().getValue();
	}
}
