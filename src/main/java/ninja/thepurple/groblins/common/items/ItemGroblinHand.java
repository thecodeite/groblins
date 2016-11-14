package ninja.thepurple.groblins.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ninja.thepurple.groblins.common.blocks.ModBlocks;

import javax.annotation.Nullable;

public class ItemGroblinHand extends BasicItem {

    public ItemGroblinHand() {
        super("groblinHand");
        this.maxStackSize = 1;

    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemStackIn, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult(EnumActionResult.FAIL, itemStackIn);
            }
            else
            {
                boolean isReplaceable = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = isReplaceable && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemStackIn))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
                else if (this.tryPlace(playerIn, worldIn, blockpos1))
                {
                    playerIn.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
                }
                else
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
            }
        }
    }

    public boolean tryPlace(@Nullable EntityPlayer worldIn, World world, BlockPos posIn)
    {
        IBlockState iblockstate = world.getBlockState(posIn);
        Material material = iblockstate.getMaterial();
        boolean isNotSolid = !material.isSolid();
        Block block = iblockstate.getBlock();
        boolean isReplaceable = block.isReplaceable(world, posIn);

        if (!world.isAirBlock(posIn) && !isNotSolid && !isReplaceable)
        {
            return false;
        }
        else if (block == ModBlocks.groblinPlasma)
        {
            return false;
        }
        else
        {
            if (!world.isRemote && (isNotSolid || isReplaceable) && !material.isLiquid())
            {
                world.destroyBlock(posIn, true);
            }

            world.setBlockState(posIn, ModBlocks.groblinPlasma.getDefaultState(), 11);

            return true;
        }
    }
}