package uet.oop.bomberman.misc;

public enum Direction {
  EAST("east"),
  WEST("west"),
  SOUTH("south"),
  NORTH("north");

  private final String text;

  Direction(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
