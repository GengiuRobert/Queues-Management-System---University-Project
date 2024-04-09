package Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {

    private ArrayList<Task> generatedTasks;
    public int numberOfClients;
    public int maximumArrivalTime;
    public int minimArrivalTime;
    public int maximumServiceTime;
    public int minimServiceTime;
    public int timeLimit;
    public int numberOfQueues;
    public int currentTime;

    public SelectionPolicy selectionPolicy ;

    public Scheduler scheduler;


    public SimulationManager(int numberOfClients, int maximumArrivalTime, int minimArrivalTime, int maximumServiceTime, int minimServiceTime, int timeLimit, int numberOfQueues,
                             SelectionPolicy selectionPolicy) {
        this.numberOfClients = numberOfClients;
        this.maximumArrivalTime = maximumArrivalTime;
        this.minimArrivalTime = minimArrivalTime;
        this.maximumServiceTime = maximumServiceTime;
        this.minimServiceTime = minimServiceTime;
        this.numberOfQueues = numberOfQueues;
        this.timeLimit = timeLimit;
        this.selectionPolicy = selectionPolicy;


        this.scheduler = new Scheduler(this.numberOfQueues, 100);
        this.scheduler.changeStrategy(selectionPolicy);
        for (Thread thread : scheduler.threads) {
            thread.start();
        }
        generateRandomTasks();
    }


    public ArrayList<Task> getGeneratedTasks() {
        return generatedTasks;
    }


    public void generateRandomTasks() {
        Random random = new Random();
        this.generatedTasks = new ArrayList<>();
        for (int i = 0; i < this.numberOfClients; i++) {
            int arrivalTime;
            do {
                arrivalTime = random.nextInt(this.maximumArrivalTime - this.minimArrivalTime + 1) + this.minimArrivalTime;
            } while (arrivalTime == 0);
            if (arrivalTime == 0) {
                arrivalTime += 1;
            }
            int serviceTime;
            do {
                serviceTime = random.nextInt(this.maximumServiceTime - this.minimServiceTime + 1) + this.minimServiceTime;
            } while (serviceTime == 0);
            if (serviceTime == 0) {
                serviceTime += 1;
            }
            Task task = new Task(i, arrivalTime, serviceTime);
            this.generatedTasks.add(task);
        }

        Collections.sort(generatedTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return Integer.compare(t1.getArrivalTime(), t2.getArrivalTime());
            }
        });
    }

    @Override
    public void run() {
        this.currentTime = 0;
        while (this.currentTime <= this.timeLimit) {
            if (((this.generatedTasks.isEmpty()) && (this.scheduler.queuesEmptyFromServers()))) {
                break;
            }

            System.out.println("current time ->" + this.currentTime);
            System.out.println();

            for (int i = 0; i < this.numberOfQueues; i++) {
                System.out.print("Queue " + (i + 1) + ": ");
                Server server = this.scheduler.getServers().get(i);
                BlockingQueue<Task> tasks = server.getTasks();


                for (Task task : tasks) {
                    System.out.print("(" + task.getID() +
                            "," + task.getArrivalTime() +
                            "," + task.getServiceTime() + ")" + " | ");
                }


                if (tasks.isEmpty()) {
                    System.out.print("No tasks in queue.");
                }

                System.out.println();
            }
            ArrayList<Task> remainingTask = new ArrayList<>();
            for (Task task : this.generatedTasks) {
                if (task.getArrivalTime() == this.currentTime) {
                    scheduler.dispatchTask(task);
                } else {
                    remainingTask.add(task);
                }
            }

            this.currentTime += 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTotalServiceTime() {
        int result = 0;
        for (Task task : this.generatedTasks) {
            result += task.getServiceTime();
        }
        return result;
    }

    public int getTotalWaitingTime() {
        int result = 0;
        for (Task task : this.generatedTasks) {
            result += task.getWaitingTime();
        }
        return result;
    }

    public int getTasksAtCurrentHour(int currentHour) {
        int result = 0;
        for (Task task : this.generatedTasks) {
            if (task.getArrivalTime() == currentHour)
                result += 1;
        }

        return result;
    }

    public int getProcessedTasks() {
        int result = 0;
        for (Server server : scheduler.getServers()) {
            result += server.getTotalClientsServed();
        }
        return result;
    }
}
