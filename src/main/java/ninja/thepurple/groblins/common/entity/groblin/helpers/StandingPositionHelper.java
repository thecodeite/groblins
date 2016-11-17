package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class StandingPositionHelper {
    private static Vec3i[] offsets = new Vec3i[]{
            new Vec3i(0, 0, 1), // 0
            new Vec3i(1, 0, 1), // 1
            new Vec3i(1, 0, 0), // 2
            new Vec3i(1, 0, -1), // 3
            new Vec3i(0, 0, -1), // 4
            new Vec3i(-1, 0, -1), // 5
            new Vec3i(-1, 0, 0), // 6
            new Vec3i(-1, 0, 1), // 7
    };

    public static Vec3d pickSpotToAddBlockFrom(BlockPos posToAddBlock, Vec3d currentPosition, World world) {
        double xDelta = posToAddBlock.getX() - currentPosition.xCoord;
        double zDelta = posToAddBlock.getZ() - currentPosition.zCoord;

        int idx;
        if (Math.abs(xDelta) > Math.abs(zDelta)) {
            idx = xDelta < 0 ? 2 : 6;
        } else {
            idx = zDelta < 0 ? 0 : 4;
        }

        BlockPos standAt;
        for (int i=0; i<4; i++) {
            standAt = posToAddBlock.add(offsets[(idx+i) % 8]);
            if (canStandAt(standAt, world)) return middleOfBlock(standAt);
            if (canStandAt(standAt.down(), world)) return middleOfBlock(standAt.down());
            if (canStandAt(standAt.down(2), world)) return middleOfBlock(standAt.down(2));
            if (canStandAt(standAt.up(), world)) return middleOfBlock(standAt.up());
            if (canStandAt(standAt.down(3), world)) return middleOfBlock(standAt.down(3));
            if (canStandAt(standAt.down(4), world)) return middleOfBlock(standAt.down(4));
            if (canStandAt(standAt.up(2), world)) return middleOfBlock(standAt.up(2));

            if (i%3 != 0) {
                standAt = posToAddBlock.add(offsets[(idx-i+8) % 8]);
                if (canStandAt(standAt, world)) return middleOfBlock(standAt);
                if (canStandAt(standAt.down(), world)) return middleOfBlock(standAt.down());
                if (canStandAt(standAt.down(2), world)) return middleOfBlock(standAt.down(2));
                if (canStandAt(standAt.up(), world)) return middleOfBlock(standAt.up());
                if (canStandAt(standAt.down(3), world)) return middleOfBlock(standAt.down(3));
                if (canStandAt(standAt.down(4), world)) return middleOfBlock(standAt.down(4));
                if (canStandAt(standAt.up(2), world)) return middleOfBlock(standAt.up(2));
            }
        }

        return null;
    }

    private static Vec3d middleOfBlock(BlockPos standAt) {
        return new Vec3d(standAt.getX() + 0.5d, standAt.getY(), standAt.getZ()+0.5D);
    }

    private static boolean canStandAt(BlockPos pos, World world) {

        boolean isAir = world.isAirBlock(pos);
        boolean isAirUp = world.isAirBlock(pos.up());
        boolean solidBase = world.isSideSolid(pos.down(), EnumFacing.UP);

        System.out.println(pos + " isAir=" + isAir + " isAirUp=" + isAirUp + " solidBase=" + solidBase);
        return isAir && isAirUp && solidBase;
    }
}
