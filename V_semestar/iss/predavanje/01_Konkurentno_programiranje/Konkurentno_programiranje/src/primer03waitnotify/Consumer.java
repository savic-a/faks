package primer03waitnotify;

public class Consumer extends Thread {
	private Buffer buffer;
	private int count;
  
	public Consumer(int id, Buffer buffer, int count) {
		this.buffer = buffer;
		this.count = count;
	}
  
  public void run() {
    for (int i = 0; i < count; i++)
      buffer.read();
  }
  
}