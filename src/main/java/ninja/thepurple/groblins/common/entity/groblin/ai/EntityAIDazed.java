package ninja.thepurple.groblins.common.entity.groblin.ai;

import net.minecraft.entity.ai.EntityAIBase;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

public class EntityAIDazed extends EntityAIBase {
    private EntityGroblin groblin;
    public EntityAIDazed(EntityGroblin groblin) {
    this.groblin = groblin;
    }

    @Override
    public boolean shouldExecute() {
        return groblin.wakeAt > groblin.worldObj.getWorldTime();
    }

    public void startExecuting() {
    groblin.say("I'm Dazed");
    }

    @Override
    public boolean continueExecuting() {
    return groblin.wakeAt > groblin.worldObj.getWorldTime();
}
}

