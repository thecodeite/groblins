package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTask;

public class EntityAIPerformObjectiveTask extends EntityAIBase {
    private EntityGroblin groblin;
    private GroblinTask activeTask;

    public EntityAIPerformObjectiveTask(EntityGroblin groblin) {
        this.groblin = groblin;
    }

    @Override
    public boolean shouldExecute() {
        return activeTask != null || groblin.objectiveTasks.size() > 0;
    }

    public void startExecuting() {
        if (activeTask == null) activeTask = groblin.objectiveTasks.poll();
    }

    @Override
    public boolean continueExecuting() {
        boolean complete = activeTask.workOnTask();
        return complete;
    }
}
