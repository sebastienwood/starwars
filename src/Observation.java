
public class Observation {
	private Etoile e;
	private double debut;
	private double fin ;
	private double duree;
	private double proba;
	private int IDnuit;
	private int IDetoile;
	/**
	 * a constructor for an observation
	 * @param e the star we want to observe
	 * @param i the id of the night we want to watch the star
	 */
	public Observation( Etoile e, int i) {
		this.e=e;
		for(int k =0;k<e.getNbNight();k++) {
			if(e.getNight(k).getID()==i) {
				this.debut=e.getNight(k).getDebut();
				this.fin=e.getNight(k).getFin();
				this.duree=e.getNight(k).getDuree();
				this.IDnuit=i;
			}
		}
		
		this.IDetoile=e.getID();
	}
	
	public int getIDnuit() {
		return this.IDnuit;
	}
	
	public int getIDetoile() {
		return this.IDetoile;
	}
	
	public double getDebut() {
		return this.debut;
	}
	
	public double getFin() {
		return this.fin;
	}
	
	
	
	public double getDuree() {
		return this.duree;
	}
	
	public double getProba() {
		return this.proba;
	}
	
	public boolean estCompatible(Observation o) {
		boolean resultat= true;
		
		if(o.getDebut()>this.getDebut()&&o.getDebut()<this.getDebut()+this.duree) resultat=false;
		if(this.getDebut()>o.getDebut()&&this.getDebut()<o.getDebut()+o.duree) resultat = false;
		return resultat;
	}
	
	public double getInterest() {
		return this.e.getPriority();
	}
	
	public void setProba(double x) {
		this.proba=x;
	}
}
