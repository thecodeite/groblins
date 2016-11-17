package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldInteractionHelper {
    public static boolean tryPlaceBlock(BlockPos pos, IBlockState blockToPlace, World world, EnumFacing needSolidFace) {
        IBlockState iblockstate = world.getBlockState(pos);
        Material material = iblockstate.getMaterial();
        boolean isNotSolid = !material.isSolid();
        Block block = iblockstate.getBlock();
        boolean isReplaceable = block.isReplaceable(world, pos);

        if (!world.isAirBlock(pos) && !isNotSolid && !isReplaceable)
        {
            System.out.println("Cant place block: !world.isAirBlock(pos)=" + !world.isAirBlock(pos) +
             " !isNotSolid=" + !isNotSolid +
             " !isReplaceable" + !isReplaceable);
            return false;
        }
        else if (needSolidFace != null && !world.isSideSolid(pos.offset(needSolidFace.getOpposite()), needSolidFace))
        {
            System.out.println("Cant place block: missing solid surface");
            return false;
        }
        else
        {
            if (!world.isRemote && (isNotSolid || isReplaceable) && !material.isLiquid())
            {
                world.destroyBlock(pos, true);
            }

            world.setBlockState(pos, blockToPlace, 11);

            return true;
        }
    }

    public static boolean tryPlaceBlock(BlockPos pos, IBlockState blockToPlace, World world) {
        return tryPlaceBlock(pos, blockToPlace, world, null);
    }

    public static boolean tryBreakBlock(BlockPos pos, World world) {
        IBlockState blockState = world.getBlockState(pos);
        //blockState.getBlockHardness(world, pos);
        //blockState.getPlayerRelativeBlockHardness()
        //world.sendBlockBreakProgress(-1, pos, 50);
        world.destroyBlock(pos, true);

        return true;
    }
}
