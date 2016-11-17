package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class PathNavigateGroblin extends PathNavigateGround {
    public PathNavigateGroblin(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }
}
