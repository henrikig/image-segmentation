package Controller;

import Models.*;
import Utilities.ImageReader;
import Utilities.ImageWriter;
import Utilities.Parameters;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NSGA {

    private final Vertex[][] vertexGrid;
    private final Random random;
    private final Prim prim;
    private ArrayList<Chromosome> offspring;
    private ArrayList<Chromosome> population;
    private ArrayList<Chromosome> parents;
    private int height;
    private int width;

    public NSGA() throws IOException {
        this.random = new Random();
        this.offspring = new ArrayList<>();
        this.population = new ArrayList<>();
        this.offspring = new ArrayList<>();
        this.vertexGrid = initVertexGrid();
        this.prim = new Prim(this.vertexGrid);
        this.height = vertexGrid.length;
        this.width = vertexGrid[0].length;

        initPopulation();
    }

    public void main() throws IOException {

        for (int i = 0; i < Parameters.GENERATIONS; i++) {

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


    }
}
