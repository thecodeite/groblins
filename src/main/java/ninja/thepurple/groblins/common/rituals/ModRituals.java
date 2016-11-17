package ninja.thepurple.groblins.common.rituals;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public final class ModRituals {
    public static ArrayList<Ritual> allRituals = new ArrayList<>();

    public static SummonBlockRitual ritualCreateDirt;
    public static SummonBlockRitual ritualCreateSapling;
    public static SummonBlockRitual ritualCreateGrass;
    public static Ritual ritualCreateWaterHole;
    public static Ritual ritualBasicGrowth;

    public static void createRituals() {
        ritualCreateDirt = register(new SummonBlockRitual("#o;X#", Blocks.DIRT));
        //ritualCreateDirt = register(new SummonBlockRitual("####;####;#o##;###X", Blocks.DIRT));
        ritualCreateSapling = register(new SummonBlockRitual("o#X;###", Blocks.SAPLING));
        ritualCreateGrass = register(new SummonBlockRitual("X###;#ooo;#ooo;#ooo", Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS)));
        ritualCreateWaterHole = register(new SummonBlockRitual("###;X. ;###", Blocks.WATER));

        ritualBasicGrowth = register(new GrowthRitual("#o#;###; X "));
    }

    private static <R extends Ritual> R register(R ritual) {
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
