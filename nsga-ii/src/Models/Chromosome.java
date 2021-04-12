package Models;

import java.util.List;
import java.util.Random;

public class Chromosome {

    private final int[][] genotype;
    private List<Edge> path = null;
    private final int height;
    private final int width;
    private int segments = 0;
    private Random random;

    public Chromosome(List<Edge> path, int height, int width, int segments) {
        this.genotype = new int[height][width];
        this.path = path;
        this.height = height;
        this.width = width;
        this.segments = segments;

        initGenotype();
    }

    public Chromosome(Chromosome c1, Chromosome c2) {
        this.height = c1.getHeight();
        this.width = c1.getWidth();
        this.genotype = new int[height][width];
        this.random = new Random();

        crossover(c1, c2);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSegments() {
        if (segments == 0) {
            return calculateNumSegments();
        } else {
            return segments;
        }
    }

    //TODO: Calculate current number of segments from genotype
    public int calculateNumSegments() {
        return 0;
    }

    public int getDirection(int x, int y) {
        return genotype[y][x];
    }

    private void initGenotype() {
        for (int i = 1; i <= segments; i++) {
            path.remove(path.size() - 1);
        }

        for (Edge e : path) {
            genotype[e.getFrom().getY()][e.getFrom().getX()] = e.getDirection();
        }
    }

    private void crossover(Chromosome c1, Chromosome c2) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (random.nextInt(2) == 0) {
                    genotype[y][x] = c1.getDirection(x, y);
                } else {
                    genotype[y][x] = c2.getDirection(x, y);
                }
            }
        }
    }
}
