package ninja.thepurple.groblins.common.rituals;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public final class ModRituals {
    public static ArrayList<Ritual> allRituals = new ArrayList<Ritual>();

    public static Ritual ritualCreateSapling;

    public static void createRituals() {
        ritualCreateSapling = register(new Ritual());
    }

    private static Ritual register(Ritual ritual) {
        allRituals.add(ritual);
        return ritual;

    }

    public static Ritual matchRitual(ArrayList<BlockPos> positions) {
        if (positions.size() > 5) return new Ritual();

        return null;
    }
}
