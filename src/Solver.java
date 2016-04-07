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
			File file = new File("Instances",src);
			in = new Scanner(new FileReader(file));
			System.out.println(in.nextLine());
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
