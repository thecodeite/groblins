package ninja.thepurple.groblins.common.entity.groblin.tasks;

import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public abstract class GroblinTask {
    final EntityGroblin groblin;

    GroblinTask(EntityGroblin groblin) {
        this.groblin = groblin;
    }

    public abstract boolean workOnTask();
}
