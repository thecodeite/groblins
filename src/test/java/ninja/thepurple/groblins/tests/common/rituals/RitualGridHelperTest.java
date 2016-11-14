package ninja.thepurple.groblins.tests.common.rituals;

import ninja.thepurple.groblins.common.rituals.RitualGrid;

import static org.junit.Assert.*;


public class RitualGridHelperTest {
    @org.junit.Test
    public void rotations() throws Exception {
        RitualGrid[] rotations = RitualGrid.rotations("##;##");

        assertEquals("##;##", rotations[0].full);
        assertEquals("##;##", rotations[1].full);
        assertEquals("##;##", rotations[2].full);
        assertEquals("##;##", rotations[3].full);
    }

    @org.junit.Test
    public void singleBlock() throws Exception {
        RitualGrid[] rotations = RitualGrid.rotations("# ;  ");

        assertEquals("# ;  ", rotations[0].full);
        assertEquals(" #;  ", rotations[1].full);
        assertEquals("  ; #", rotations[2].full);
        assertEquals("  ;# ", rotations[3].full);
    }

    @org.junit.Test
    public void oddShape() throws Exception {
        RitualGrid[] rotations = RitualGrid.rotations("###;## ");

        assertEquals("###;## ", rotations[0].full);
        assertEquals("##;##; #", rotations[1].full);
        assertEquals(" ##;###", rotations[2].full);
        assertEquals("# ;##;##", rotations[3].full);
    }

}