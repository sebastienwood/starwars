import java.util.*;

public class Executable {

	public static void main(String[] args) {

//		Genetic sol = new Genetic("ins_400_71_1", 500);
//		
//		/*Pilot the treads*/
//		sol.live(1);
//		
//		Schedule alpha = sol.getAlpha();
//		Recuit rec = new Recuit(alpha, 80, 0.1, 5);
//		System.out.println(rec.activate());
//		
//		System.out.println(rec.getValue(false));
		long a=System.currentTimeMillis();
		Etoile[] data= Filehandler.read("ins_400_71_1");
		ScheduleQ sc= new ScheduleQ(data.length);
//		System.out.println(sc.BestSchedule(10,"ins_400_71_1").getValue(false) );
		LinkedList<Observation> bestway= sc.AntColonyFor(0.002,data, 10, 20, 0.001, 0.0007);
		System.out.println(sc.getGlobalInterest(sc.planning));
		int[] tabNuits=sc.communicateNights();
		Schedule planning= new Schedule(1,tabNuits,data);
		Genetic gen=new Genetic(data, 100, planning);
		gen.changePop(sc.SendAnts(100, "ins_400_71_1"));
		gen.live(0.06);
		Schedule alpha=gen.getAlpha();
		System.out.println(alpha.getValue(false));
		Recuit rec=new Recuit(alpha,80,0.1,5);
		System.out.println(rec.activate());
		System.out.println(rec.getValue(false));
		
		long b=System.currentTimeMillis();
		System.out.println("le temps mis par l'algorithme est de "+(b-a));
	}

}
