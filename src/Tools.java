/**
 * @author Sébastien
 *
 */
public class Tools {

	/**
	 * A static method to return the int at the end of a string
	 * @param line: the string to decipher
	 * @return the int value at the end of the string
	 */
	public static int stringhandler_int(String line) {
		return Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
	}
	
	/**
	 * A static method to return an observation given a string containing the raw observation and an array containing the data for each night of this observation
	 * @param line: the line at the format Obs 0 priority : 30
	 * @param data: an array with the data for all night concerning our observation
	 * @return the finalized observation
	 */
	public static Observation stringhandler_observation(String line, Night[] data) {
		int id = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.indexOf("p")-1));
		int priority = Integer.parseInt(line.substring(line.indexOf(":")+2, line.length()));
		return new Observation(id, priority, data);
	}
	
	/**
	 * A static method to return a night given a string at the specified format
	 * @param line: a line at the format "Night 57: p 78 r 417 m 475 d 525" OR "Night 58: "
	 * duree debut meridien fin
	 * @return a Night object
	 */
	public static Night stringhandler_night(String line) {
		int id = Integer.parseInt(line.substring(line.indexOf(" ")+1, line.indexOf(":")));
		int deb = 0;
		int fin = 0;
		if(line.indexOf(":")+1 != line.length()-1) {
			deb = Integer.parseInt(line.substring(line.indexOf("r")+2, line.indexOf("m")-1));
			fin = Integer.parseInt(line.substring(line.indexOf("d")+2, line.length()-1));
		}
		return new Night(id, deb, fin);
	}
}
