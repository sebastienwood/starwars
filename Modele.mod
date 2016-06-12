/*********************************************
 * OPL 12.6.0.0 Model
 * Author: louisad
 * Creation Date: 3 mai 2016 at 15:04:27
 *********************************************/

int NbEtoiles = ...;

range etoile = 1..NbEtoiles;

//Debut au plus tot de l observation
int debut[etoile] = ...;

//Fin au plus tard de l observation
int fin[etoile] = ...;

//Duree totale requise pour l observation
int dureeObs[etoile] = ...;

//Importance de la nuit
int valeurEtoile[etoile] = ...;

//Fin au plus tard du planning
int finAuPlusTard = ...;

//Debut au plus tot du planning
int debutAuPlusTot = ...;

//Fin au plus tot du planning
int finAuPlusTot = ...;

//Debut au plus tard du planning
int debutAuPlusTard = ...;

//Variables
dvar boolean elue[etoile];


//Objectif
maximize sum(e in etoile) elue[e]*valeurEtoile[e]  ;

//Contraintes
subject to {

	sum(e in etoile) dureeObs[e]*elue[e] <= finAuPlusTard - debutAuPlusTot;
	sum(e in etoile) dureeObs[e]*elue[e] >= debutAuPlusTard - finAuPlusTot;

}		



/*execute {
	writeln ("La valeur de l'objectif est de "+cplex.getObjValue()+ ".");
	for (var e in etoile){
		if (debut[e] == debutAuPlusTot && elue[e]>0){
				var fin1 = debutAuPlusTot + dureeObs[e];
				writeln ("Pour l etoile " + e + ", l observation est realisee de " + debut[e] + " a " + fin1);		
		}	
	}
	/for (var e in etoile){

		if (elue[e]>0 && debut[e] != debutAuPlusTot){	
			writeln ("Pour l etoile " + e + ", l observation est realisee de " + debut[e] + " a " + fin[e]);	
		}	
	}
}*/
