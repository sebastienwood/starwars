import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Sébastien
 *
 */
public class Filehandler {

	/**
	 * A static method to return the int at the end of a string
	 * @param line: the string to decipher
	 * @return the int value at the end of the string
	 */
	private static int stringhandler_int(String line) {
		return Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
	}
	
	/**
	 * A static method to return an observation given a string containing the raw observation and an array containing the data for each night of this observation
	 * @param line: the line at the format Obs 0 priority : 30
	 * @param data: an array with the data for all night concerning our observation
	 * @return the finalized observation
	 */
	private static Etoile stringhandler_observation(String line, LinkedList<Night> data) {
		int id = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.indexOf("p")-1));
		int priority = Integer.parseInt(line.substring(line.indexOf(":")+2, line.length()));
		
		return new Etoile(id, priority, data);
	}
	
	/**
	 * A static method to return a night given a string at the specified format
	 * @param line: a line at the format "Night 57: p 78 r 417 m 475 d 525" OR "Night 58: "
	 * duree debut meridien fin
	 * @return a Night object
	 */
	private static Night stringhandler_night(String line) {
		int id = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.indexOf(":")));
		int deb = 0;
		int fin = 0;
		int duree = 0;
		if(line.indexOf(":")+1 != line.length()-1) {
			deb = Integer.parseInt(line.substring(line.indexOf("r")+2, line.indexOf("m")-1));
			fin = Integer.parseInt(line.substring(line.indexOf("d")+2, line.length()-1));
			duree = Integer.parseInt(line.substring(line.indexOf("p")+2, line.indexOf("r")-1));
		}
		return new Night(id, deb, fin, duree);
	}
	
	/**
	 * A static method to read a complete file
	 * @param src: name of the file
	 * @return all the Etoile in an array
	 */
	public static Etoile[] read(String src) {
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
			int nb_night = Filehandler.stringhandler_int(in.nextLine());
			int nb_obs = Filehandler.stringhandler_int(in.nextLine());
			Etoile[] etoiles = new Etoile[nb_obs];
			
			//then we initialize each observation AKA star
			for(int i = 0;i<nb_obs;i++) {
				String obs_data = in.nextLine();
				LinkedList<Night> night_data = new LinkedList<Night>();
				for(int j = 0; j<nb_night;j++) {
					Night nuit = Filehandler.stringhandler_night(in.nextLine());
					if(nuit.getDebut() != 0 && nuit.getFin() != 0) {
						night_data.add(nuit);
					}
				}
				etoiles[i] = Filehandler.stringhandler_observation(obs_data, night_data);
			}
			in.close();
			return etoiles;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
