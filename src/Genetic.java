import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
	
	private Population pop;
	
	/**
	 * Constructor for the Genetic algorithm with customized parameters
	 * @param src: source file
	 * @param popsize: initial pop size wished
	 * @param taux_mutation: mutation rate wished
	 * @param taille_tournoi: tournament size wished
	 */
	public Genetic(String src, int popsize, double taux_mutation, int taille_tournoi) {
		super(src);
		this.taille_tournoi = taille_tournoi;
		this.taux_mutation = taux_mutation;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			pop.addIndividual(this.breed(i));
		}
	}
	
	public Genetic(String src, int popsize) {
		super(src);
		this.taille_tournoi = 5;
		this.taux_mutation = 0.015;
		this.pop = new Population();
		
		for(int i = 0; i< popsize; i ++) {
			pop.addIndividual(this.breed(i));
		}
	}
	
	private Individu breed(int ind) {
		/*
		 * Queue all the possible star ID
		 * Randomize
		 * Take the first one
		 * Then for each successive, try to put it in
		 */
		Individu i = new Individu(ind);
		LinkedList<Etoile> o = new LinkedList<Etoile>(Arrays.asList(data));
		Collections.shuffle(o);
		Iterator<Etoile> it = o.iterator();
		while(it.hasNext()) {
			Etoile ob = it.next();
			i.tryAdding(ob);
		}
		return i;
	}
	
	/*
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
			i1.crossover(i2);
			newGen.add(i1);
		}
	}
}
