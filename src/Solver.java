import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 
 * @author Sébastien
 *
 */
public class Solver {

	private Telescope tel;
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
				Night[] night_data = new Night[nb_night];
				for(int j = 0; j<nb_night;j++) {
					night_data[j] = Tools.stringhandler_night(in.nextLine());
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
