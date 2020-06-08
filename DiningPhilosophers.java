import java.util.*;
import java.util.concurrent.Semaphore;
public class DiningPhilosophers {
	
	public static void main(String[] args) {
	    String[] names = {"Philosopher 1", "Philosopher 2", "Philosopher 3", "Philosopher 4", "Philosopher 5"};
	    Semaphore[] fork = new Semaphore[5];
	    Philosopher[] philosopher = new Philosopher[5];

	    for (int i = 0; i < fork.length; i++) {
	        fork[i] = new Semaphore(1);
	    }

	    for (int i = 0; i < philosopher.length; i++) {
	    	// now assign names, and forks to the different philosophers
	        if (i != philosopher.length - 1) {
	            philosopher[i] = new Philosopher(fork[i], fork[i+1], names[i]);
	            philosopher[i].start();
	        } else {
	            philosopher[i] = new Philosopher(fork[0], fork[i], names[i]);
	            philosopher[i].start();
	        }
	    }
	    
	    
	}
	


	static class Philosopher extends Thread {

		private Semaphore leftFork;
		private Semaphore rightFork;
		private String name;
		boolean hasEaten = false;

		Philosopher(Semaphore leftFork, Semaphore rightFork, String name) {
		    this.leftFork = leftFork;
		    this.rightFork = rightFork;
		    this.name = name;
		    this.hasEaten = hasEaten;
		}

		public void run() {

		    try {
		        sleep(500);
		    } catch (InterruptedException ex) {
		    }

		    while (this.hasEaten==false) {
				
				    if(leftFork.tryAcquire()){
				        if(rightFork.tryAcquire()){
				        	//System.out.println(this.name + " has both");
				            try {
				                sleep(1000); // eating;
					        	//System.out.println(this.name + " is eating");

				            } catch (InterruptedException ex) { }
				            System.out.println(this.name );
				            rightFork.release();
				            leftFork.release();  
				            
				            this.hasEaten = true;

				        }
				        else{
				        	leftFork.release();
				        }
				    }
				}
		    }
		}


		
	
	
	

}
