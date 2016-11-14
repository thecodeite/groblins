package ninja.thepurple.groblins.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ninja.thepurple.groblins.common.rituals.ModRituals;
import ninja.thepurple.groblins.common.rituals.Ritual;

import javax.annotation.Nullable;
import java.util.*;

public class BlockGroblinPlasma extends BasicBlock {
    public static final PropertyEnum<BlockGroblinPlasma.EnumAttachPosition> NORTH = PropertyEnum.create("north", BlockGroblinPlasma.EnumAttachPosition.class);
    public static final PropertyEnum<BlockGroblinPlasma.EnumAttachPosition> EAST = PropertyEnum.create("east", BlockGroblinPlasma.EnumAttachPosition.class);
    public static final PropertyEnum<BlockGroblinPlasma.EnumAttachPosition> SOUTH = PropertyEnum.create("south", BlockGroblinPlasma.EnumAttachPosition.class);
    public static final PropertyEnum<BlockGroblinPlasma.EnumAttachPosition> WEST = PropertyEnum.create("west", BlockGroblinPlasma.EnumAttachPosition.class);
    public static final PropertyBool IS_RITUAL = PropertyBool.create("is_ritual");

    protected static final AxisAlignedBB[] GROBLIN_PLASMA_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D)};


    public BlockGroblinPlasma() {
        super("groblinPlasma", Material.CIRCUITS);
        this.setLightLevel(0.5f);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, BlockGroblinPlasma.EnumAttachPosition.NONE)
                .withProperty(EAST, BlockGroblinPlasma.EnumAttachPosition.NONE)
                .withProperty(SOUTH, BlockGroblinPlasma.EnumAttachPosition.NONE)
                .withProperty(WEST, BlockGroblinPlasma.EnumAttachPosition.NONE)
                .withProperty(IS_RITUAL, false));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return GROBLIN_PLASMA_AABB[getAABBIndex(state.getActualState(source, pos))];
    }

    private static int getAABBIndex(IBlockState state) {
        int i = 0;
        boolean isNorth = state.getValue(NORTH) != BlockGroblinPlasma.EnumAttachPosition.NONE;
        boolean isEast = state.getValue(EAST) != BlockGroblinPlasma.EnumAttachPosition.NONE;
        boolean isSouth = state.getValue(SOUTH) != BlockGroblinPlasma.EnumAttachPosition.NONE;
        boolean isWest = state.getValue(WEST) != BlockGroblinPlasma.EnumAttachPosition.NONE;

        if (isNorth || isSouth && !isNorth && !isEast && !isWest) {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if (isEast || isWest && !isNorth && !isEast && !isSouth) {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if (isSouth || isNorth && !isEast && !isSouth && !isWest) {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if (isWest || isEast && !isNorth && !isSouth && !isWest) {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return super.getLightValue(state);
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
        state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
        state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
        state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));

//        ArrayList<BlockPos> foundPositions = new ArrayList<BlockPos>();
//        foundPositions.add(pos);
//        this.findConnectedPlasma(worldIn, pos, foundPositions, 0);
//
//        if (foundPositions.size() > 5) {
//            state = state.withProperty(IS_RITUAL, true);
//        }

        return state;
    }

    private BlockGroblinPlasma.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
        BlockPos blockpos = pos.offset(direction);

        if (canConnectTo(worldIn.getBlockState(blockpos), direction, worldIn, blockpos)) {
            return EnumAttachPosition.SIDE;
        }

        return EnumAttachPosition.NONE;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isFullyOpaque();
    }


//    /**
//     * Calls World.notifyNeighborsOfStateChange() for all neighboring blocks, but only if the given block is a redstone
//     * wire.
//     */
//    private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos)
//    {
//        if (worldIn.getBlockState(pos).getBlock() == this)
//        {
//            worldIn.notifyNeighborsOfStateChange(pos, this);
//
//            for (EnumFacing enumfacing : EnumFacing.values())
//            {
//                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
//            }
//        }
//    }
//
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote) {
            ArrayList<BlockPos> foundPositions = new ArrayList<BlockPos>();
            foundPositions.add(pos);
            this.findConnectedPlasma(worldIn, pos, foundPositions, 0);

            if (foundPositions.size() > 16) {
                for (BlockPos p : foundPositions) {
                    worldIn.destroyBlock(p, true);
                }
            } else {
                Ritual ritual = ModRituals.matchRitual(foundPositions);

                boolean isRitual = ritual != null;

                for (BlockPos p : foundPositions) {
                    worldIn.setBlockState(p, this.blockState.getBaseState().withProperty(IS_RITUAL, isRitual));
                }
            }

//            this.updateSurroundingRedstone(worldIn, pos, state);
//
//            for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
//            {
//                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
//            }
//
//            for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
//            {
//                this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
//            }
//
//            for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL)
//            {
//                BlockPos blockpos = pos.offset(enumfacing2);
//
//                if (worldIn.getBlockState(blockpos).isNormalCube())
//                {
//                    this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
//                }
//                else
//                {
//                    this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
//                }
//            }
        }
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (state.getBlock() == this) {
            if (!this.canPlaceBlockAt(worldIn, pos)) {
                worldIn.destroyBlock(pos, true);
            }
        }
    }




    private void findConnectedPlasma(IBlockAccess worldIn, BlockPos pos, ArrayList<BlockPos> foundPositions, int recDepth) {
        if (recDepth > 100) {
            System.err.println("recDepth exceeded 100. Something went wrong!");
            return;
        }

        this.lookForPlasma(worldIn, pos, EnumFacing.WEST, foundPositions, recDepth);
        this.lookForPlasma(worldIn, pos, EnumFacing.EAST, foundPositions, recDepth);
        this.lookForPlasma(worldIn, pos, EnumFacing.NORTH, foundPositions, recDepth);
        this.lookForPlasma(worldIn, pos, EnumFacing.SOUTH, foundPositions, recDepth);

    }

    private void lookForPlasma(IBlockAccess worldIn, BlockPos pos, EnumFacing direction, ArrayList<BlockPos> foundPositions, int recDepth) {
        BlockPos blockpos = pos.offset(direction);
        if (foundPositions.contains(blockpos)) return;

        Block block = worldIn.getBlockState(blockpos).getBlock();

        if (block == ModBlocks.groblinPlasma) {
            foundPositions.add(blockpos);
            this.findConnectedPlasma(worldIn, blockpos, foundPositions, recDepth + 1);
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);


        if (!worldIn.isRemote) {
            for(EnumFacing direction : new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH}) {
                BlockPos adjPos = pos.offset(direction);

                Block block = worldIn.getBlockState(adjPos).getBlock();

                if (block == ModBlocks.groblinPlasma) {
                    ArrayList<BlockPos> foundPositions = new ArrayList<BlockPos>();
                    foundPositions.add(adjPos);
                    this.findConnectedPlasma(worldIn, adjPos, foundPositions, 0);

                    Ritual ritual = ModRituals.matchRitual(foundPositions);
                    if (ritual == null) {
                        for (BlockPos p : foundPositions) {
                            worldIn.setBlockState(p, this.blockState.getBaseState().withProperty(IS_RITUAL, false));
                        }
                    }
                }
            }

            for (EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
            }

//            for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
//            {
//                this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
//            }

//            for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL)
//            {
//                BlockPos blockpos = pos.offset(enumfacing2);
//
//                if (worldIn.getBlockState(blockpos).isNormalCube())
//                {
//                    this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
//                }
//                else
//                {
//                    this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
//                }
//            }
        }
    }


    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }


    protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos) {
        Block block = blockState.getBlock();

        return (block == ModBlocks.groblinPlasma);
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int meta) {
        float f = (float) meta / 15.0F;
        float f1 = f * 0.6F + 0.4F;

        if (meta == 0) {
            f1 = 0.3F;
        }

        float f2 = f * f * 0.7F - 0.5F;
        float f3 = f * f * 0.6F - 0.7F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        int i = MathHelper.clamp_int((int) (f1 * 255.0F), 0, 255);
        int j = MathHelper.clamp_int((int) (f2 * 255.0F), 0, 255);
        int k = MathHelper.clamp_int((int) (f3 * 255.0F), 0, 255);
        return -16777216 | i << 16 | j << 8 | k;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(IS_RITUAL)) {
            double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double) ((float) pos.getY() + 0.0625F);
            double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;

            float f1 =  0.6F + 0.4F;
            float f2 = Math.max(0.0F, 0.7F - 0.5F);
            float f3 = Math.max(0.0F, 0.6F - 0.7F);
            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, (double) f1, (double) f2, (double) f3, new int[0]);
        }
    }


    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.getValue(IS_RITUAL)) {
            return 15;
        }

        return 7;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Items.REDSTONE);
    }


    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(IS_RITUAL, (meta & 1) > 0);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(IS_RITUAL) ? 1 : 0;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, IS_RITUAL});
    }

    static enum EnumAttachPosition implements IStringSerializable {
        SIDE("side"),
        NONE("none");

        private final String name;

        private EnumAttachPosition(String name) {
            this.name = name;
        }

        public String toString() {
            return this.getName();
        }

        public String getName() {
            return this.name;
        }
    }
}

