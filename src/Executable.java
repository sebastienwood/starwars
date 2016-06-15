import java.util.*;

public class Executable {

	public static void main(String[] args) {

		
		Master sol = new Master("ins_400_71_1");
		sol.strat_2h();
	}
	
	
	public static void test1() {
		long a=System.currentTimeMillis();
		Etoile[] data= Filehandler.read("ins_400_71_1");
		ScheduleQ sc= new ScheduleQ(data,10);
		sc.live(0.001);
		Schedule omega=sc.getAlpha();
		long c=System.currentTimeMillis();
		
		Recuit rec=new Recuit(omega,80,0.1,5);
		rec.live(0.01);
		System.out.println(rec.getValue(false));
		long b=System.currentTimeMillis();
		System.out.println("le temps mis par l'algorithme est de "+(b-a));
		System.out.println("le temps mis par le recuit est de"+(b-c));
	}
	
	public static void test2() {
		long a=System.currentTimeMillis();
		Etoile[] data= Filehandler.read("ins_400_71_1");
		ScheduleQ sc= new ScheduleQ(data,10);
		sc.live(0.005);
		Schedule omega=sc.getAlpha();
		Genetic gen=new Genetic(data, 100,omega);
		gen.live(0.01);
		Schedule alpha=gen.getAlpha();
		System.out.println(alpha.getValue(false));
		Recuit rec=new Recuit(alpha,80,0.1,5);
		rec.live(0.01);
		System.out.println(rec.getValue(false));
		long b=System.currentTimeMillis();
		System.out.println("le temps mis par l'algorithme est de "+(b-a));
	}
	
	public static void testScheduleQ() {
		long a=System.currentTimeMillis();
		Etoile[] data= Filehandler.read("ins_400_71_1");
		ScheduleQ sc= new ScheduleQ(data,100);
		sc.createAnts(10, false);
		System.out.println(System.currentTimeMillis()-a);
	}
	
	public static int testMaster() {

		Master sol = new Master("ins_1000_142_4");
		
		return (sol.strat_2m_ALL());
	}
	
	public static int testQuentin() {

		Master sol = new Master("ins_1000_142_4");
		return(sol.strat_2m_Quentin());

	}
}
