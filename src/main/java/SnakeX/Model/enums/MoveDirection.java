package SnakeX.Model.enums;

public enum MoveDirection {
    Up(false),
    Down(false),
    Left(true),
    Right(true);

    private final boolean horizonal;
    private MoveDirection(final boolean horizonal) { this.horizonal = horizonal; }
    public boolean isHorizonal() { return this.horizonal; }
}
