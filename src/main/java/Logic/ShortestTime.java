package Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class ShortestTime implements StrategyInterface{
    @Override
    public void addTask(ArrayList<Server> servers, Task task) {
        Server serverWithShortestTime = servers.get(0);
        int shortestTime = Integer.MAX_VALUE;

        for (Server server : servers) {
            int estimatedRemainingTime = calculateEstimatedRemainingTime(server, task);
            if (estimatedRemainingTime < shortestTime) {
                shortestTime = estimatedRemainingTime;
                serverWithShortestTime = server;
            }
        }

        serverWithShortestTime.addTask(task);
    }

    private int calculateEstimatedRemainingTime(Server server, Task task) {
        int totalServiceTime = server.getTotalServiceTime() + task.getServiceTime();
        int totalWaitingTime = server.getTotalWaitingTime() + server.getTasks().size() * task.getServiceTime();
        return totalServiceTime + totalWaitingTime;
    }
}
