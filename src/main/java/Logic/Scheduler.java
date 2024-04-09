package Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private ArrayList<Server> servers;
    public ArrayList<Thread> threads;
    private int maxNoServers;
    private int maxTasksPerServer;
    private StrategyInterface strategy;


    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();
        this.threads = new ArrayList<>();
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(0);
            this.servers.add(server);
            Thread thread = new Thread(server);
            this.threads.add(thread);
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueue();
        }

        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ShortestTime();
        }
    }

    public void dispatchTask(Task t) {
        this.strategy.addTask(this.servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }


    public boolean queuesEmptyFromServers() {
        for (Server server : servers) {
            if (!server.tasks.isEmpty())
                return false;
        }
        return true;
    }

}
