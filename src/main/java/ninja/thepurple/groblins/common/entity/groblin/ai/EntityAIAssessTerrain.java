package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskGetResourceFromRitual;
import ninja.thepurple.groblins.common.rituals.ModRituals;

public class EntityAIAssessTerrain extends EntityAIBase {
    private EntityGroblin groblin;
    private int yLevelsAggregate;
    private int lookingAtRow = 0;

    public EntityAIAssessTerrain(EntityGroblin groblin) {
        this.groblin = groblin;
    }

    @Override
    public boolean shouldExecute() {
        return groblin.homeChunk == null || this.groblin.yLevel <= 0;
    }

    public void startExecuting() {
        BlockPos blockpos = this.groblin.getPosition();

        if (this.groblin.homeChunk == null) {
            this.groblin.homeChunk =  this.groblin.worldObj.getChunkFromBlockCoords(blockpos);
            this.groblin.yLevel = -1;
            this.groblin.say("I am a groblin " + this.groblin.name + " and I'm making my home in the chunk at " +
                    this.groblin.homeChunk.xPosition + ", "+this.groblin.homeChunk.zPosition);
        }
    }

    @Override
    public boolean continueExecuting() {
        if (this.groblin.yLevel < 0) {
            if (lookingAtRow < 16) {
                for(int i=0; i<16; i++) {
                    int x = lookingAtRow;
                    int z = i;
                    //yLevelsAggregate += this.groblin.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
                    yLevelsAggregate += this.groblin.homeChunk.getHeightValue(x, z);
                }
                lookingAtRow++;
                return true;
            } else {

                this.groblin.yLevel = yLevelsAggregate / 256;
                if (yLevelsAggregate % 256 > 250) {
                    this.groblin.yLevel++;
                    int dirtNeeded = (yLevelsAggregate % 256) - 250;
                    //this.groblin.addNeeds(new ItemStack(Blocks.DIRT, (yLevelsAggregate % 256) - 250 ));
                    for (int i = 0; i < dirtNeeded; i++) {
                        this.groblin.addObjectiveTask(new GroblinTaskGetResourceFromRitual(this.groblin, ModRituals.ritualCreateDirt));
                    }

                }
                this.groblin.say("I am a groblin " + this.groblin.name + " and I live on level " + this.groblin.yLevel);
                return false;
            }
        }
        return false;
    }
}
