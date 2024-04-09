package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    public BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int totalWaitingTime;
    private int totalClientsServed;
    private int totalServiceTime;

    public Server(int waitingPeriod) {
        this.waitingPeriod = new AtomicInteger(waitingPeriod);
        this.tasks = new LinkedBlockingDeque<>();
        this.totalClientsServed = 0;
        this.totalWaitingTime = 0;
        this.totalServiceTime = 0;

    }

    public void addTask(Task task) {
        this.tasks.add(task);
        this.waitingPeriod.addAndGet(task.getServiceTime());
        this.totalServiceTime += task.getServiceTime();
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    public int getTotalClientsServed() {
        return totalClientsServed;
    }


    public BlockingQueue<Task> getTasks() {
        return tasks;
    }


    @Override
    public void run() {
        while (true) {
            try {
                if (!tasks.isEmpty()) {
                    for (Task t : tasks) {
                        int currentTimeOfTask = t.getWaitingTime() + 1;
                        t.setWaitingTime(currentTimeOfTask);
                    }
                    tasks.element().setWaitingTime(tasks.element().getWaitingTime() - 1);
                    tasks.element().setServiceTime(tasks.element().getServiceTime() - 1);
                    if (tasks.element().getServiceTime() == 0) {
                        this.totalClientsServed += 1;
                        this.totalWaitingTime += tasks.element().getWaitingTime();
                        this.totalServiceTime -= tasks.element().getServiceTime();
                        tasks.take();
                    }
                    Thread.sleep(1000);

                    if (this.waitingPeriod.intValue() > 0) {
                        this.waitingPeriod.decrementAndGet();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
