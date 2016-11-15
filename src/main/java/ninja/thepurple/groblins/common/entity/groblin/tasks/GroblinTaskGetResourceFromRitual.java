package ninja.thepurple.groblins.common.entity.groblin.tasks;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.ExpandingChunkSearch;
import ninja.thepurple.groblins.common.entity.groblin.helpers.FlatSpaceFinder;
import ninja.thepurple.groblins.common.rituals.SummonBlockRitual;

import static ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskGetResourceFromRitual.TaskState.FINDING_SPACE;

public class GroblinTaskGetResourceFromRitual extends GroblinTask {
    private final SummonBlockRitual ritual;
    private BlockPos ritualPosition;

    enum TaskState {
        FINDING_SPACE, MOVING_TO_SPACE, DRAWING_RITUAL, HARVEST_PRODUCT, FINISHED
    }
    private TaskState state;

    public GroblinTaskGetResourceFromRitual(EntityGroblin groblin, SummonBlockRitual ritual) {
        super(groblin);
        this.ritual = ritual;
        state = FINDING_SPACE;
    }

    @Override
    public String toString() {
        return "Get " + ritual.blockToSummon + " from ritual";
    }

    @Override
    public boolean workOnTask() {
        switch (this.state) {
            case FINDING_SPACE: findSpace(groblin); return false;
            case MOVING_TO_SPACE: movingToSpace(groblin); return false;
            case DRAWING_RITUAL: drawRitual(groblin); return false;
            case HARVEST_PRODUCT: findSpace(groblin); return false;
            default:
            case FINISHED: return true;
        }
    }

    private void findSpace(EntityGroblin groblin) {
        ritualPosition = spaceSearch(groblin);
        if (ritualPosition == null) {
            System.err.println("Groblin could not find a space to perform ritual. Going to sleep.");
            groblin.wakeAt = groblin.worldObj.getWorldTime() + (20*30);
            this.state = TaskState.FINISHED;
        } else {
            double x = ritualPosition.getX() + 0.5D;
            double y = ritualPosition.getY() + 0.5D;
            double z = ritualPosition.getZ() + 0.5D;
            groblin.getNavigator().tryMoveToXYZ(x, y, z, 1.0D);
            this.state = TaskState.MOVING_TO_SPACE;
        }
    }

    private void movingToSpace(EntityGroblin groblin) {
        if(groblin.getNavigator().noPath()) {
            System.out.println("Standing at ritual site");
            this.state = TaskState.DRAWING_RITUAL;
        }
    }

    private void drawRitual(EntityGroblin groblin) {
        System.err.println("Groblin should draw ritual but going to sleep instead.");
        groblin.wakeAt = groblin.worldObj.getWorldTime() + (20*30);
        this.state = TaskState.FINISHED;
    }

    private BlockPos spaceSearch(EntityGroblin groblin) {
        System.out.println("I'm standing at  = " + groblin.getPosition());
        ExpandingChunkSearch positions = new ExpandingChunkSearch(groblin.getPosition(), groblin.homeChunk);
        for(BlockPos pos : positions) {
            System.out.println("Checking  = " + pos);
            if(pos == null) return null;
            if(FlatSpaceFinder.isFlatSquare(groblin.homeChunk, pos, 2)) {
                System.out.println("Found a good spot at = " + pos);
                return pos;
            }
        }

        return null;
    }
}
