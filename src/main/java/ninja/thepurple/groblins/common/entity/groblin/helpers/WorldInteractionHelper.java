package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.activities.GroblinActivity;

import java.util.Set;

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

    public static ItemStack pickTool(IBlockState harvestTarget, IInventory inventory) {
        System.out.println("Groblin has "+inventory.getSizeInventory()+" items to check");

        ItemStack pickedStack = null;
        float bestStrength = 1.0f;

        for (int i=0 ;i<inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            System.out.println("Checking if I can use " + stack + " to harvest " + harvestTarget);

            if (stack != null) {
                float str = stack.getItem().getStrVsBlock(stack, harvestTarget);

                System.out.println("I can use " + stack + " to harvest " + harvestTarget + " with strength " + str);
                if (str > bestStrength) {
                    bestStrength = str;
                    pickedStack = stack;
                }
            }
        }

        return pickedStack;
    }

    public static GroblinActivity.ActivityResult tryBreakBlock(EntityGroblin e, BlockPos pos, World world, int breakingTime) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block.isAir(blockState, world, pos)) {
            return GroblinActivity.ActivityResult.DONE;
        }

        ItemStack tool = e.getHeldItemMainhand();

        float str = tool == null ? 1.0f : tool.getItem().getStrVsBlock(tool, blockState);
        float hardness = blockState.getBlockHardness(world, pos);
        float breakTime = (hardness * 20.0f)/str;

        int progress = (int)((float)breakingTime / breakTime * 10.0F);

        world.sendBlockBreakProgress(e.getEntityId(), pos, progress);
        if(progress >= 10) {

            boolean dropItem = blockState.getMaterial().isToolNotRequired();

            if (!dropItem && tool != null) {
                Set<String> classes = tool.getItem().getToolClasses(tool);
                for(String className : classes) {
                    if(block.isToolEffective(className, blockState)) {
                        dropItem = true;
                    }
                }
            }

            world.destroyBlock(pos, dropItem);

            if (tool != null) {
                tool.damageItem(1, e);
            }

            return GroblinActivity.ActivityResult.DONE;
        }

        return GroblinActivity.ActivityResult.DOING;
    }
}
