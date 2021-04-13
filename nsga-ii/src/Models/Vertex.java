package Models;

import java.util.ArrayList;

public class Vertex {

    private final int x;
    private final int y;
    private final int[] rgb;
    private ArrayList<Edge> edges;
    private int segment = 0;
    private boolean isEdge = false;

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

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public void setEdge(boolean edge) {
        isEdge = edge;
    }
}
