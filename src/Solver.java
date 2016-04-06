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
	
	public Solver(String src) {
		Scanner in;
		try {
			in = new Scanner(new FileReader("ins_400_71_1"));
			
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
