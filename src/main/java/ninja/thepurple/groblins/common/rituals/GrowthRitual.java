package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class GrowthRitual extends Ritual {

    public GrowthRitual(String grid) {
        super(grid);
    }

    @Override
    public boolean eventSiteMustBeEmpty() {
        return false;
    }

    @Override
    public boolean activateRitual(ValidRitual validRitual, @Nullable EntityPlayer playerIn, World world) {
        if (validRitual == null) {
            return false;
        }

        BlockPos[] eventPositions = validRitual.getEventPositions();

        if (eventPositions.length == 0) {
            System.err.println("No event positions found");
            return false;
        }

        for (BlockPos blockPos : validRitual.getEventPositions()) {
            IBlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if(block instanceof IGrowable) {
                IGrowable growable = (IGrowable) block;
                if (growable.canGrow(world, blockPos, blockState, !world.isRemote)) {
                    growable.grow(world, new Random(), blockPos, blockState);
                }
            }

        }

        return true;
    }
}
