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

    private int breakingTime = 0;
    public ActivityBreakBlock(EntityGroblin groblin, BlockPos breakBlockPos) {
        super(groblin, breakBlockPos);
    }

    @Override
    protected String getInteractionLabel() {
        return "break block";
    }

    @Override
    protected ActivityResult interactWithWorld(BlockPos interactionPos) {
        ++this.breakingTime;
        System.out.println("I'm here, breaking at "+ interactionPos + " breakingTime: "+ breakingTime);

        return WorldInteractionHelper.tryBreakBlock(this.groblin, interactionPos, groblin.worldObj, breakingTime);
    }
}

