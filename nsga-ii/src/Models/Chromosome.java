package Models;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Chromosome {

    private int[][] genotype;
    private List<Edge> path = null;
    private final int height;
    private final int width;
    private int segments = 0;
    private Random random;

    public Chromosome(List<Edge> path, int height, int width, int segments) {
        this.genotype = new int[height][width];
        this.height = height;
        this.width = width;
        this.segments = segments;

        initGenotype(path);
    }

    public Chromosome(Chromosome c1, Chromosome c2) {
        this.height = c1.getHeight();
        this.width = c1.getWidth();
        this.genotype = new int[height][width];
        this.random = new Random();

        crossover(c1, c2);
    }

    public int[][] getGenotype() {
        return genotype;
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

    private void initGenotype(List<Edge> path) {

        for (Edge e : path) {
            if (genotype[e.getFrom().getY()][e.getFrom().getX()] == 0) {
                genotype[e.getFrom().getY()][e.getFrom().getX()] = e.getDirection().getDirection();
            } else {
                genotype[e.getTo().getY()][e.getTo().getX()] = e.getDirection().getOpposite().getDirection();
            }
        }

        // Create a priority queue with the longest edges first
        PriorityQueue<Edge> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.addAll(path);

        // Remove the k longest edges
        for (int i = 0; i < segments - 1; i++) {
            Edge e = pq.remove();
            genotype[e.getFrom().getY()][e.getFrom().getX()] = 0;
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
