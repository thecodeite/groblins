package ninja.thepurple.groblins.common.entity.groblin.helpers;

import java.util.Random;

public final class GroblinNameGenerator {
    private static final char[] VOWLES = "aeiou".toCharArray();
    private static final char[] CONSONANTS = "bcdfghjklmnprstuvwxyz".toCharArray();
    private static final Random random = new Random();

    public static String generateName() {
        StringBuilder builder = new StringBuilder();
        builder.append('G');

        for(int i=0; i<rint(3,5); i++) {
            builder.append(VOWLES[random.nextInt(VOWLES.length)]);
            builder.append(CONSONANTS[random.nextInt(CONSONANTS.length)]);
        }

        return builder.toString();
    }

    private static int rint(int l, int u) {
        int bound = u-l;
        return l + random.nextInt(bound);
    }

}
