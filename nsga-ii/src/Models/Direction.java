package Models;

public enum Direction {
    E (1),
    W (2),
    N (3),
    S (4),
    NE (5),
    SE (6),
    NW (7),
    SW (8);

    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

}
