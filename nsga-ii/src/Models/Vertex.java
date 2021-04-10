package Models;

import java.util.ArrayList;

public class Vertex {

    private final int id;
    private final int[] rgb;
    private boolean visited = false;
    private ArrayList<Edge> edges;

    public Vertex(int id, int[] rgb) {
        this.id = id;
        this.rgb = rgb;
        this.edges = new ArrayList<>();
    }

    public int getId() {
        return id;
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

    public void setVisited() {
        visited = true;
    }
}
