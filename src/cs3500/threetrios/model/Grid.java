package cs3500.threetrios.model;

public class Grid {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  private static class Cell {
    boolean isHole;
    Card card;

    Cell() {
      this.isHole = false;
      this.card = null;
    }

    Cell(boolean isHole) {
      this.isHole = isHole;
      this.card = null;
    }

    boolean isEmpty() {
      return !isHole && card == null;
    }
  }

  public Grid(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Grid dimensions must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[this.rows][this.cols];
    initializeGrid();
  }

  private void initializeGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell();
      }
    }
  }

  // Place card only if valid; no need for external checking logic
  public boolean placeCard(int row, int col, Card card) {
    if (isValidPosition(row, col) && grid[row][col].isEmpty()) {
      grid[row][col].card = card;
      return true;
    }
    return false;
  }

  // Battle logic now encapsulated in the grid class itself
  public void battlePhase(int row, int col, Card placedCard, Direction direction) {
    int adjacentRow = getAdjacentRow(row, direction);
    int adjacentCol = getAdjacentCol(col, direction);

    if (isValidPosition(adjacentRow, adjacentCol)) {
      Card adjacentCard = grid[adjacentRow][adjacentCol].card;
      if (adjacentCard != null) {
        if (placedCard.attack(direction) > adjacentCard.attack(getOppositeDirection(direction))) {
          // Flip the card - Ownership change logic can be handled at a higher level (Player/Model)
        }
      }
    }
  }

  private Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case EAST:
        return Direction.WEST;
      case SOUTH:
        return Direction.NORTH;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  private int getAdjacentRow(int row, Direction direction) {
    switch (direction) {
      case NORTH:
        return row - 1;
      case SOUTH:
        return row + 1;
      default:
        return row;
    }
  }

  private int getAdjacentCol(int col, Direction direction) {
    switch (direction) {
      case EAST:
        return col + 1;
      case WEST:
        return col - 1;
      default:
        return col;
    }
  }
}
