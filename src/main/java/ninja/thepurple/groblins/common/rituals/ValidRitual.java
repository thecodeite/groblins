package ninja.thepurple.groblins.common.rituals;

import net.minecraft.util.math.BlockPos;

public class ValidRitual {
    private final Ritual ritual;
    private final BlockPos keyPosition;
    private final BlockPos[] eventPositions;

    ValidRitual(Ritual ritual, BlockPos keyPosition, BlockPos[] eventPositions) {
        this.ritual = ritual;
        this.keyPosition = keyPosition;
        this.eventPositions = eventPositions;
    }

    public Ritual getRitual() {
        return ritual;
    }


    public BlockPos getKeyPosition() {
        return keyPosition;
    }

    public BlockPos[] getEventPositions() {
        return eventPositions;
    }
}
