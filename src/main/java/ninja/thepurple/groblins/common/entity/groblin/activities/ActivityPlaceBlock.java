package ninja.thepurple.groblins.common.entity.groblin.activities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;
import ninja.thepurple.groblins.common.entity.groblin.helpers.WorldInteractionHelper;

public class ActivityPlaceBlock extends ActivityInteractWithWorld {

    private final IBlockState blockToPlace;

    public ActivityPlaceBlock(EntityGroblin groblin, BlockPos insertPos, IBlockState blockToPlace) {
        super(groblin, insertPos);
        this.blockToPlace = blockToPlace;
    }

    @Override
    protected String getInteractionLabel() {
        return "place block";
    }

    @Override
    protected ActivityResult interactWithWorld(BlockPos interactionPos) {
        System.out.println("I'm here, inserting at"+ interactionPos);
        boolean success = WorldInteractionHelper.tryPlaceBlock(interactionPos, blockToPlace, groblin.worldObj);
        if( success) {
            return ActivityResult.DONE;
        }else {
            System.out.println("Task failed as couldn't place block ");
            return ActivityResult.CANT_DO;
        }
    }
}
