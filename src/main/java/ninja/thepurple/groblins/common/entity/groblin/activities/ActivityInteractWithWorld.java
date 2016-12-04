package ninja.thepurple.groblins.common.entity.groblin.activities;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.StandingPositionHelper;

public abstract class ActivityInteractWithWorld extends GroblinActivity {
    private Path pathToStandingPosition;
    private final BlockPos interactionPos;

    public ActivityInteractWithWorld(EntityGroblin groblin, BlockPos interactionPos) {
        super(groblin);
        this.interactionPos = interactionPos;
    }

    @Override
    protected ActivityResult start() {
        System.out.println("Going to "+this.getInteractionLabel()+" at "+ interactionPos);
        ActivityResult isClose = closeToTarget();

        switch (isClose) {
            case DOING:
                return ActivityResult.DOING;
            case DONE:
                return interactWithWorld(interactionPos);
            case CANT_DO:
            default:
                return ActivityResult.CANT_DO;
        }
    }

    protected abstract String getInteractionLabel();
    protected abstract ActivityResult interactWithWorld(BlockPos interactionPos);

    @Override
    protected ActivityResult isFinished() {
        if (pathToStandingPosition != null && !pathToStandingPosition.isFinished()) {
            return ActivityResult.DOING;
        }

        ActivityResult isClose = closeToTarget();
        switch (isClose) {
            case DOING:
                return ActivityResult.DOING;
            case DONE:
                return interactWithWorld(interactionPos);
            case CANT_DO:
            default:
                return ActivityResult.CANT_DO;
        }
    }

    @Override
    public String toString() {
        return "ActivityBreakBlock " + interactionPos;
    }

    private ActivityResult closeToTarget() {
        double distanceSq = groblin.getDistanceSqToCenter(interactionPos);
        System.out.println("distanceSq = " + distanceSq);

        if (distanceSq > 16.0D) {
            Vec3d spotToStand = StandingPositionHelper.pickSpotToAddBlockFrom(interactionPos, groblin.getPositionVector(), groblin.worldObj);
            if (spotToStand == null) {
                System.out.println("Task failed as nowhere to stand");
                return ActivityResult.CANT_DO;
            }

            System.out.println("I'll stand at "+ spotToStand);

            distanceSq = groblin.getDistanceSqToCenter(interactionPos);
            if (distanceSq < 4D) {
                return ActivityResult.DONE;
            } else if (distanceSq > 256.0D) {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.groblin, 14, 3, spotToStand);

                if (vec3d != null) {
                    pathToStandingPosition = this.groblin.getNavigator().getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
                } else {
                    System.out.println("Task failed as no random block towards found");
                    return ActivityResult.CANT_DO;
                }
            } else {
                pathToStandingPosition = groblin.getNavigator().getPathToXYZ(spotToStand.xCoord, spotToStand.yCoord, spotToStand.zCoord);
            }

            if (pathToStandingPosition == null) {
                System.out.println("Can't find a path to that spot");
                return ActivityResult.CANT_DO;
            }

            groblin.getNavigator().setPath(pathToStandingPosition, 0.5D);
            return ActivityResult.DOING;
        } else {
            return ActivityResult.DONE;
        }
    }
}
