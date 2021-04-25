package Controller;

import Models.*;
import Utilities.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GA implements GeneralSolver {

    private final Vertex[][] vertexGrid;
    private final Random random;
    private final Prim prim;
    private ArrayList<Chromosome> population;
    private ArrayList<Chromosome> parents;
    private int height;
    private int width;

    public GA() throws IOException {
        this.random = new Random();
        this.population = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.vertexGrid = initVertexGrid();
        this.prim = new Prim(this.vertexGrid);
        this.height = vertexGrid.length;
        this.width = vertexGrid[0].length;
    }

    public void main() throws IOException {

        initPopulation();

        addShutdownHook();

        for (int i = 1; i <= Parameters.GENERATIONS; i++) {

            System.out.printf("----------Generation %d----------%n", i);

            parents.addAll(population);

            population.clear();

            // Calculate objectives
            parents.forEach(o -> o.calculateObjectives(vertexGrid));

            setMaxValues(parents);

            createOffspring();

            // P_(t+1) = Ø
            resetParents();

        }

    }

    public Vertex[][] initVertexGrid() throws IOException {
        BufferedImage image = ImageReader.readImage();
        return ImageReader.getImageAsGraph(image);
    }

    public void initPopulation() {

        for (int i = 0; i < Parameters.POPULATION_SIZE; i++) {
            List<Edge> path = prim.createMinimumSpanningTree();

            population.add(new Chromosome(path, height, width));
        }
    }

    public void createOffspring() {
        population.clear();

        while (population.size() < Parameters.POPULATION_SIZE - Parameters.ELITISM) {
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

            population.add(child1);
            population.add(child2);
        }

        population.forEach(o -> {
            if (Math.random() < Parameters.MUTATE_CHROMOSOME) {
                o.mutate();
            }
        });

        elitism();
    }

    public Chromosome tournamentSelect() {
        Chromosome c1 = parents.get(random.nextInt(parents.size()));
        Chromosome c2 = parents.get(random.nextInt(parents.size()));

        if (c1.getWeightedSum() < c2.getWeightedSum()) {

            return c1;

        } else if (c1.getWeightedSum() > c2.getWeightedSum()) {

            return c2;

        }

        return random.nextInt(2) == 0 ? c1 : c2;
    }

    public void elitism() {
        parents.sort(Comparator.comparingDouble(Chromosome::getWeightedSum));

        for (int i = 0; i < Parameters.ELITISM; i++) {
            population.add(parents.get(i));
        }
    }

    public void resetParents() {
        parents.clear();
    }

    public void setMaxValues(ArrayList<Chromosome> pop) {
        double maxEdgeValue = pop.stream().max(Comparator.comparing(Chromosome::getEdgeValue)).get().getEdgeValue() + 0.001;
        double maxConnectivity = pop.stream().max(Comparator.comparing(Chromosome::getEdgeValue)).get().getEdgeValue() + 0.001;
        double maxDeviation = pop.stream().max(Comparator.comparing(Chromosome::getEdgeValue)).get().getEdgeValue() + 0.001;

        pop.forEach(o -> o.setMaxValues(maxEdgeValue, maxConnectivity, maxDeviation));

    }


    public void createFinalImages() throws IOException {

        population.forEach(o -> o.calculateObjectives(vertexGrid));

        setMaxValues(population);

        population.sort(Comparator.comparingDouble(Chromosome::getWeightedSum));

        StringBuilder solution = new StringBuilder("Edge Value - Connectivity - Deviation - Segments\n");
        Formatter fmt;


        for (int i = 0; i < 10; i++) {
            Chromosome c = population.get(i);
            fmt = new Formatter();

            solution.append(fmt.format("%.2f\t%.2f\t%.2f", c.getEdgeValue(), c.getConnectivity(), c.getDeviation())).append("\t");
            solution.append(c.getNumSegments()).append("\n");

            ImageWriter.writeBWImage(c, vertexGrid);
            ImageWriter.writeColorImage(c, vertexGrid);
        }

        Utils.writeSolution(solution.toString());

        System.out.println("Images saved.");
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
