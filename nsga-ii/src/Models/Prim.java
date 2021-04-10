package Models;

import Utilities.ImageReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Prim {

    private ArrayList<Vertex> graph;
    private final Random random = new Random();

    public Prim(int[][][] imgRGB) {
        initGraph(imgRGB);
    }

    public List<Edge> createMinimumSpanningTree() {
        Set<Vertex> unvisited = new HashSet<>(graph);

        Vertex start = graph.get(random.nextInt(graph.size()));
        unvisited.remove(start);

        List<Edge> path = new ArrayList<>();
        Queue<Edge> pq = new PriorityQueue<>();

        Vertex current = start;
        while (!unvisited.isEmpty()) {
            for (Edge e : current.getEdges()) {
                if (unvisited.contains(e.getTo())) {
                    pq.add(e);
                }
            }

            Edge nextEdge = pq.remove();
            path.add(nextEdge);

            current = nextEdge.getTo();
            unvisited.remove(current);
        }

        return path;
    }

    private boolean isDisconnected() {
        for (Vertex vertex : graph) {
            if (!vertex.isVisited()) {
                return true;
            }
        }
        return false;
    }

    public void initGraph(int[][][] imgRGB) {
        int height = imgRGB.length;
        int width = imgRGB[0].length;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageReader.readImage();
        int[][][] imgRGB = ImageReader.get2DImage(image);

    }
}
