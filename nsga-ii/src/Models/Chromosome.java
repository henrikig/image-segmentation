package Models;

import Utilities.Utils;

import java.util.*;

public class Chromosome implements Comparable<Chromosome> {

    private int[][] genotype;
    private int[][] segments;
    private final int height;
    private final int width;
    private int numSegments;
    private Random random;

    private double edgeValue;
    private double connectivity;
    private double deviation;
    private int dominationCount;
    private ArrayList<Chromosome> dominates;
    private double crowdingDistance;

    public Chromosome(List<Edge> path, int height, int width, int numSegments) {
        this.genotype = new int[height][width];
        this.height = height;
        this.width = width;
        this.numSegments = numSegments;
        this.dominates = new ArrayList<>();

        initGenotype(path);
    }

    public Chromosome(Chromosome c1, Chromosome c2) {
        this.height = c1.getHeight();
        this.width = c1.getWidth();
        this.genotype = new int[height][width];
        this.random = new Random();

        crossover(c1, c2);
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
        for (int i = 0; i < numSegments - 1; i++) {
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

    public Vertex[][] createVertexGrid(Vertex[][] vertices) {
        Segments segments = new Segments(genotype);

        this.segments = segments.makeSegments();

        numSegments = segments.getNumSegments();

        Vertex[][] vertexGrid = new Vertex[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                vertexGrid[y][x] = new Vertex(vertices[y][x], this.segments);
            }
        }

        return vertexGrid;
    }

    public void calculateObjectives(Vertex[][] vertices) {

        Vertex[][] vertexGrid = createVertexGrid(vertices);

        edgeValue = 0.0;
        connectivity = 0.0;
        deviation = 0.0;

        int[] segmentCount = new int[numSegments];
        int[][] rgbSum = new int[numSegments][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Vertex current = vertexGrid[y][x];

                if (current.getIsEdge()) {
                    for (Direction d : Direction.values()) {

                        if (0 <= x + d.getShiftX() && x + d.getShiftX() < width
                                && 0 <= y + d.getShiftY() && y + d.getShiftY() < height) {

                            Vertex neighbor = vertexGrid[y + d.getShiftY()][x + d.getShiftX()];

                            if (neighbor.getSegment() != current.getSegment()) {
                                edgeValue += Utils.getEuclideanDist(current.getRgb(), neighbor.getRgb());

                                connectivity += 1.0 / d.getDirection();
                            }

                        }
                    }
                }

                int segmentNum = current.getSegment();
                int[] rgb = current.getRgb();

                segmentCount[segmentNum-1]++;

                for (int i = 0; i < 3; i++) {
                    rgbSum[segmentNum-1][i] += rgb[i] * rgb[i];
                }
            }
        }

        double[][] segmentAverage = new double[numSegments][3];

        for (int i = 0; i < numSegments; i++) {
            for (int j = 0; j < 3; j++) {
                segmentAverage[i][j] = Math.sqrt((float) rgbSum[i][j] / segmentCount[i]);
            }
        }

        calculateDeviation(segmentAverage, vertexGrid);
    }

    public void calculateDeviation(double[][] segmentAverage, Vertex[][] vertices) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vertex current =  vertices[y][x];

                int segmentNum = current.getSegment();

                deviation += Utils.getEuclideanDist(current.getRgb(), segmentAverage[segmentNum-1]);

            }
        }
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

    public int getDirection(int x, int y) {
        return genotype[y][x];
    }

    public int getDominationCount() {
        return dominationCount;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public int getNumSegments() {
        return numSegments;
    }

    public int[][] getSegments() {
        return segments;
    }

    public double getEdgeValue() {
        return edgeValue;
    }

    public double getConnectivity() {
        return connectivity;
    }

    public double getDeviation() {
        return deviation;
    }

    @Override
    public int compareTo(Chromosome o) {
        if (this.dominationCount < o.getDominationCount()) {
            return 1;
        }

        if (this.dominationCount == o.getDominationCount()) {
            if (this.crowdingDistance > o.getCrowdingDistance()) {
                return 1;
            }

            if (this.crowdingDistance == o.getCrowdingDistance()) {
                return 0;
            }
        }

        return -1;
    }
}
