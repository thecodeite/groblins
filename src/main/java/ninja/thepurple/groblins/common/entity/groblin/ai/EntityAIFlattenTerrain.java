//package ninja.thepurple.groblins.common.entity.groblin.ai;
//
//import net.minecraft.entity.ai.EntityAIBase;
//import net.minecraft.init.Blocks;
//import net.minecraft.pathfinding.Path;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vec3d;
//import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
//import ninja.thepurple.groblins.common.entity.groblin.activities.GroblinActivity;
//import ninja.thepurple.groblins.common.entity.groblin.helpers.StandingPositionHelper;
//
//import java.util.ArrayList;
//
//public class EntityAIFlattenTerrain extends EntityAIBase {
//    private EntityGroblin groblin;
//    private final ArrayList<BlockPos> targetsToRemove = new ArrayList<>();
//    private final ArrayList<BlockPos> targetsToAdd = new ArrayList<>();
//    private GroblinActivity currentActivity;
//
//    public EntityAIFlattenTerrain(EntityGroblin groblin) {
//        this.setMutexBits(8);
//        this.groblin = groblin;
//    }
//
//    @Override
//    public boolean shouldExecute() {
//        if(!groblin.hasHomeChunk || this.groblin.yLevel < 0) {
//            return false;
//        }
//
//        for(int y : groblin.getHomeChunk().getHeightMap()) {
//            if (y != this.groblin.yLevel) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public void startExecuting() {
//        int top = 0, bottom = 256;
//        int[] heightMap = groblin.getHomeChunk().getHeightMap();
//        for(int y : heightMap) {
//            if (y > top) top = y;
//            if (y < bottom) bottom = y;
//        }
//
//        resetTask();
//
//        while(top > this.groblin.yLevel) {
//            for (int i = 0; i < heightMap.length; i++) {
//                int y = heightMap[i];
//                if (y == top) {
//                    targetsToRemove.add(new BlockPos(groblin.getHomeX() + (i%16), y-1, groblin.getHomeZ() + (i>>4)));
//                    if(targetsToRemove.size() > 10) break;
//                }
//            }
//            top--;
//        }
//
//        if(targetsToRemove.size() < 10) {
//            while(bottom < this.groblin.yLevel) {
//                for (int i = 0; i < heightMap.length; i++) {
//                    int y = heightMap[i];
//                    if (y == bottom) {
//                        targetsToAdd.add(new BlockPos(groblin.getHomeX() + (i%16), y, groblin.getHomeZ() + (i>>4)));
//                        if(targetsToAdd.size() + targetsToRemove.size() > 10) break;
//                    }
//                }
//                bottom++;
//            }
//
//        }
//    }
//
//    @Override
//    public void resetTask() {
//        targetsToRemove.clear();
//        targetsToAdd.clear();
//        currentActivity = null;
//    }
//
//    @Override
//    public boolean continueExecuting() {
//        if (currentActivity != null) {
//            GroblinActivity.ActivityResult result = currentActivity.update();
//            switch (result) {
//                case DOING: return true;
//                case DONE:
//                    currentActivity = null;
//                    break;
//                case CANT_DO:
//                    System.out.println("Can't complete activity " + currentActivity);
//                    currentActivity = null;
//                    return false;
//            }
//        }
//
//        if (targetsToRemove.size() > 0) {
//            currentActivity = groblin.breakBlock(targetsToRemove.remove(0));
//            return true;
//        } else if (targetsToAdd.size() > 0) {
//            currentActivity = groblin.placeBlock(targetsToAdd.remove(0), Blocks.DIRT.getDefaultState());
//            return true;
//        } else {
//            System.out.println("No more blocks to add or remove");
//            return false;
//        }
//    }
//}
