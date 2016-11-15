package ninja.thepurple.groblins.common.rituals;

import java.util.ArrayList;
import java.util.Arrays;

public final class RitualGrid {
    public final String full;
    public final String matching;
    public final Offset keyOffset;
    public final Offset[] eventOffsets;

    public RitualGrid (String full) {
        this.full = full;
        this.matching = toMatching(full);
        this.keyOffset = calculateKeyOffset(full);
        this.eventOffsets = calculateEventOffsets(full);
    }

    public class Offset {
        final int x, y, z;

        Offset(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Offset x="+x+"; y="+y+" z="+z;
        }
    }

    private Offset calculateKeyOffset(String fullGrid) {
        char[][] arr = gridToArray(fullGrid);

        for(int z=0; z<arr.length; z++) {
            for(int x = 0; x<arr[z].length; x++) {
                if (arr[z][x] == 'X') {
                    return new Offset(x, 0, z);
                }
            }
        }

        return new Offset(0, 0, 0);
    }

    private Offset[] calculateEventOffsets(String fullGrid) {
        char[][] arr = gridToArray(fullGrid);
        ArrayList<Offset> results = new ArrayList<>();

        for(int z=0; z<arr.length; z++) {
            for(int x = 0; x<arr[z].length; x++) {
                if (arr[z][x] == 'o') {
                    results.add(new Offset(x, 0, z));
                } else if (arr[z][x] == '.') {
                    results.add(new Offset(x, -1, z));
                }
            }
        }

        Offset[] result = results.toArray(new Offset[results.size()]);

        return result;
    }

    private static String toMatching(String grid) {
        return grid
                .replace('.', ' ')
                .replace('o', ' ')
                .replace('X', '#');
    }

    public static RitualGrid[] rotations(String grid) {
        RitualGrid[] grids = new RitualGrid[4];
        grids[0] = new RitualGrid(grid);

        char[][] arr = gridToArray(grid);
        char[][] arr90 = rotate(arr);
        grids[1] = new RitualGrid(arrayToGrid(arr90));

        char[][] arr180 = rotate(arr90);
        grids[2] = new RitualGrid(arrayToGrid(arr180));

        char[][] arr270 = rotate(arr180);
        grids[3] = new RitualGrid(arrayToGrid(arr270));

        return grids;
    }

    private static char[][] gridToArray(String grid) {
        String[] lines = grid.split(";");
        int width = lines[0].length();
        int height = lines.length;

        char[][] results = new char[height][width];
        char[] line;
        for(int i=0; i<lines.length; i++) {
            line = lines[i].toCharArray();
            for(int j=0; j<line.length; j++) {
                results[i][j] = line[j];
            }
        }

        return results;
    }

    private static String arrayToGrid(char[][] arr) {
        String[] lines = new String[arr.length];

        for(int i=0; i<lines.length; i++) {
            lines[i] = new String(arr[i]);
        }

        return String.join(";", (CharSequence[]) lines);
    }

    private static char[][] rotate(char[][] arr) {
        int width = arr.length;
        int height = arr[0].length;

        char[][] result = new char[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                result[i][j] = arr[width - j - 1][i];
            }
        }

        return result;
    }
}
