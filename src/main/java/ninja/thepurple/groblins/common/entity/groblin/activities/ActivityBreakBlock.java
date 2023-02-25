package ninja.thepurple.groblins.common.entity.groblin.activities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.StandingPositionHelper;
import ninja.thepurple.groblins.common.entity.groblin.helpers.WorldInteractionHelper;

public class ActivityBreakBlock extends ActivityInteractWithWorld {

    private final ItemStack tool;
    private int breakingTime = 0;
    public ActivityBreakBlock(EntityGroblin groblin, BlockPos breakBlockPos, ItemStack tool) {
        super(groblin, breakBlockPos);
        this.tool = tool;
    }

    @Override
    protected String getInteractionLabel() {
        return "break block";
    }

    @Override
    protected ActivityResult interactWithWorld(BlockPos interactionPos) {
        ++this.breakingTime;
        groblin.setHeldItem(EnumHand.MAIN_HAND, tool);
        System.out.println("I'm here, breaking at "+ interactionPos + " breakingTime: "+ breakingTime);

        return WorldInteractionHelper.tryBreakBlock(this.groblin, interactionPos, groblin.worldObj, breakingTime);
    }
}

