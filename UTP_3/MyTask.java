package zad3;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MyTask implements Callable<Integer> {
    private int counter;
    private String name;
    private boolean isSubmited = false;
    private Future futureTask;
    private int result = 0;

    public MyTask(String name, int counter){
        this.name = name;
        this.counter = counter;
    }
    public String getName(){
        return this.name;
    }

    public int getResult(){
        return this.result;
    }

    public boolean isSubmited(){
        return isSubmited;
    }

    private void setSubmitedStatus(){
        this.isSubmited = true;
    }

    public void setFuture(ExecutorService exec){
        this.futureTask = exec.submit(this);
        setSubmitedStatus();
    }

    public Future getFuture(){
        return this.futureTask;
    }

    @Override
    public Integer call() throws Exception {
        int random;
        int i = 0;
        while (i < this.counter) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored){};
            random = (int)(Math.random() * 50 + 1);
            this.result += random;
            i++;
        }
        return this.result;
    }
}
