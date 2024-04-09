package Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class ShortestQueue implements StrategyInterface{
    @Override
    public void addTask(ArrayList<Server> servers, Task task) {
        Server serverAux = servers.get(0);
        int minQueueSize = serverAux.getTasks().size();
        for (Server s : servers) {
            int currentQueueSize = s.getTasks().size();
            if (currentQueueSize < minQueueSize) {
                minQueueSize = currentQueueSize;
                serverAux = s;
            }
        }
        serverAux.addTask(task);
    }


}
