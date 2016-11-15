package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public final class ModRituals {
    public static ArrayList<Ritual> allRituals = new ArrayList<>();

    public static Ritual ritualCreateSapling;
    public static Ritual ritualCreateGrass;
    public static Ritual ritualCreateWaterHole;
    public static Ritual ritualBasicGrowth;

    public static void createRituals() {
        ritualCreateSapling = register(new BasicSummonBlockRitual("#o;X#", Blocks.SAPLING));
        ritualCreateGrass = register(new BasicSummonBlockRitual("X###;#ooo;#ooo;#ooo", Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS)));
        ritualCreateWaterHole = register(new BasicSummonBlockRitual("###;X. ;###", Blocks.WATER));

        ritualBasicGrowth = register(new GrowthRitual("#o#;###; X "));
    }

    private static Ritual register(Ritual ritual) {
        allRituals.add(ritual);
        return ritual;

    }

    public static ValidRitual matchRitual(ArrayList<BlockPos> positions, World world) {
        if (positions.size() < 2) return null;

        for(Ritual r : allRituals) {
            ValidRitual match = r.isRitualMatch(world, positions);
            if (match != null) {
                return match;
            }
        }

        return null;
    }
}
