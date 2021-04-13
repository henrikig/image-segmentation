package Models;

import Utilities.Utils;

public class Edge implements Comparable<Edge>{

    private final double weight;
    private final Vertex from;
    private final Vertex to;
    private final Direction direction;
    private boolean included = false;

    public Edge(Vertex from, Vertex to, Direction d) {
        this.from = from;
        this.to = to;
        this.direction = d;
        this.weight = Utils.getEuclideanDist(from.getRgb(), to.getRgb());

        from.addEdge(this);
        to.addEdge(this);
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getWeight() {
        return weight;
    }

    public boolean getIncluded() {
        return included;
    }

    public void setIncluded() {
        included = true;
    }

    @Override
    public int compareTo(Edge o) {
        if (weight > o.weight) {
            return 1;
        } else if (weight < o.weight) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {

        return String.format("(%d, %d) -> (%d, %d): %f", from.getX() + 1, from.getY() + 1, to.getX() + 1, to.getY() + 1, weight);
    }
}
