package ninja.thepurple.groblins.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ninja.thepurple.groblins.GroblinsMod;

public class BlockGroblinHouse extends Block
{
    public BlockGroblinHouse()
    {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(10.0F);
        setUnlocalizedName("groblinHouse");
        setRegistryName("groblinHouse");
        setCreativeTab(GroblinsMod.creativeTab);
        setLightLevel(0.4f);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}