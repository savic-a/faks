package primer04semafor;


public class Test04 {

	public static final int MAX_COUNT = 10;
	public static void main(String[] args) {
		ResourcePool pool = new ResourcePool();
		for (int i = 0; i < MAX_COUNT; i++) {
			new Consumer(i, pool, MAX_COUNT).start();
		}
	}

}
