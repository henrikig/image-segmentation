package Models;

import java.util.ArrayList;

public class Vertex {

    private final int x;
    private final int y;
    private final int[] rgb;
    private boolean visited = false;
    private ArrayList<Edge> edges;

    public Vertex(int x, int y, int[] rgb) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
        this.edges = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getRgb() {
        return rgb;
    }

    public boolean isVisited() {
        return visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public void setVisited() {
        visited = true;
    }
}
