import java.net.*;
import java.io.*;
import java.util.*;

public class Roulette {
	/**
	 * calculate RTP of roulette game where user only bets on black when ball lands
	 * on black, player gets 2x input, else player loses the bet amount RTP % =
	 * credited winnings/wagered amount* 100 simulation needs to be run 10 million
	 * times to start simulation, use command line argument to define how many
	 * threads we use for simulation
	 * 
	 * 
	 * output the time it took for simulation to run along w calc RTP value
	 **/
	int threadInput;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  if (args.length != 1) {
		  System.err.println("Usage: java Roulette <threadInput>"); System.exit(1); }
		 

		Scanner scan = new Scanner(System.in);
		 int threadInput = Integer.parseInt(args[0]);
		//int threadInput = scan.nextInt();
		double finalRTPSum = 0;
		ArrayList<Simulation> simulationThreads = new ArrayList<>();

		double startTiming = System.nanoTime();
		for (int i = 0; i < threadInput; i++) {
			Simulation sim = new Simulation(threadInput);
			// save in AL
			simulationThreads.add(sim);
			sim.start();

		}
		// go thru AL, join
		for (int j = 0; j < simulationThreads.size(); j++) {
			try {
				simulationThreads.get(j).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// get RTPs
			finalRTPSum+=simulationThreads.get(j).result;
		}
		double finalRTP = finalRTPSum/threadInput;
		System.out.println("RTP: " + finalRTP);
		// timer end here
		double totalTime = (System.nanoTime() - startTiming)/100000000;
		System.out.println("Simulation Time: " + (totalTime) + "s ");
	}

}

class Simulation extends Thread {
	Scanner scan = new Scanner(System.in);
	int threadInput;
	double totalRTPSum;
	double result = 0;


	// add constructor -- needs string,
	public Simulation(int threadInput) {
		this.threadInput = threadInput;
		this.result = result;
	}

	Random rand = new Random();

	public void run() {
		int winnings = 0;
		int betAmount = 100;
	    result = 0;


		double neededIterations = 10000000 / threadInput;
		for (int i = 0; i < neededIterations; i++) {
			int run = rand.nextInt(37);
			if (run % 2 == 1 && run != 0) {
				// win
				winnings = winnings + 200;
			}

		}

		this.result = winnings / (neededIterations * (100)) * 100;
		//System.out.println("RTP: " + result);
	}


}
