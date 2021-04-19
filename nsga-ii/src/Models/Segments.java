package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Segments {

    private final int[][] segments;
    private final int[][] genotype;
    private final int height;
    private final int width;
    private int numSegments;

    public Segments(int[][] genotype) {
        this.genotype = genotype;
        this.height = genotype.length;
        this.width = genotype[0].length;
        this.segments = new int[height][width];
        this.numSegments = 0;
    }

    public int[][] getSegments() {
        return segments;
    }

    public int getNumSegments() {
        return numSegments+1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] makeSegments() {

        ArrayList<List<Integer>> neighborhood;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (segments[y][x] == 0) {

                    neighborhood = new ArrayList<>();
                    neighborhood.add(Arrays.asList(x, y));
                    numSegments += 1;
                    segments[y][x] = numSegments;

                    numberNeighbor(x, y, neighborhood);
                }
            }
        }

        return segments;
    }

    private void numberNeighbor(int x, int y, ArrayList<List<Integer>> neighborhood) {

        if (genotype[y][x] != 0) {

            Direction d = Direction.getDirection(genotype[y][x]);

            int neighborX = x + d.getShiftX();
            int neighborY = y + d.getShiftY();

            if (segments[neighborY][neighborX] == 0) {
                segments[neighborY][neighborX] = numSegments;
                neighborhood.add(Arrays.asList(neighborX, neighborY));

                numberNeighbor(neighborX, neighborY, neighborhood);
            } else {
                int neighborSegment = segments[neighborY][neighborX];

                neighborhood.forEach(o -> segments[o.get(1)][o.get(0)] = neighborSegment);
                numSegments -= 1;
            }
        }
    }
}
