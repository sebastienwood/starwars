import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 * @author Sébastien
 *
 */
public class Solver {

	private Observation[] etoiles;
	
	/**
	 * Constructor of a solver
	 * @param src: path to the instance file
	 */
	public Solver(String src) {
		Scanner in;
		try {
			File file = new File("rsc",src);
			in = new Scanner(new FileReader(file));
			/*
			 * First line: nb night
			 * Second: nb of obs
			 * Then:
			 * -carac obs
			 * -for each night the time frame for this obst
			 */
			int nb_night = Tools.stringhandler_int(in.nextLine());
			int nb_obs = Tools.stringhandler_int(in.nextLine());
			this.etoiles = new Observation[nb_obs];
			
			//then we initialize each observation AKA star
			for(int i = 0;i<nb_obs;i++) {
				String obs_data = in.nextLine();
				LinkedList<Night> night_data = new LinkedList<Night>();
				for(int j = 0; j<nb_night;j++) {
					Night nuit = Tools.stringhandler_night(in.nextLine());
					if(nuit.getDebut() != 0 && nuit.getFin() != 0) {
						night_data.add(nuit);
					}
				}
				this.etoiles[i] = Tools.stringhandler_observation(obs_data, night_data);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
