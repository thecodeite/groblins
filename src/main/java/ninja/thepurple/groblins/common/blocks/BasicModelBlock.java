package ninja.thepurple.groblins.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BasicModelBlock extends BasicBlock {

    public BasicModelBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    public BasicModelBlock(String unlocalizedName, Material material) {
        super(unlocalizedName, material);
    }

    public BasicModelBlock(String unlocalizedName, float hardness, float resistance) {
        super(unlocalizedName, hardness, resistance);
    }

    public BasicModelBlock(String unlocalizedName) {
        super(unlocalizedName);
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
