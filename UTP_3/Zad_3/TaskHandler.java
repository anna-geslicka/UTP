package zad3;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskHandler {
    private final ExecutorService exec = Executors.newFixedThreadPool(3);;

    public ArrayList<MyTask> getTasks(){

        ArrayList<MyTask> tasks = new ArrayList<>();
        tasks.add(new MyTask("Task A", 100));
        tasks.add(new MyTask("Task B", 1500));
        tasks.add(new MyTask("Task C", 3));

        return tasks;
    }

    public ExecutorService getExec(){
        return this.exec;
    }
}
