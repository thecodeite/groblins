package ninja.thepurple.groblins.common.entity.groblin.activities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.StandingPositionHelper;
import ninja.thepurple.groblins.common.entity.groblin.helpers.WorldInteractionHelper;

public class ActivityBreakBlock extends ActivityInteractWithWorld {

    public ActivityBreakBlock(EntityGroblin groblin, BlockPos breakBlockPos) {
        super(groblin, breakBlockPos);
    }

    @Override
    protected String getInteractionLabel() {
        return "break block";
    }

    @Override
    protected ActivityResult interactWithWorld(BlockPos interactionPos) {
        System.out.println("I'm here, breaking at "+ interactionPos);
        boolean success = WorldInteractionHelper.tryBreakBlock(interactionPos, groblin.worldObj);
        return success ? ActivityResult.DONE : ActivityResult.CANT_DO;
    }
}

