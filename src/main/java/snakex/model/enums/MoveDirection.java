package snakex.model.enums;

public enum MoveDirection {
    UP(false),
    DOWN(false),
    LEFT(true),
    RIGHT(true);

    private final boolean horizonal;
    MoveDirection(final boolean horizonal) { this.horizonal = horizonal; }

    public static MoveDirection getOpposite(MoveDirection direction){
        switch (direction){

            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        throw new IllegalArgumentException();
    }

    public boolean isHorizonal() { return this.horizonal; }
}
