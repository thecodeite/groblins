package ninja.thepurple.groblins.common.entity.groblin.tasks;

import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public class GroblinMakeWoodenToolsTask extends GroblinTask {
    public GroblinMakeWoodenToolsTask(EntityGroblin groblin) {
        super(groblin);
    }

    @Override
    public void prepareTask() {

    }

    @Override
    public void workOnTask() {

    }

    @Override
    public boolean taskIsComplete() {
        return groblin.getBestToolLevel("all") >= 1;
    }
}
