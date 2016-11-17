package ninja.thepurple.groblins.common.entity.groblin.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class StaningPositionHelperTest {
    @Test
    public void pickSpotToAddBlockFrom() throws Exception {
        int idx = 2;

        for (int i=0; i<5; i++) {
            System.out.println((idx+i) % 8);

            if (i%4 != 0) {
                System.out.println((idx-i+8) % 8);
            }
        }
    }

}