package zad1;

import java.util.ArrayList;

public class Letters {
    private final String letters;

    public Letters (String letters) {
        this.letters = letters;
    }

    public ArrayList<Thread> getThreads() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < this.letters.length(); i++) {
            String threadLetter = this.letters.substring(i, i + 1);
            Thread temp = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        System.out.print(threadLetter);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }}});
            temp.setName("Thread " + threadLetter);
            threads.add(temp);
        }
        return threads;
    }
}

