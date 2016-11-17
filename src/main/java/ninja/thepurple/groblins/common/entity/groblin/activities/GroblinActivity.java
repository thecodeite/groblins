package ninja.thepurple.groblins.common.entity.groblin.activities;

import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public abstract class GroblinActivity {
    protected final EntityGroblin groblin;
    private boolean started;
    public GroblinActivity(EntityGroblin groblin) {

        this.groblin = groblin;
        started = false;
    }

    public final ActivityResult update() {
        if(started) {
            return isFinished();
        } else {
            started = true;
            return start();
        }
    }

    protected abstract ActivityResult start();
    protected abstract ActivityResult isFinished();

    public enum ActivityResult {
        DOING, DONE, CANT_DO
    }
}

