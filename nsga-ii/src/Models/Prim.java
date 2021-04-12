package Models;

import java.util.*;

public class Prim {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private final Random random;

    public Prim(Vertex[][] vertices) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.random = new Random();
        initGraph(vertices);
    }

    public List<Edge> createMinimumSpanningTree() {
        Set<Vertex> unvisited = new HashSet<>(vertices);

        Vertex start = this.vertices.get(random.nextInt(this.vertices.size()));
        unvisited.remove(start);

        List<Edge> path = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        Vertex current = start;
        while (!unvisited.isEmpty()) {

            for (Edge e : current.getEdges()) {
                if (unvisited.contains(e.getTo())) {
                    pq.add(e);
                }
            }

            Edge nextEdge = pq.remove();

            while (!unvisited.contains(nextEdge.getTo())) {
                nextEdge = pq.remove();
            }

            path.add(nextEdge);

            current = nextEdge.getTo();
            unvisited.remove(current);
        }

        Collections.sort(path);
        return path;
    }

    public void initGraph(Vertex[][] initVertices) {
        int height = initVertices.length;
        int width = initVertices[0].length;

        for (Vertex[] verticesArr : initVertices) {
            this.vertices.addAll(Arrays.asList(verticesArr));
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Vertex from = initVertices[y][x];

                for (Direction d : Direction.values()) {

                    if (0 <= x + d.getShiftX() && x + d.getShiftX() < width
                            && 0 <= y + d.getShiftY() && y + d.getShiftY() < height) {

                        Vertex to = initVertices[y + d.getShiftY()][x + d.getShiftX()];

                        this.edges.add(new Edge(from, to, d));
                    }
                }
            }
        }
    }
}
