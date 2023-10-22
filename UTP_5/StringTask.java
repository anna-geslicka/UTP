package zad2;

enum TaskState {CREATED, RUNNING, ABORTED, READY}

public class StringTask implements Runnable{
    private String letter;
    private String sentence = "";
    private int count;
    private TaskState state;
    private Thread task;

    public StringTask(String letter, int count){
        this.state = TaskState.CREATED;
        this.letter = letter;
        this.count = count;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < this.count && !this.task.isInterrupted()) {
            this.sentence += this.letter;
            i++;
        }
        this.state = TaskState.READY;
    }

    public String getResult(){
        return this.sentence;
    }

    public TaskState getState(){
        return this.state;
    }

    public void start(){
        this.task = new Thread(this);
        this.task.start();
        this.state = TaskState.RUNNING;
    }

    public void abort(){
        this.task.interrupt();
        this.state = TaskState.ABORTED;
    }

    public boolean isDone(){
        return this.state == TaskState.READY || this.state == TaskState.ABORTED;
    }
}
