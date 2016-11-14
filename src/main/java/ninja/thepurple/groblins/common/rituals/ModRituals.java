package ninja.thepurple.groblins.common.rituals;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public final class ModRituals {
    public static ArrayList<Ritual> allRituals = new ArrayList<>();

    public static Ritual ritualCreateSapling;

    public static void createRituals() {
        ritualCreateSapling = register(new BasicSummonRitual("#o;X#", Blocks.SAPLING));
    }

    private static Ritual register(Ritual ritual) {
        allRituals.add(ritual);
        return ritual;

    }

    public static ValidRitual matchRitual(ArrayList<BlockPos> positions) {
        if (positions.size() < 2) return null;

        for(Ritual r : allRituals) {
            RitualGrid match = r.isRitualMatch(null, positions);
            if (match != null) {
                return new ValidRitual(r, match);
            }
        }

        return null;
    }
}
