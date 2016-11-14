package ninja.thepurple.groblins.common.rituals;

public class ValidRitual {
    private final Ritual ritual;
    private final RitualGrid ritualGrid;

    public ValidRitual(Ritual ritual, RitualGrid ritualGrid) {

        this.ritual = ritual;
        this.ritualGrid = ritualGrid;
    }

    public Ritual getRitual() {
        return ritual;
    }

    public RitualGrid getRitualGrid() {
        return ritualGrid;
    }
}
