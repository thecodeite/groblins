package ninja.thepurple.groblins.common.entity.groblin.tasks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.common.blocks.BlockGroblinPlasma;
import ninja.thepurple.groblins.common.blocks.ModBlocks;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.ExpandingChunkSearch;
import ninja.thepurple.groblins.common.entity.groblin.helpers.FlatSpaceFinder;
import ninja.thepurple.groblins.common.rituals.SummonBlockRitual;

import javax.annotation.Nullable;

import java.util.ArrayList;

import static ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskGetResourceFromRitual.TaskState.FINDING_SPACE;

public class GroblinTaskGetResourceFromRitual extends GroblinTask {
    private final SummonBlockRitual ritual;
    private BlockPos ritualPosition, ritualDrawingPosition, ritualActivationBlockPosition;
    private final ArrayList<BlockPos> ritualHarvestLocations = new ArrayList<>();
    private int ritualProgress = -1;
    private char[] ritualInstructions;
    private int rest = 0;

    enum TaskState {
        FINDING_SPACE, MOVING_TO_SPACE, DRAWING_RITUAL, ACTIVATE_RITUAL, HARVEST_PRODUCT, FINISHED
    }
    private TaskState state;

    public GroblinTaskGetResourceFromRitual(EntityGroblin groblin, SummonBlockRitual ritual) {
        super(groblin);
        this.ritual = ritual;
        ritualInstructions = ritual.getGrids()[0].full.toCharArray();
        state = FINDING_SPACE;
    }

    @Override
    public String toString() {
        return "Get " + ritual.blockToSummon + " from ritual";
    }

    @Override
    public boolean workOnTask() {
        if (rest > 0) {
            rest--;
            System.err.println("Resting for " + rest);
            return false;
        }

        switch (this.state) {
            case FINDING_SPACE: findSpace(groblin); return false;
            case MOVING_TO_SPACE: movingToSpace(groblin); return false;
            case DRAWING_RITUAL: drawingRitual(groblin); return false;
            case ACTIVATE_RITUAL: activateRitual(groblin); return false;
            case HARVEST_PRODUCT: harvestProduct(groblin); return false;
            default:
            case FINISHED: return true;
        }
    }

    private void findSpace(EntityGroblin groblin) {
        ritualPosition = spaceSearch(groblin);
        if (ritualPosition == null) {
            System.err.println("Groblin could not find a space to perform ritual. Going to sleep.");
            groblin.wakeAt = groblin.worldObj.getWorldTime() + (20*30);
            this.state = TaskState.FINISHED;
        } else {
            double x = ritualPosition.getX() + 1.5D;
            double y = ritualPosition.getY();
            double z = ritualPosition.getZ() + 1.5D;
            groblin.getNavigator().tryMoveToXYZ(x, y, z, 0.5D);
            this.state = TaskState.MOVING_TO_SPACE;
        }
    }

    private void movingToSpace(EntityGroblin groblin) {
        if(groblin.getNavigator().noPath()) {
            System.out.println("Standing at ritual site");
            System.out.println("Thinking about the ritual: " + new String(ritualInstructions));
            ritualProgress = -1;
            rest = 10;
            this.state = TaskState.DRAWING_RITUAL;
        }
    }

    private void drawingRitual(EntityGroblin groblin) {
        if (ritualProgress < 0) {

            ritualProgress = 0;
            ritualActivationBlockPosition = null;
            ritualHarvestLocations.clear();
            ritualDrawingPosition = ritualPosition;
            System.out.println("Started drawing ritual");
        } else if (ritualProgress >= ritualInstructions.length) {
            this.state = TaskState.ACTIVATE_RITUAL;
            return;
        }

        System.out.println("Thinking about a " + ritualInstructions[ritualProgress] + " in the ritual (progress=" +ritualProgress+")");

        switch (ritualInstructions[ritualProgress]) {
            case 'X':
                ritualActivationBlockPosition = ritualDrawingPosition;
            case '#':
                System.out.println("Trying to place some plasma at " + ritualDrawingPosition);
                lookAtBlock(groblin, ritualDrawingPosition);
                if(!tryPlacePlasma(groblin.worldObj, ritualDrawingPosition)) {
                    System.out.println("Ritual site must have changed. Start looking again.");
                    this.state = TaskState.FINDING_SPACE;
                } else {
                    System.out.println("I placed some plasma at "+ ritualDrawingPosition);
                    ritualDrawingPosition = ritualDrawingPosition.east();
                    rest = 20;
                }
                break;
            case ';':
                ritualDrawingPosition = new BlockPos(
                        ritualPosition.getX(),
                        ritualPosition.getY(),
                        ritualDrawingPosition.getZ()+1
                );
                rest = 10;
                break;
            case 'o':
                ritualHarvestLocations.add(ritualDrawingPosition);
                ritualDrawingPosition = ritualDrawingPosition.east();
                rest = 10;
                break;
            default:
                ritualDrawingPosition = ritualDrawingPosition.east();
                rest = 10;
                break;
        }
        ritualProgress++;

    }

    private void activateRitual(EntityGroblin groblin) {
        if (ritualActivationBlockPosition != null) {
            IBlockState blockState = groblin.worldObj.getBlockState(ritualActivationBlockPosition);

            Block block = blockState.getBlock();
            if(block instanceof BlockGroblinPlasma) {
                BlockGroblinPlasma plasma = (BlockGroblinPlasma) block;
                boolean success = plasma.onBlockActivated(groblin.worldObj, ritualActivationBlockPosition, blockState, null, EnumHand.MAIN_HAND, null, EnumFacing.UP, 0, 0, 0);

                if (!success) {
                    System.err.println("Could not activate ritual");
                    System.out.println("Ritual site must have changed. Start looking again.");
                    this.state = TaskState.FINDING_SPACE;
                } else {
                    System.out.println("Ritule was a success, moving to harvest");
                    this.state = TaskState.HARVEST_PRODUCT;
                }
            }
        }
    }

    private void harvestProduct(EntityGroblin groblin) {
        if(ritualHarvestLocations.size() == 0) {
            System.out.println("No more items to harvest, task complete");
            this.state = TaskState.FINISHED;
        } else if(groblin.getNavigator().noPath()) {
            System.out.println("Going to harvest a block");
            BlockPos harvestPos = this.ritualHarvestLocations.get(0);
            boolean isSolid = groblin.worldObj.getBlockState(harvestPos).getMaterial().isSolid();
            if(isSolid) harvestPos = harvestPos.up();
            if (this.groblin.getDistanceSqToCenter(harvestPos) > 1.0D) {
                double x = harvestPos.getX() + 0.5D;
                double y = harvestPos.getY();
                double z = harvestPos.getZ() + 0.5D;
                groblin.getNavigator().tryMoveToXYZ(x, y, z, 0.5D);
            } else {
                System.out.println("I'm here, harvesting");
                groblin.worldObj.destroyBlock(this.ritualHarvestLocations.get(0), true);
                this.ritualHarvestLocations.remove(0);
            }
        }
    }

    private boolean tryPlacePlasma(World world, BlockPos pos)
    {
        IBlockState blockState = world.getBlockState(pos);
        Material material = blockState.getMaterial();
        boolean isSolid = material.isSolid();
        boolean isNotReplaceable = !blockState.getBlock().isReplaceable(world, pos);

        if (!world.isAirBlock(pos) && isSolid && isNotReplaceable)
        {
            System.err.println("Can't place plasma at " + pos);
            if(!world.isAirBlock(pos)) {
                System.err.println("Not an air block" + blockState);
            } else if (isSolid) {
                System.err.println("Block is solid" + material);
            } else if (isNotReplaceable) {
                System.err.println("Block is no replacable" + blockState.getBlock());
            }
            return false;
        }
        else
        {
            if (!world.isRemote && !(isSolid && isNotReplaceable) && !material.isLiquid())
            {
                world.destroyBlock(pos, true);
            }

            world.setBlockState(pos, ModBlocks.groblinPlasma.getDefaultState(), 11);

            return true;
        }
    }

    private void lookAtBlock(EntityGroblin groblin, BlockPos pos) {
        groblin.getLookHelper().setLookPosition(
                pos.getX() + 0.5D,
                pos.getY(),
                pos.getZ() + 0.5D,
                80,
                80
        );
    }

    private void sleep(EntityGroblin groblin, String realTask) {
        System.err.println("Groblin should "+realTask+" but going to sleep instead.");
        groblin.wakeAt = groblin.worldObj.getWorldTime() + (20*30);
        this.state = TaskState.FINISHED;
    }

    private BlockPos spaceSearch(EntityGroblin groblin) {
        System.out.println("I'm standing at  = " + groblin.getPosition());
        ExpandingChunkSearch positions = new ExpandingChunkSearch(groblin.getPosition(), groblin.getHomeChunk());
        for(BlockPos pos : positions) {
            System.out.println("Checking  = " + pos);
            if(pos == null) return null;
            if(FlatSpaceFinder.isFlatSquare(groblin.getHomeChunk(), pos, ritual.size)) {
                System.out.println("Found a good spot at = " + pos);
                return pos;
            }
        }

        return null;
    }
}
