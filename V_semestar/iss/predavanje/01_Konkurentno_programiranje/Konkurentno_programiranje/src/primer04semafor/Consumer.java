package primer04semafor;


public class Consumer extends Thread {

	private ResourcePool pool;
	private int count;
	private int id;

	public Consumer(int id, ResourcePool pool, int count) {
		this.id = id;
		this.pool = pool;
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < count; i++) {
			System.out.println(id + ". Fetching object from pool.");
			try {
				Object item = pool.getItem();
				System.out.println(id + ". Fetched object from pool: " + item);
				sleep(500);
				System.out.println(id + ". Returned " + item + " object to pool.");
				pool.putItem(item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
