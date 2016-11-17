package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class Ritual {
    protected RitualGrid[] grids;
    public final int size;

    public Ritual (String grid) {
        /*
          e.g. ' # ;#X#;o#o' = |   #   |
                               | # X # |
                               | o # o |

         '#' = plasma
         'X' = key plasma
         'o' = clear surface
        */

        grids = RitualGrid.rotations(grid);

        String[] bits = grid.split(";");
        int width = bits[0].length();
        int height = bits.length;
        size = width > height ? width : height;
    }

    public abstract boolean eventSiteMustBeEmpty();

    public abstract boolean activateRitual(ValidRitual validRitual, @Nullable EntityPlayer playerIn, World world);

    public ValidRitual isRitualMatch(World world, ArrayList<BlockPos> positions) {
        String ritualGrid = positionsToGrid(positions);

        for(int i=0; i<4; i++) {
            if (this.grids[i].matching.equals(ritualGrid)) {
                BlockPos keyPos = this.findKeyPos(this.grids[i], positions);
                BlockPos[] eventPositions = this.getEventPositions(this.grids[i], positions);
                boolean eventPositionsClear = true;

                if(this.eventSiteMustBeEmpty()) {
                    for(BlockPos eventPos : eventPositions) {
                        if (!world.isAirBlock(eventPos)) {
                            IBlockState blockState = world.getBlockState(eventPos);
                            if (blockState.getMaterial().isSolid() && !blockState.getBlock().isReplaceable(world, eventPos)) {
                                eventPositionsClear = false;
                            }
                        }
                    }
                }

                if (eventPositionsClear) {
                    return new ValidRitual(this, keyPos, eventPositions);
                }
            }
        }

        return null;
    }

    private BlockPos findKeyPos(RitualGrid grid, ArrayList<BlockPos> positions) {
        int minX = positions.stream().mapToInt(Vec3i::getX).min().getAsInt();
        int minZ = positions.stream().mapToInt(Vec3i::getZ).min().getAsInt();

        int keyOffsetX = grid.keyOffset.x + minX;
        int keyOffsetZ = grid.keyOffset.z + minZ;

        for (BlockPos pos : positions) {
            if (pos.getX() == keyOffsetX && pos.getZ() == keyOffsetZ) {
                return pos;
            }
        }

        System.err.println("KeyPos not found");
        return positions.get(0);
    }

    private BlockPos[] getEventPositions(RitualGrid grid, ArrayList<BlockPos> positions) {
        int minX = positions.stream().mapToInt(Vec3i::getX).min().getAsInt();
        int minY = positions.stream().mapToInt(Vec3i::getY).min().getAsInt();
        int minZ = positions.stream().mapToInt(Vec3i::getZ).min().getAsInt();

        ArrayList<BlockPos> results = new ArrayList<>();
        for(RitualGrid.Offset offset : grid.eventOffsets) {
            int eventOffsetX = offset.x + minX;
            int eventOffsetY = offset.y + minY;
            int eventOffsetZ = offset.z + minZ;

            results.add(new BlockPos(eventOffsetX, eventOffsetY, eventOffsetZ));
        }


        return results.toArray(new BlockPos[results.size()]);
    }


    private String positionsToGrid(ArrayList<BlockPos> positions) {
        int posCount = positions.size();

        int minX = positions.stream().mapToInt(Vec3i::getX).min().getAsInt();
        int minZ = positions.stream().mapToInt(Vec3i::getZ).min().getAsInt();
        int maxX = positions.stream().mapToInt(Vec3i::getX).max().getAsInt();
        int maxZ = positions.stream().mapToInt(Vec3i::getZ).max().getAsInt();
        int sizeX = 1+maxX-minX;
        int sizeZ = 1+maxZ-minZ;

        int[] xOff = positions.stream().mapToInt(Vec3i::getX).map(p -> p - minX).toArray();
        int[] zOff = positions.stream().mapToInt(Vec3i::getZ).map(p -> p - minZ).toArray();

        char[] arrayGrid = new char[(sizeX * sizeZ) + (sizeZ - 1)];
        java.util.Arrays.fill(arrayGrid, ' ');

        for(int i=sizeX; i<arrayGrid.length; i+=(sizeX+1)) {
            arrayGrid[i] = ';';
        }

        for(int i=0; i<posCount; i++) {
            int off = xOff[i] + (zOff[i] * (sizeX + 1));
            arrayGrid[off] = '#';
        }

        return new String(arrayGrid);
    }

    public RitualGrid[] getGrids() {
        return grids;
    }
}

