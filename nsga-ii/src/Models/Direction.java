package Models;

public enum Direction {
    E (1, 1, 0),
    W (2, -1, 0),
    N (3, 0, -1),
    S (4, 0, 1),
    NE (5, 1, -1),
    SE (6, 1, 1),
    NW (7, -1, -1),
    SW (8, -1, 1);

    private final int direction;
    private final int xShift;
    private final int yShift;

    Direction(int direction, int xShift, int yShift) {
        this.direction = direction;
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public int getDirection() {
        return direction;
    }

    public int getShiftX() {
        return xShift;
    }

    public int getShiftY() {
        return yShift;
    }
}
