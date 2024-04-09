package Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public interface StrategyInterface {

    public void addTask(ArrayList<Server> servers, Task task);

}
