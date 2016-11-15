package ninja.thepurple.groblins.common.entity.groblin;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.entity.groblin.ai.EntityAIAssessTerrain;
import ninja.thepurple.groblins.common.entity.groblin.ai.EntityAIDazed;
import ninja.thepurple.groblins.common.entity.groblin.ai.EntityAIGoHome;
import ninja.thepurple.groblins.common.entity.groblin.ai.EntityAIPerformObjectiveTask;
import ninja.thepurple.groblins.common.entity.groblin.helpers.GroblinNameGenerator;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTask;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class EntityGroblin extends EntityCreature  {
    public static final ResourceLocation LOOT = new ResourceLocation(GroblinsMod.MODID, "entity/groblin");
    public final String name;

    public Chunk homeChunk = null;
    public int yLevel = -1;
    public final Queue<GroblinTask> objectiveTasks = new ArrayDeque<>();
    public long wakeAt;


    public EntityGroblin(World worldIn) {
        super(worldIn);
        this.name = GroblinNameGenerator.generateName();
        this.say("I am a groblin " + name + " and I am alive!");
        this.setHealth(1);
        this.wakeAt = worldIn.getWorldTime() + 100;
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIDazed(this));
        this.tasks.addTask(1, new EntityAIGoHome(this));
        this.tasks.addTask(5, new EntityAIPerformObjectiveTask(this));
        this.tasks.addTask(10, new EntityAIAssessTerrain(this));
    }

    @Override
    public boolean isAIDisabled()
    {
        return false;
    }

//    protected void initEntityAIOld()
//    {
//        this.tasks.addTask(0, new EntityAISwimming(this));
//        //this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
//        this.tasks.addTask(1, new EntityAITradePlayer(this));
//        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
//        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
//        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
//        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
//        this.tasks.addTask(6, new EntityAIVillagerMate(this));
//        this.tasks.addTask(7, new EntityAIFollowGolem(this));
//        //this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
//        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
//        this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
//        //this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
//    }

    public void addObjectiveTask(GroblinTask task) {
        objectiveTasks.add(task);
        say("I am a groblin " + name + " and I now need to" + task.toString());
    }

    public void say(String text) {
        this.addChatMessage(new TextComponentString(text));
        System.out.println("Groblin says: "+text);
    }
}


