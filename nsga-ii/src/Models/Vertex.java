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

    public Vertex(Vertex v, int[][] segments) {
        this.x = v.getX();
        this.y = v.getY();
        this.rgb = v.getRgb();
        this.segment = segments[y][x];

        this.isEdge = isEdge(x, y, segments);

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

    public boolean getIsEdge() {
        return isEdge;
    }

    public void setEdge(boolean edge) {
        isEdge = edge;
    }

    private boolean isEdge(int x, int y, int[][] segments) {

        for (Direction d : Direction.values()) {

            int neighborX = x + d.getShiftX();
            int neighborY = y + d.getShiftY();

            if (0 <= neighborX && neighborX < segments[0].length
                    && 0 <= neighborY && neighborY < segments.length) {

                if (segment != segments[neighborY][neighborX]) {
                    return true;
                }
            }
        }

        return false;
    }
}
