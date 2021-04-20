package Controller;

import Models.*;
import Utilities.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class NSGA {

    private final Vertex[][] vertexGrid;
    private final Random random;
    private final Prim prim;
    private ArrayList<Chromosome> population;
    private ArrayList<Chromosome> offspring;
    private ArrayList<Chromosome> parents;
    private ArrayList<ArrayList<Chromosome>> frontiers;
    private int height;
    private int width;

    public NSGA() throws IOException {
        this.random = new Random();
        this.population = new ArrayList<>();
        this.offspring = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.frontiers = new ArrayList<>();
        this.vertexGrid = initVertexGrid();
        this.prim = new Prim(this.vertexGrid);
        this.height = vertexGrid.length;
        this.width = vertexGrid[0].length;
    }

    public void main() throws IOException {

        initPopulation();

        population.forEach(o -> o.calculateObjectives(vertexGrid));

        nonDominatedSort();

        parents.addAll(population);

        population.clear();

        createOffspring();

        addShutdownHook();

        for (int i = 0; i < Parameters.GENERATIONS; i++) {

            System.out.printf("----------Generation %d----------%n", i);

            // Merge parents and offspring
            updatePopulation();

            // Calculate the objective values for each chromosome
            population.forEach(o -> o.calculateObjectives(vertexGrid));

            // Perform a non-dominated sort on the population to create frontiers
            nonDominatedSort();

            // P_(t+1) = Ø
            resetParents();

            // Index for iterating frontiers
            int j = 0;

            while (parents.size() + frontiers.get(j).size() < Parameters.POPULATION_SIZE) {
                // Calculate the crowding distance for each frontier which proceeds to the next population
                calculateCrowdingDistance(frontiers.get(j));

                // Add the frontier to the next population
                parents.addAll(frontiers.get(j));

                // Increment frontier index
                j++;
            }

            calculateCrowdingDistance(frontiers.get(j));

            Collections.sort(frontiers.get(j));

            int remaining = Parameters.POPULATION_SIZE - parents.size();

            parents.addAll(
                    frontiers.get(j)
                    .stream()
                    .limit(remaining)
                    .collect(Collectors.toList())
            );

            createOffspring();
        }

    }

    public Vertex[][] initVertexGrid() throws IOException {
        BufferedImage image = ImageReader.readImage();
        return ImageReader.getImageAsGraph(image);
    }

    public void initPopulation() {

        for (int i = 0; i < Parameters.POPULATION_SIZE; i++) {
            List<Edge> path = prim.createMinimumSpanningTree();
            int numSegments = Parameters.MIN_SEGMENTS + random.nextInt(Parameters.MAX_SEGMENTS - Parameters.MIN_SEGMENTS);

            population.add(new Chromosome(path, height, width, numSegments));
        }
    }

    public void createOffspring() {
        offspring.clear();

        while (offspring.size() < Parameters.POPULATION_SIZE) {
            Chromosome c1 = tournamentSelect();
            Chromosome c2 = tournamentSelect();

            Chromosome child1;
            Chromosome child2;

            if (Math.random() < Parameters.XOVER_PROB) {

                child1 = new Chromosome(c1, c2);
                child2 = new Chromosome(c2, c1);

            } else {

                child1 = new Chromosome(c1);
                child2 = new Chromosome(c2);

            }

            offspring.add(child1);
            offspring.add(child2);
        }

        offspring.forEach(o -> {
            if (Math.random() < Parameters.MUTATE_CHROMOSOME) {
                o.mutate();
            }
        });
    }

    public Chromosome tournamentSelect() {
        Chromosome c1 = parents.get(random.nextInt(parents.size()));
        Chromosome c2 = parents.get(random.nextInt(parents.size()));

        if (c1.compareTo(c2) < 0) {

            return c1;

        } else if (c1.compareTo(c2) > 0) {

            return c2;

        }

        return random.nextInt(2) == 0 ? c1 : c2;
    }

    public void updatePopulation() {
        population.clear();

        population.addAll(parents);
        population.addAll(offspring);
    }

    public void resetParents() {
        parents.clear();
    }

    public void nonDominatedSort() {

        population.forEach(Chromosome::resetDomination);

        ArrayList<Chromosome> firstFrontier = new ArrayList<>();
        frontiers.clear();
        frontiers.add(firstFrontier);

        for (Chromosome p1 : population) {
            for (Chromosome p2 : population) {

                if (p1 != p2) {

                    if (p1.dominates(p2)) {

                        p1.addDominates(p2);

                    } else if (p2.dominates(p1)) {

                        p1.increaseDominationCount();

                    }
                }
            }

            if (p1.getDominationCount() == 0) {

                firstFrontier.add(p1);
                p1.setRank(1);

            }
        }

        int i = 0;
        while (!frontiers.get(i).isEmpty()) {

            ArrayList<Chromosome> nextFrontier = new ArrayList<>();
            frontiers.add(nextFrontier);

            for (Chromosome p1 : frontiers.get(i)) {
                for (Chromosome p2 : p1.getDominates()) {
                    p2.decreaseDominationCount();

                    if (p2.getDominationCount() == 0) {

                        p2.setRank(i+2);
                        nextFrontier.add(p2);
                    }
                }
            }

            i++;
        }
    }

    public void calculateCrowdingDistance(ArrayList<Chromosome> frontier) {

        frontier.forEach(Chromosome::resetCrowdingDistance);

        EdgeValueComparator comparator1 = new EdgeValueComparator();
        ConnectivityComparator comparator2 = new ConnectivityComparator();
        DeviationComparator comparator3 = new DeviationComparator();

        calculateObjectiveDistance(frontier, comparator1, "getEdgeValue");
        calculateObjectiveDistance(frontier, comparator2, "getConnectivity");
        calculateObjectiveDistance(frontier, comparator3, "getDeviation");

    }

    private void calculateObjectiveDistance(ArrayList<Chromosome> frontier, Comparator<Chromosome> comparator, String methodName) {

        if (frontier.size() < 3) {
            frontier.forEach(o -> o.incrementCrowdingDistance(Double.POSITIVE_INFINITY));
            return;
        }

        frontier.sort(comparator);

        frontier.get(0).incrementCrowdingDistance(Double.POSITIVE_INFINITY);
        frontier.get(frontier.size()-1).incrementCrowdingDistance(Double.POSITIVE_INFINITY);

        try {
            Method getObjVal = Chromosome.class.getMethod(methodName);

            double maxVal = (double) getObjVal.invoke(frontier.get(0));
            double minVal = (double) getObjVal.invoke(frontier.get(frontier.size()-1));

            for (int i = 1; i < frontier.size() - 1; i++) {
                double dist1 = (double) getObjVal.invoke(frontier.get(i-1));
                double dist2 = (double) getObjVal.invoke(frontier.get(i+1));

                double value = (dist2 - dist1) / (minVal - maxVal);

                frontier.get(i).incrementCrowdingDistance(value);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void createFinalImages() throws IOException {

        frontiers.get(0).sort(Collections.reverseOrder());
        for (Chromosome c : frontiers.get(0)) {
            ImageWriter.writeBWImage(c, vertexGrid);
            ImageWriter.writeColorImage(c, vertexGrid);
        }
    }

    public void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                createFinalImages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
