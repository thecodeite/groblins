package ninja.thepurple.groblins.common.entity.groblin.tasks;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.activities.GroblinActivity;
import ninja.thepurple.groblins.common.entity.groblin.helpers.WorldInteractionHelper;

import java.util.ArrayList;

public class GroblinTaskFixTerrain extends GroblinTask {
    private final ArrayList<BlockPos> targetsToRemove = new ArrayList<>();
    private final ArrayList<BlockPos> targetsToAdd = new ArrayList<>();
    private GroblinActivity currentActivity;
    private boolean terrainFlatWhenDone;

    public GroblinTaskFixTerrain(EntityGroblin groblin) {
        super(groblin);
    }

    @Override
    public void prepareTask() {
        if(!groblin.hasHomeChunk || this.groblin.yLevel < 0) {
            return;
        }

        int top = 0, bottom = 256;
        int[] heightMap = groblin.getHomeChunk().getHeightMap();
        for(int y : heightMap) {
            if (y > top) top = y;
            if (y < bottom) bottom = y;
        }

        terrainFlatWhenDone = false;
        while(top > this.groblin.yLevel) {
            for (int i = 0; i < heightMap.length; i++) {
                int y = heightMap[i];
                if (y == top) {
                    targetsToRemove.add(new BlockPos(groblin.getHomeX() + (i%16), y-1, groblin.getHomeZ() + (i>>4)));
                    if(targetsToRemove.size() > 10) {
                        System.out.println("I have 10 blocks to remove");
                        return;
                    }
                }
            }
            top--;
        }

        while(bottom < this.groblin.yLevel) {
            for (int i = 0; i < heightMap.length; i++) {
                int y = heightMap[i];
                if (y == bottom) {
                    targetsToAdd.add(new BlockPos(groblin.getHomeX() + (i%16), y, groblin.getHomeZ() + (i>>4)));
                    if(targetsToAdd.size() + targetsToRemove.size() > 10) {
                        System.out.println("I have "+ targetsToRemove.size() +" blocks to remove and "+targetsToAdd.size()+" blocks to add");
                        return;
                    }
                }
            }
            bottom++;
        }
        System.out.println("I have "+ targetsToRemove.size() +" blocks to remove and "+targetsToAdd.size()+" blocks to add and the terrain will be flat");
        terrainFlatWhenDone = true;
    }

    @Override
    public boolean taskIsComplete() {
        System.out.println("I' working on "+currentActivity+" and have "+ targetsToRemove.size() +" blocks to remove and "+targetsToAdd.size()+" blocks to add and the terrain will be flat");
       return currentActivity == null && targetsToRemove.size() <= 0 && targetsToAdd.size() <= 0;
    }

    @Override
    public void workOnTask() {
        if (currentActivity != null) {
            GroblinActivity.ActivityResult result = currentActivity.update();
            switch (result) {
                case DOING: return;
                case DONE:
                    currentActivity = null;
                    break;
                case CANT_DO:
                    System.out.println("Can't complete activity " + currentActivity);
                    currentActivity = null;
                    return;
            }
        }

        if (targetsToRemove.size() > 0) {
            BlockPos blockToRemove = targetsToRemove.remove(0);
            ItemStack tool = WorldInteractionHelper.pickTool(groblin.worldObj.getBlockState(blockToRemove), groblin.getGroblinInventory());
            currentActivity = groblin.breakBlock(blockToRemove, tool);
        } else if (targetsToAdd.size() > 0) {
            currentActivity = groblin.placeBlock(targetsToAdd.remove(0), Blocks.DIRT.getDefaultState());
        } else {
            System.out.println("No more blocks to add or remove");
            if (terrainFlatWhenDone) {
                System.out.println("I'm now happy with my chunk terrain");
                groblin.setTerrainMap(groblin.getHomeChunk().getHeightMap());
            } else {
                System.out.println("My chunk terrain needs more work");
            }

        }
    }
}
