package cs3500.threetrios.model;

/**
 * Represents a position on a 2D grid with x (column) and y (row) coordinates.
 */
public class Posn {
  private final int x;
  private final int y;

  /**
   * Constructs a Posn object with the specified x and y coordinates.
   *
   * @param x the x-coordinate (column index)
   * @param y the y-coordinate (row index)
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate of the position.
   *
   * @return the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate of the position.
   *
   * @return the y-coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Checks if this Posn is equal to another object.
   *
   * @param obj the object to compare with
   * @return true if the given object is a Posn with the same coordinates, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Posn posn = (Posn) obj;
    return x == posn.x && y == posn.y;
  }

  /**
   * Computes the hash code for this position.
   *
   * @return the hash code of this position
   */
  @Override
  public int hashCode() {
    return 31 * x + y;
  }

  /**
   * Returns a string representation of the position.
   *
   * @return a string in the format "(x, y)"
   */
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
