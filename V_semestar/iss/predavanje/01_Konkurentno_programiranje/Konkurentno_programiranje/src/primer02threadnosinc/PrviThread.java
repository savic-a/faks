package primer02threadnosinc;

public class PrviThread extends Thread {

	private static final int COUNT = 10000;

	private int threadID;

	/**
	 * Ovaj objekat se prosleđuje svim nitima da bi se uvećavao brojač.
	 */
	private Counter counter;

	/**
	 * Konstruktor
	 * 
	 * @param threadID
	 *            Identifikator niti
	 */
	public PrviThread(int threadID, Counter counter) {
		this.threadID = threadID;
		this.counter = counter;
	}

	public void run() {
		System.out.println("Thread " + threadID + " started incrementing. Counter state: " + this.counter.count);
		for (int i = 0; i < COUNT; i++) {
			counter.count++;
		}
		System.out.println("Thread " + threadID + " finished incrementing. Counter state: " + this.counter.count);
	}

}