package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class Ritual {
    protected RitualGrid[] grids;

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
    }

    public abstract void activateRitual();

    public RitualGrid isRitualMatch(World worldIn, ArrayList<BlockPos> positions) {
        String ritualGrid = positionsToGrid(positions);

        for(int i=0; i<4; i++) {
            System.out.println("ritualGrid = »" + ritualGrid + "«");
            System.out.println("gridForMatching = »" + this.grids[i].matching + "«");
            if (this.grids[i].matching.equals(ritualGrid)) {
                return this.grids[i];
            }
        }

        return null;
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
}
