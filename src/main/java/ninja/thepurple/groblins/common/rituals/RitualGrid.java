package ninja.thepurple.groblins.common.rituals;

public final class RitualGrid {
    public String full;
    public String matching;

    public RitualGrid (String full) {
        this.full = full;
        this.matching = toMatching(full);
    }

    private static String toMatching(String grid) {
        return grid.replace('o', ' ').replace('X', '#');
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
