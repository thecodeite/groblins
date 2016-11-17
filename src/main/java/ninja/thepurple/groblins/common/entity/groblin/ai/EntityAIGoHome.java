package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.activities.ActivityWalkTo;

public class EntityAIGoHome extends EntityAIBase {
    private EntityGroblin groblin;
    private ActivityWalkTo activityWalkTo;

    public EntityAIGoHome(EntityGroblin groblin) {
        this.groblin = groblin;
        this.setMutexBits(8);
    }

    @Override
    public boolean shouldExecute() {
        if(!groblin.getNavigator().noPath()) return false;
        Chunk currentChunk = groblin.worldObj.getChunkFromBlockCoords(groblin.getPosition());
        return currentChunk != groblin.getHomeChunk();
    }

    public void startExecuting() {
        groblin.say("I'm a long way from home");

        int y = groblin.getHomeChunk().getHeightValue(0, 0);
        BlockPos homePos = new BlockPos(groblin.getHomeX()+8, y, groblin.getHomeZ()+8);
        activityWalkTo = new ActivityWalkTo(groblin, homePos);
    }

    @Override
    public boolean continueExecuting() {
        switch (activityWalkTo.update()){
            case DOING: return true;
            case DONE:
            case CANT_DO:
            default: return false;
        }
    }
}
