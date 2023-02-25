package ninja.thepurple.groblins.common.entity.groblin.tasks;

import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

import java.util.LinkedList;
import java.util.List;

public abstract class GroblinTaskWithSubTasks extends GroblinTask {
    private List<GroblinTask> subTasks;

    GroblinTaskWithSubTasks(EntityGroblin groblin) {
        super(groblin);
        subTasks = new LinkedList<>();
    }

    @Override
    public void workOnTask() {
        if (subTasks.isEmpty()) {
            return;
        }

        GroblinTask task;
        do {
            task = subTasks.get(0);

            if (task.taskIsComplete()) {
                subTasks.remove(0);
                task = null;
            }
        } while(!subTasks.isEmpty()) 



    }
}
