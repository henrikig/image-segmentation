package Models;

import java.util.Arrays;

public class Segmentation {

    public static int[][] makeSegments(int[][] genotype) {
        int height = genotype.length;
        int width = genotype[0].length;

        int[][] opposite = getOpposite(genotype, height, width);

        int[][] segments = new int[height][width];

        int yCurr = 0;

        int segmentNum = 1;

        segments[yCurr][0] = segmentNum;

        for (int y = yCurr; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (genotype[y][x] != 0) {
                    int direction = genotype[y][x];
                    Direction d = Direction.getDirection(direction);
                }
            }
        }

        return segments;
    }

    public static int[][] mergeSegments(int seg1, int seg2, int[][] segments) {
        return null;
    }

    public static int[][] getOpposite(int[][] genotype, int height, int width) {
        int[][] opposite = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (genotype[y][x] != 0) {
                    opposite[y][x] = Direction.getDirection(genotype[y][x]).getOpposite().getDirection();
                }
            }
        }

        return opposite;
    }

    public static void main(String[] args) {
        int[][] genotype = new int[10][10];

        int[][] res = makeSegments(genotype);


    }
}
