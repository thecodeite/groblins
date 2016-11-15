package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class BasicSummonBlockRitual extends Ritual {
    protected IBlockState blockToSummon;

    public BasicSummonBlockRitual(String grid, Block blockToSummon) {
        super(grid);
        this.blockToSummon = blockToSummon.getDefaultState();
    }

    public BasicSummonBlockRitual(String grid, IBlockState blockToSummon) {
        super(grid);
        this.blockToSummon = blockToSummon;
    }

    @Override
    public boolean eventSiteMustBeEmpty() {
        return true;
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
            IBlockState iblockstate = world.getBlockState(blockPos);
            Material material = iblockstate.getMaterial();
            boolean isNotSolid = !material.isSolid();
            Block block = iblockstate.getBlock();
            boolean isReplaceable = block.isReplaceable(world, blockPos);

            if (!world.isAirBlock(blockPos) && !isNotSolid && !isReplaceable) {
                return false;
            } else {
                if (!world.isRemote && (isNotSolid || isReplaceable) && !material.isLiquid()) {
                    world.destroyBlock(blockPos, true);
                }

                System.out.println("Putting block in world blockPos = " + blockPos);
                world.setBlockState(blockPos, blockToSummon, 3);
            }
        }

        return true;
    }

    @Override
    public ValidRitual isRitualMatch(World worldIn, ArrayList<BlockPos> positions) {
       return super.isRitualMatch(worldIn, positions);
    }
}
