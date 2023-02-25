package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinMakeWoodenToolsTask;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTask;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskFixTerrain;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskGetResourceFromRitual;
import ninja.thepurple.groblins.common.rituals.ModRituals;

public class EntityAIAssessTerrain extends EntityAIBase {
    private EntityGroblin groblin;
    private GroblinTask activeTask;
    public EntityAIAssessTerrain(EntityGroblin groblin) {
        this.groblin = groblin;
    }

    @Override
    public boolean shouldExecute() {
        if (activeTask != null) {
            if (activeTask.taskIsComplete()) {
                activeTask = null;
            } else {
                return false;
            }
        }

        if (!groblin.hasHomeChunk || groblin.yLevel <= 0 || groblin.getTerrainMap() == null) {
            return true;
        }

        return false;
    }

    public void startExecuting() {
        if (activeTask != null) {
            return;
        }

        BlockPos blockpos = this.groblin.getPosition();
        Chunk chunk;

        if (!groblin.hasHomeChunk) {
            groblin.hasHomeChunk = true;
            chunk = this.groblin.worldObj.getChunkFromBlockCoords(blockpos);
            groblin.homeChunkX = chunk.xPosition;
            groblin.homeChunkZ = chunk.zPosition;

            groblin.say("I am a groblin " + this.groblin.getCustomNameTag() + " and I'm making my home in the chunk at " +
                    this.groblin.homeChunkX + ", " + this.groblin.homeChunkZ);
        }

        if (groblin.yLevel < 0) {
            chunk = this.groblin.getHomeChunk();
            int[] heightMap = chunk.getHeightMap();

            int sum = 0;
            for (int y : heightMap) {
                sum += y;
            }

            groblin.yLevel = sum >> 8;
            System.out.println("sum = " + sum);
            System.out.println("sum/ = " + sum / 256D);
            System.out.println("sum %= " + sum % 256);
            groblin.say("I like the look of y level " + groblin.yLevel);
            int blocksNeededToBuildUp = 256 - (sum % 256);
            if (blocksNeededToBuildUp < 16) {
                groblin.yLevel++;
                groblin.say("But actually, I only need to find " + blocksNeededToBuildUp + " blocks to live on " + groblin.yLevel);
            }
        }

        if (groblin.getBestToolLevel("all") < 1) {
            groblin.say("I want to start mining but I need some tools first" );
            activeTask = new GroblinMakeWoodenToolsTask(groblin);
            groblin.objectiveTasks.add(activeTask);
            return;
        }

        if (groblin.getTerrainMap() == null && groblin.<GroblinTaskFixTerrain>getTaskType() == null) {
            for (int y : groblin.getHomeChunk().getHeightMap()) {
                if (y != this.groblin.yLevel) {
                    activeTask = new GroblinTaskFixTerrain(groblin);
                    groblin.objectiveTasks.add(activeTask);
                    return;
                }
            }
        }
    }

//    @Override
//    public boolean continueExecuting() {
//
//        if (this.groblin.yLevel < 0) {
//            if (lookingAtRow < 16) {
//                for(int i=0; i<16; i++) {
//                    int x = lookingAtRow;
//                    int z = i;
//                    //yLevelsAggregate += this.groblin.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
//                    yLevelsAggregate += this.groblin.homeChunk.getHeightValue(x, z);
//                }
//                lookingAtRow++;
//                return true;
//            } else {
//
//                this.groblin.yLevel = yLevelsAggregate / 256;
//                if (yLevelsAggregate % 256 > 250) {
//                    this.groblin.yLevel++;
//                    int dirtNeeded = (yLevelsAggregate % 256) - 250;
//                    //this.groblin.addNeeds(new ItemStack(Blocks.DIRT, (yLevelsAggregate % 256) - 250 ));
//                    for (int i = 0; i < dirtNeeded; i++) {
//                        this.groblin.addObjectiveTask(new GroblinTaskGetResourceFromRitual(this.groblin, ModRituals.ritualCreateDirt));
//                    }
//
//                }
//                this.groblin.say("I am a groblin " + this.groblin.name + " and I live on level " + this.groblin.yLevel);
//                return false;
//            }
//        }
//        return false;
//    }
}
