package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class FlatSpaceFinder {
    public static boolean isFlatSquare(Chunk chunk, BlockPos pos, int size) {
        if (chunk == null || pos == null) return false;

        int x = pos.getX() - chunk.xPosition;
        int z = pos.getZ() - chunk.zPosition;

        if(x + size > 15 || z + size > 15) return false;
        if(x < 0 || z < 0) return false;

        System.out.println("pos="+pos+", x="+x+" z="+z);
        int y = chunk.getHeightValue(x, z);

        for(int i=1; i< size * size; i++) {
            int cx = i/size;
            int cz = i%size;
            System.out.println("size="+size+", i="+i+", cx="+cx+" cz="+cz);
            if (chunk.getHeightValue(x + cx, z + cz) != y) return false;
        }

        return true;
    }
}
