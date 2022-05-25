package zad3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class MyFrame extends JFrame implements ActionListener {
    private JPanel taskPanel, buttonPanel, outputPanel;
    private JButton startButton, cancelButton, statusButton, resultButton;
    private JTextArea outputArea;
    private final ArrayList<MyTask> tasks;
    private MyTask currentTask;
    private final ExecutorService exec;

    public MyFrame(TaskHandler handler) {
        this.tasks = handler.getTasks();
        this.exec = handler.getExec();
        setFrame();
        setTaskPanel();
        setThreadList();
        setButtonPanel();
        setButtons();
        setOutputPanel();
        setOutputArea();

        this.pack();
        this.setVisible(true);
    }

    private void setFrame() {
        this.setTitle("Task List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(800, 400));
        this.getContentPane().setBackground(Color.WHITE);
    }

    private void setTaskPanel() {
        this.taskPanel = new JPanel();
        this.taskPanel.setBounds(45, 30, 700, 150);
        this.taskPanel.setLayout(new BorderLayout());
        this.taskPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        this.add(this.taskPanel);
    }

    private void setOutputPanel() {
        this.outputPanel = new JPanel();
        this.outputPanel.setBounds(45, 190, 700, 50);
        this.outputPanel.setLayout(new BorderLayout());
        this.outputPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        this.add(this.outputPanel);
    }

    private void setOutputArea() {
        this.outputArea = new JTextArea("...");

        this.outputPanel.add(this.outputArea);
    }

    private void setThreadList() {
       String[] taskNames = new String[this.tasks.size()];
        for (int i = 0; i < this.tasks.size(); i++)
            taskNames[i] = this.tasks.get(i).getName();

        JList<String> threadList = new JList<>(taskNames);
        this.taskPanel.add(threadList);

        threadList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList locator = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 1) {
                    int index = locator.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = locator.getModel().getElementAt(index);
                        setCurrentTask(o.toString());
                    }
                }
            }
        });
    }

    private void setButtonPanel() {
        this.buttonPanel = new JPanel();
        this.buttonPanel.setBounds(100, 250, 600, 50);
        this.buttonPanel.setBackground(Color.WHITE);
        this.add(this.buttonPanel);
    }

    private void setButtons() {
        startButton = new JButton("Start");
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        statusButton = new JButton("Status");
        statusButton.setBackground(Color.WHITE);
        statusButton.addActionListener(this);
        buttonPanel.add(statusButton);

        resultButton = new JButton("Show result");
        resultButton.setBackground(Color.WHITE);
        resultButton.addActionListener(this);
        buttonPanel.add(resultButton);
    }

    private void setCurrentTask(String taskName) {
        for (MyTask task : this.tasks) {
            if (task.getName().equals(taskName))
                this.currentTask = task;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            if (startButton.equals(source)) {
                if (!this.currentTask.isSubmited()) {
                    this.currentTask.setFuture(this.exec);
                    this.outputArea.setText("Starting " + this.currentTask.getName());
                }
                else
                    this.outputArea.setText("This task was already started.");
            } else if (cancelButton.equals(source)) {
                if (!this.currentTask.isSubmited())
                    this.outputArea.setText("Start this task first.");
                else if (this.currentTask.getFuture().isCancelled())
                    this.outputArea.setText(this.currentTask.getName() + " was already cancelled.");
                else {
                    this.currentTask.getFuture().cancel(true);
                    this.outputArea.setText("Cancelling " + this.currentTask.getName());
                }
            } else if (statusButton.equals(source)) {
                if (this.currentTask.isSubmited()) {
                    if (this.currentTask.getFuture().isCancelled())
                        this.outputArea.setText(this.currentTask.getName() + " is cancelled.");
                    else if (this.currentTask.getFuture().isDone())
                        this.outputArea.setText(this.currentTask.getName() + " is done.");
                    else
                        this.outputArea.setText(this.currentTask.getName() + " is running.");
                } else
                    this.outputArea.setText(this.currentTask.getName() + " is ready.");
            } else if (resultButton.equals(source)) {
                if (!this.currentTask.isSubmited())
                    this.outputArea.setText(String.valueOf(this.currentTask.getResult()));
                else if (this.currentTask.getFuture().isCancelled())
                    this.outputArea.setText(String.valueOf(this.currentTask.getResult()));
                else if (this.currentTask.getFuture().isDone())
                    this.outputArea.setText(String.valueOf(this.currentTask.getFuture().get()));
                else
                    this.outputArea.setText(this.currentTask.getName() + " is not ready yet.");
            }
        }catch (NullPointerException ex){
            this.outputArea.setText("Select Task.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
