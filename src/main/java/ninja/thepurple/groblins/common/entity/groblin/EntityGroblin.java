package ninja.thepurple.groblins.common.entity.groblin;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ninja.thepurple.groblins.GroblinsMod;
import ninja.thepurple.groblins.common.entity.groblin.activities.ActivityBreakBlock;
import ninja.thepurple.groblins.common.entity.groblin.activities.ActivityPlaceBlock;
import ninja.thepurple.groblins.common.entity.groblin.ai.*;
import ninja.thepurple.groblins.common.entity.groblin.helpers.GroblinNameGenerator;
import ninja.thepurple.groblins.common.entity.groblin.helpers.PathNavigateGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.StandingPositionHelper;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTask;
import ninja.thepurple.groblins.common.entity.groblin.tasks.GroblinTaskFixTerrain;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class EntityGroblin extends EntityCreature {
    public static final ResourceLocation LOOT = new ResourceLocation(GroblinsMod.MODID, "entity/groblin");

    //public Chunk homeChunk = null;
    public boolean hasHomeChunk = false;
    public int homeChunkX, homeChunkZ;
    public int yLevel = -1;
    public final Queue<GroblinTask> objectiveTasks = new ArrayDeque<>();
    public long wakeAt;
    private final GroblinInventory groblinInventory;
    public BlockPos chunkCorner;
    private int[] terrainMap;

    public EntityGroblin(World worldIn) {
        super(worldIn);
        this.setHealth(1);
        this.wakeAt = worldIn.getWorldTime() + 100;
        this.groblinInventory = new GroblinInventory("Groblin Inventory", false, 9);
        this.setCanPickUpLoot(true);
    }

    public Chunk getHomeChunk() {
        return worldObj.getChunkFromChunkCoords(homeChunkX, homeChunkZ);
    }


    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {

        if (this.isEntityAlive())
        {
            if (!this.worldObj.isRemote)
            {
                player.openGui(
                        GroblinsMod.instance,
                        GroblinsMod.GUI_ENUM.GROBLIN.ordinal(),
                        player.worldObj,
                        this.getEntityId(), 0, 0);

            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand, stack);
        }


    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {

        this.setCustomNameTag(GroblinNameGenerator.generateName());
        this.say("I am a groblin " + getCustomNameTag() + " and I am alive!");
        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIDazed(this));
        this.tasks.addTask(1, new EntityAIGoHome(this));
        this.tasks.addTask(2, new EntityAICollectItems(this));
        this.tasks.addTask(5, new EntityAIPerformObjectiveTask(this));
        this.tasks.addTask(10, new EntityAIAssessTerrain(this));
        // this.tasks.addTask(20, new EntityAIFlattenTerrain(this));
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

    @Override
    protected PathNavigate getNewNavigator(World worldIn) {
        return new PathNavigateGroblin(this, worldIn);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        InventoryHelper.dropInventoryItems(worldObj, this, groblinInventory);
//        InventoryBasic inventory = getGroblinInventory();
//        for (int i = 0; i < inventory.getSizeInventory(); i++) {
//            ItemStack itemstack = inventory.removeStackFromSlot(i);
//            if (itemstack != null) {
//                this.entityDropItem(itemstack, 0.0F);
//            }
//
//        }
    }

    public void addObjectiveTask(GroblinTask task) {
        objectiveTasks.add(task);
        say("I am a groblin " + getCustomNameTag() + " and I now need to" + task.toString());
    }

    public void say(String text) {
        this.addChatMessage(new TextComponentString(text));
        System.out.println("Groblin says: "+text);
    }

    @Override
    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        ItemStack itemstack = itemEntity.getEntityItem();
        Item item = itemstack.getItem();

        if (this.canGroblinPickupItem(item))
        {
            ItemStack itemstack1 = this.groblinInventory.addItem(itemstack);

            if (itemstack1 == null)
            {
                itemEntity.setDead();
            }
            else
            {
                itemstack.stackSize = itemstack1.stackSize;
            }
        }
    }

    private boolean canGroblinPickupItem(Item itemIn)
    {
        return true;
        // return itemIn == Item.getItemFromBlock(Blocks.DIRT);
    }

    public InventoryBasic getGroblinInventory() {
        return groblinInventory;
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setBoolean("HasHomeChunk", hasHomeChunk);
        if (hasHomeChunk) {
            compound.setInteger("HomeChunkX", homeChunkX);
            compound.setInteger("HomeChunkZ", homeChunkZ);
            compound.setInteger("YLevel", yLevel);
        }

        NBTTagList inventoryTagList = new NBTTagList();

        for (int i = 0; i < this.groblinInventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.groblinInventory.getStackInSlot(i);

            if (itemstack != null)
            {
                inventoryTagList.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        compound.setTag("Inventory", inventoryTagList);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        hasHomeChunk = compound.getBoolean("HasHomeChunk");

        if (hasHomeChunk) {
            homeChunkX = compound.getInteger("HomeChunkX");
            homeChunkZ = compound.getInteger("HomeChunkZ");
            yLevel = compound.getInteger("YLevel");
        }


        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));

            if (itemstack != null)
            {
                this.groblinInventory.addItem(itemstack);
            }
        }
    }

    public ActivityPlaceBlock placeBlock (BlockPos insertPos, IBlockState blockToPlace) {
        return new ActivityPlaceBlock(this, insertPos, blockToPlace);
    }

    public ActivityBreakBlock breakBlock (BlockPos pos, ItemStack tool) {
        return new ActivityBreakBlock(this, pos, tool);
    }

    public AxisAlignedBB getHomeChunkAABB() {
        if(hasHomeChunk) {
            return new AxisAlignedBB((homeChunkX*16), yLevel, (homeChunkZ*16), (homeChunkX+1)*16, yLevel + 4, (homeChunkZ+1) * 16);
        }
        return null;
    }

    public int getHomeX() {
        return homeChunkX * 16;
    }

    public int getHomeZ() {
        return homeChunkZ * 16;
    }

    public <T extends GroblinTask> T getTaskType() {
        for(GroblinTask task : this.objectiveTasks) {
            if (task instanceof GroblinTaskFixTerrain) {
                return (T) task;
            }
        }

        return null;
    }

    public int[] getTerrainMap() {
        return terrainMap;
    }

    public void setTerrainMap(int[] terrainMap) {
        this.terrainMap = terrainMap;
    }


    public int getBestToolLevel(String toolClass) {
        return this.groblinInventory.getBestToolLevel(toolClass);
    }
}



