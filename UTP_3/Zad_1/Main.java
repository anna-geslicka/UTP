/**
 *
 *  @author Gęślicka Anna S21151
 *
 */

package zad1;

import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Letters letters = new Letters("ABCD");
    for (Thread t : letters.getThreads()) System.out.println(t.getName());
    ArrayList<Thread> threads = letters.getThreads();
    for (Thread t : threads) t.start();

    Thread.sleep(5000);

    for (Thread t : threads) t.interrupt();
    System.out.println("\nProgram skończył działanie");
  }

}
