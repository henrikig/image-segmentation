package Models;

import Utilities.Parameters;
import Utilities.Utils;

import java.util.*;

public class Chromosome implements Comparable<Chromosome> {

    private int[][] genotype;
    private transient int[][] segments;
    private final int height;
    private final int width;
    private transient int numSegments;
    private final transient Random random = new Random();

    private transient double edgeValue;
    private transient double connectivity;
    private transient double deviation;
    private transient int dominationCount;
    private transient int rank;
    private ArrayList<Chromosome> dominates;
    private transient double crowdingDistance;

    private transient double maxEdgeValue;
    private transient double maxConnectivity;
    private transient double maxDeviation;

    public Chromosome(List<Edge> path, int height, int width) {
        this.genotype = new int[height][width];
        this.height = height;
        this.width = width;
        this.dominates = new ArrayList<>();

        initGenotype(path);
    }

    public Chromosome(Chromosome c) {
        this.height = c.getHeight();
        this.width = c.getWidth();
        this.dominates = new ArrayList<>();
        this.genotype = new int[height][width];

        int[][] oldGenotype = c.getGenotype();

        for (int i = 0; i < oldGenotype.length; i++) {
            System.arraycopy(oldGenotype[i], 0, this.genotype[i], 0, this.width);
        }
    }

    public Chromosome(Chromosome c1, Chromosome c2) {
        this.height = c1.getHeight();
        this.width = c1.getWidth();
        this.genotype = new int[height][width];
        this.dominates = new ArrayList<>();

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

    }

    private void crossover(Chromosome c1, Chromosome c2) {

        int yCut = random.nextInt(height);
        int xCut = random.nextInt(width);

        int[][] g1 = c1.getGenotype();
        int[][] g2 = c2.getGenotype();

        for (int y = 0; y < yCut; y++) {
            System.arraycopy(g1[y], 0, genotype[y], 0, width);
        }

        for (int y = yCut + 1; y < height; y++){
            System.arraycopy(g2[y], 0, genotype[y], 0, width);
        }

        for (int x = 0; x < width; x++) {
            if (x <= xCut) {
                genotype[yCut][x] = g1[yCut][x];
            } else {
                genotype[yCut][x] = g2[yCut][x];
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

                        if (Utils.checkDirection(d, x, y, height, width)) {

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

                if (segmentNum-1 > segmentCount.length-1) {
                    System.out.println("Hello");
                }

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

    public void mutate() {
        for (int[] row : genotype) {
            for (int i = 0; i < row.length; i++) {
                if (Math.random() < Parameters.MUTATION_PROB) {
                    row[i] = random.nextInt(9);
                }
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

    public void resetDomination() {
        dominationCount = 0;
        dominates.clear();
    }

    public void setMaxValues(double ev, double conn, double dev) {
        maxEdgeValue = ev;
        maxConnectivity = conn;
        maxDeviation = dev;
    }

    public double getWeightedSum() {
        return Parameters.W1 * deviation / maxDeviation
                + Parameters.W2 * connectivity / maxConnectivity
                - Parameters.W3 * edgeValue / maxEdgeValue;
    }

    public void increaseDominationCount() {
        dominationCount++;
    }

    public void decreaseDominationCount() {
        dominationCount--;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean dominates(Chromosome o1) {
        return edgeValue > o1.getEdgeValue() && connectivity < o1.getConnectivity() && deviation < o1.getDeviation();
    }

    public void addDominates(Chromosome o1) {
        dominates.add(o1);
    }

    public ArrayList<Chromosome> getDominates() {
        return dominates;
    }

    public void resetCrowdingDistance() {
        crowdingDistance = 0;
    }

    public void incrementCrowdingDistance(double i) {
        crowdingDistance += i;
    }

    @Override
    public int compareTo(Chromosome o) {
        if (rank < o.getRank()) {
            return -1;
        }

        if (rank == o.getRank()) {
            if (crowdingDistance > o.getCrowdingDistance()) {
                return -1;
            }

            if (this.crowdingDistance == o.getCrowdingDistance()) {
                return 0;
            }
        }

        return 1;
    }
}
