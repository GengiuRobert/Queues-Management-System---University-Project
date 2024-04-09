package GUI;

import Logic.SelectionPolicy;
import Logic.SimulationManager;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
        this.view.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clientsNumber = view.getClientsNumber();
                int queuesNumber = view.getQueuesNumber();
                int simulationTime = view.getSimulationTime();
                int arrivalTime1 = view.getArrivalTime1();
                int arrivalTime2 = view.getArrivalTime2();
                int serviceTime1 = view.getServiceTime1();
                int serviceTime2 = view.getServiceTime2();
                int strategy = view.getStrategy();
                SelectionPolicy selectionPolicy = null;
                if (strategy == 1) {
                    selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                } else if (strategy == 0) {
                    selectionPolicy = SelectionPolicy.SHORTEST_TIME;
                }
                SimulationManager gen = new SimulationManager(clientsNumber, arrivalTime2, arrivalTime1, serviceTime2, serviceTime1, simulationTime, queuesNumber,
                        selectionPolicy);

                Thread t = new Thread(gen);
                t.start();

                displayQueueContents(gen);
                Thread timeMonitorThread = new Thread(() -> {
                    try {
                        Thread.sleep(simulationTime * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                timeMonitorThread.start();
            }
        });
    }

    private void displayQueueContents(SimulationManager simulationManager) {
        JFrame frame = new JFrame("Queue Contents");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setBackground(new Color(144, 238, 144));
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            FileWriter writer = new FileWriter("results.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            final int[] totalWaitingTime = {0};
            final int[] totalServiceTime = {0};
            final int[] peakHour = {0};
            final int[] maxTasksAtPeakHour = {0};

            Thread displayThread = new Thread(() -> {
                int currentTime = 0;

                while (currentTime <= view.getSimulationTime()) {
                    StringBuilder content = new StringBuilder();
                    content.append("Current time: ").append(currentTime).append("\n\n");

                    content.append("Generated tasks:\n");
                    ArrayList<Task> generatedTasks = simulationManager.getGeneratedTasks();
                    for (Task task : generatedTasks) {
                        content.append("(").append(task.getID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append(")  |  ");
                    }
                    content.append("\n\n");

                    for (int i = 0; i < simulationManager.numberOfQueues; i++) {
                        content.append("Queue ").append(i + 1).append(": ");
                        BlockingQueue<Task> tasks = simulationManager.scheduler.getServers().get(i).getTasks();

                        if (tasks.isEmpty()) {
                            content.append("No tasks in queue.");
                        } else {
                            for (Task task : tasks) {
                                content.append("(").append(task.getID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append(")  |  ");
                                if (((ArrayList<?>) generatedTasks).contains(task)) {
                                    generatedTasks.remove(task);
                                }
                            }
                        }
                        content.append("\n");
                    }

                    try {
                        bufferedWriter.write(content.toString());
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    textArea.setText(content.toString());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentTime++;

                    totalWaitingTime[0] += simulationManager.getTotalWaitingTime();
                    totalServiceTime[0] += simulationManager.getTotalServiceTime();
                    int tasksAtCurrentHour = simulationManager.getTasksAtCurrentHour(currentTime);
                    if (tasksAtCurrentHour > maxTasksAtPeakHour[0]) {
                        maxTasksAtPeakHour[0] = tasksAtCurrentHour;
                        peakHour[0] = currentTime;
                    }
                }

                double averageWaitingTime = 0.0;
                double averageServiceTime = 0.0;

                int processedTasks = simulationManager.getProcessedTasks();
                if (processedTasks > 0) {
                    averageWaitingTime = (double) totalWaitingTime[0] / processedTasks;
                    averageServiceTime = (double) totalServiceTime[0] / processedTasks;
                }
                String formattedAverageWaitingTime = String.format("%.2f", averageWaitingTime);
                String formattedAverageServiceTime = String.format("%.2f", averageServiceTime);
                String results = "\n\nAverage Waiting Time: " + formattedAverageWaitingTime + "\n" +
                        "Average Service Time: " + formattedAverageServiceTime + "\n" +
                        "Peak Hour: " + peakHour[0] + " with " + maxTasksAtPeakHour[0] + " tasks";
                textArea.append(results);
                try {
                    bufferedWriter.write(results);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            displayThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
