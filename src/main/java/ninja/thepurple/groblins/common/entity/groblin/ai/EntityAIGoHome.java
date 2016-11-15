package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public class EntityAIGoHome extends EntityAIBase {
    private EntityGroblin groblin;
    private int workFor;
    public EntityAIGoHome(EntityGroblin groblin) {
        this.groblin = groblin;
    }

    @Override
    public boolean shouldExecute() {
        if (workFor > 0) {
            workFor--;
            return true;
        }
        Chunk currentChunk = groblin.worldObj.getChunkFromBlockCoords(groblin.getPosition());
        return currentChunk != groblin.homeChunk;
    }

    public void startExecuting() {
        groblin.say("I'm a long way from home");
    }

    @Override
    public boolean continueExecuting() {
        if (workFor > 0) {
            workFor--;
            return false;
        }

        Chunk currentChunk = groblin.worldObj.getChunkFromBlockCoords(groblin.getPosition());
        if(currentChunk != groblin.homeChunk) {
            int y = groblin.homeChunk.getHeightValue(0, 0);
            BlockPos homePos = new BlockPos(groblin.homeChunk.xPosition, y, groblin.homeChunk.zPosition);
            this.groblin.moveToBlockPosAndAngles(homePos, 0 ,0);
            workFor = 20;
            return false;
        } else {
            return true;
        }
    }
}
