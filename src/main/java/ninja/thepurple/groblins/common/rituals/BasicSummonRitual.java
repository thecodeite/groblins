package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BasicSummonRitual extends Ritual {
    protected Block blockToSummon;

    public BasicSummonRitual(String grid, Block blockToSummon) {
        super(grid);
        this.blockToSummon = blockToSummon;

    }

    @Override
    public void activateRitual() {

    }

    @Override
    public RitualGrid isRitualMatch(World worldIn, ArrayList<BlockPos> positions) {
       return super.isRitualMatch(worldIn, positions);
    }


}
