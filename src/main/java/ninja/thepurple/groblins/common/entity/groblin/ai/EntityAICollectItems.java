package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(-3D, -1D, -3D, 3D, 2D, 3D));

public class EntityAICollectItems extends EntityAIBase {
    private EntityGroblin groblin;

    //final AxisAlignedBB searchRadius = new AxisAlignedBB(3D, 3D, 3D, -3D, -1D, -3D);

    public EntityAICollectItems(EntityGroblin groblin) {
        this.groblin = groblin;
        setMutexBits(8);
    }

    @Override
    public boolean shouldExecute() {
        if (!groblin.hasHomeChunk) return false;

        List<EntityItem> items = groblin.worldObj.getEntitiesWithinAABB(EntityItem.class, groblin.getHomeChunkAABB());
        //System.out.println("items = " + items.size());
        //for (EntityItem item : items) {
            //System.out.println("item = " + item);
        //}
        return items.size() > 0;
    }

    public void startExecuting() {
        groblin.say("I'm picking up some stuff");
    }

    @Override
    public boolean continueExecuting() {
        List<EntityItem> items = groblin.worldObj.getEntitiesWithinAABB(EntityItem.class, groblin.getHomeChunkAABB());
        if (items.size() == 0) {
            return false;
        }

        if (!groblin.getNavigator().noPath()) {
            return true;
        }

        EntityItem closestItem = null;
        double distance = 9999999;

        for (EntityItem item : items) {
            double thisDist = groblin.getDistanceSqToEntity(item);
            if (thisDist < distance) {
                distance = thisDist;
                closestItem = item;
            }
        }

        BlockPos pos = closestItem.getPosition();
        groblin.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), 0.5D);
        return true;
    }
}
