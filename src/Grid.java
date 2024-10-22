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

  public void initializeGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell();
      }
    }
  }
}
