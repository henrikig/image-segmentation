package Models;

import Utilities.Utils;

import java.util.*;

public class Edge implements Comparable<Edge>{

    private final double weight;
    private final Vertex from;
    private final Vertex to;
    private boolean included = false;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
        this.weight = Utils.getEuclideanDist(from.getRgb(), to.getRgb());
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
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

    public static void main(String[] args) {



    }
}
