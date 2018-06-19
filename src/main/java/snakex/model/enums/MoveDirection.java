package snakex.model.enums;

public enum MoveDirection {
    UP(false),
    DOWN(false),
    LEFT(true),
    RIGHT(true);

    private final boolean horizonal;
    MoveDirection(final boolean horizonal) { this.horizonal = horizonal; }
    public boolean isHorizonal() { return this.horizonal; }
}
