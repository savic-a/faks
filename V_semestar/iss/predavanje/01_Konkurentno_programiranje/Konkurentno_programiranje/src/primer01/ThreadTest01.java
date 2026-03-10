package primer01;

public class ThreadTest01 {

  /** Broj niti koje ce se pokrenuti */
  public static final int THREAD_COUNT = 10;

  /** Pokrece sve niti i zavrsava sa radom 
 * @throws InterruptedException */
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < THREAD_COUNT; i++) {
      PrviThread pt = new PrviThread(i);
      pt.start();
      /* Ako ovo stavimo, onda će glavna nit da odspava, 
       * a za to vreme će PrviThread da radi. 
       * Ako je pauza kratka, onda će se niti pomešati. 
       * Ako je pauza dugačka, niti će se izvršavati jedna za drugom. 
       */
      //Thread.sleep(1); 
      
      /* Ako ovo stavimo, onda će glavna nit ovde sačekati da se PrviThread završi, 
       * pa će tek onda nastaviti petlju, te će se niti izvršavati jedna za drugom.
       */
      //pt.join(); 
    }
    System.out.println("Threads started.");
  }

}