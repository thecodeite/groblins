package ninja.thepurple.groblins.common.entity.groblin.activities;

import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public class ActivityWalkTo extends GroblinActivity {
    private Path pathToStandingPosition;
    private final BlockPos targetBlock;

    public ActivityWalkTo(EntityGroblin groblin, BlockPos targetBlock) {
        super(groblin);
        this.targetBlock = targetBlock;
    }

    @Override
    protected ActivityResult start() {
        System.out.println("Going to add a block at"+ targetBlock);
        double distance = groblin.getDistanceSqToCenter(targetBlock);
        System.out.println("distance = " + distance);

        if (distance < 1.0D) {
            return ActivityResult.DONE;
        } else {

            pathToStandingPosition = groblin.getNavigator().getPathToXYZ(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());

            if (pathToStandingPosition == null) {
                return ActivityResult.CANT_DO;
            }

            groblin.getNavigator().setPath(pathToStandingPosition, 0.5D);
            return ActivityResult.DOING;
        }
    }

    @Override
    protected ActivityResult isFinished() {
        Path path = groblin.getNavigator().getPath();
        if (path != null && path != pathToStandingPosition ) {
            return ActivityResult.CANT_DO;
        }

        if (path != null && !path.isFinished()) {
            return ActivityResult.DOING;
        }

        return ActivityResult.DONE;
    }

    @Override
    public String toString() {
        return "Walking to " + targetBlock;
    }
}
