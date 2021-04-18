package Utilities;

import Models.Chromosome;

import java.util.Comparator;

public class EdgeValueComparator implements Comparator<Chromosome> {
    @Override
    public int compare(Chromosome o1, Chromosome o2) {
        if (o1.getEdgeValue() > o2.getEdgeValue()) {
            return -1;
        } else if (o1.getEdgeValue() < o2.getEdgeValue()) {
            return 1;
        }

        return 0;
    }
}
