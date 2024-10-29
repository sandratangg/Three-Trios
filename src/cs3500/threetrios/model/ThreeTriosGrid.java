package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents the game grid in the Three Trios game. This class manages
 * the placement and battles of cards on a grid with specified rows and
 * columns. Cells in the grid can either hold cards or be designated as
 * holes where cards cannot be placed. This class also handles turn-based
 * card placement, battle logic, and provides a string representation of
 * the grid for display.
 */
public class ThreeTriosGrid implements IGrid {

  private final int rows;
  private final int cols;
  private final ThreeTriosCell[][] grid;

  /**
   * Constructs a ThreeTriosGrid with the given dimensions, initializing
   * each cell as a playable empty cell.
   *
   * @param rows the number of rows in the grid; must be positive.
   * @param cols the number of columns in the grid; must be positive.
   * @throws IllegalArgumentException if rows or columns are non-positive.
   */
  public ThreeTriosGrid(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Grid dimensions must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new ThreeTriosCell[this.rows][this.cols];
    initializeGrid();
  }

  // Helper: Initializes the grid with empty cells.
  private void initializeGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new ThreeTriosCell();
      }
    }
  }

  public boolean placeCard(int row, int col, ICard card) {
    if (isValidPosition(row, col) && grid[row][col].isEmpty()) {
      grid[row][col].card = card;
      return true;
    }
    return false;
  }

  /**
   * Executes the battle phase for a placed card in the specified direction,
   * comparing its attack value with the adjacent cell’s card. If the placed
   * card’s attack value is higher, it captures the adjacent card, flipping
   * its ownership. A captured card then recursively engages in battle with
   * its own adjacent cells, allowing for combo battles.
   *
   * @param row the row of the placed card.
   * @param col the column of the placed card.
   * @param placedCard the {@link ICard} that has been placed.
   * @param direction the {@link Direction} in which to check for adjacent cards.
   * @param currentPlayer the player who placed the card, potentially capturing
   *                      opponent cards.
   * @param oppositePlayer the opposing player, who may lose ownership of the
   *                       adjacent card.
   */
  public void battlePhase(int row, int col, ICard placedCard, Direction direction,
                          ThreeTriosPlayer currentPlayer, ThreeTriosPlayer oppositePlayer) {
    int adjacentRow = getAdjacentRow(row, direction);
    int adjacentCol = getAdjacentCol(col, direction);

    if (isValidPosition(adjacentRow, adjacentCol)) {
      ThreeTriosCell adjacentCell = grid[adjacentRow][adjacentCol];

      if (!adjacentCell.isEmpty()) {  // Check if the cell has a card
        ICard adjacentCard = adjacentCell.card;

        // Only battle if the adjacent card belongs to the opponent
        if (!currentPlayer.owns(adjacentCard)) {
          // Compare attack values in the direction
          if (placedCard.attack(direction) > adjacentCard.attack(getOppositeDirection(direction))) {
            // Flip the card: opponent loses ownership, current player gains it
            currentPlayer.addToOwned(adjacentCard);
            oppositePlayer.removeFromOwned(adjacentCard);

            // Combo step: the flipped card now does battle with its adjacent cards
            for (Direction adjDirection : Direction.values()) {
              battlePhase(adjacentRow, adjacentCol, adjacentCard, adjDirection,
                      currentPlayer, oppositePlayer);
            }
          }
        }
      }
    }
  }

  // Returns the opposite of a given direction, used in battle comparisons.
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

  // Validates if the given position is within grid boundaries.
  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  // Returns the adjacent row based on the specified direction.
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

  // Returns the adjacent column based on the specified direction.
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

  /**
   * Provides a string representation of the grid for display, showing
   * holes as spaces, empty cells as underscores, and cells owned by the
   * red or blue player as 'R' or 'B', respectively.
   *
   * @param red the red player for determining cell ownership.
   * @return a string representation of the grid's current state.
   */
  public String toString(ThreeTriosPlayer red) {
    StringBuilder gridString = new StringBuilder();

    for (int row = 0; row < rows; row++) {
      gridString.append(rowToString(row, cols, red));
    }

    return gridString.toString();
  }

  // Converts a row in the grid to a string representation.
  private String rowToString(int row, int cols, ThreeTriosPlayer red) {
    StringBuilder rowString = new StringBuilder();

    for (int col = 0; col < cols; col++) {
      ThreeTriosCell currentCell = grid[row][col];
      if (currentCell.isHole) {
        rowString.append(" ");
      } else if (currentCell.isEmpty()) {
        rowString.append("_");
      } else {
        if (red.owns(currentCell.card)) {
          rowString.append("R");
        } else {
          rowString.append("B");
        }
      }
    }

    return rowString.toString();
  }

  /**
   * Checks if the grid is fully occupied by cards.
   *
   * @return true if there are no empty cells left; false otherwise.
   */
  public boolean isGridFull() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid[row][col].isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Constructs a ThreeTriosGrid from a file, where each character
   * represents a cell type: 'C' for a card cell, 'X' for a hole.
   * The first two values in the file specify the grid dimensions.
   *
   * @param filename the path to the file containing grid data.
   * @return a ThreeTriosGrid initialized according to the file data.
   * @throws FileNotFoundException if the specified file is not found.
   */
  public static ThreeTriosGrid fromFile(String filename) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filename));
    int rows = scanner.nextInt();
    int cols = scanner.nextInt();
    scanner.nextLine();  // Move to the next line after the dimensions

    ThreeTriosGrid grid = new ThreeTriosGrid(rows, cols);

    for (int row = 0; row < rows; row++) {
      String line = scanner.nextLine();
      for (int col = 0; col < cols; col++) {
        char cellChar = line.charAt(col);
        if (cellChar == 'C') {
          grid.placeEmptyCardCell(row, col);  // Method to initialize an empty card cell
        } else if (cellChar == 'X') {
          grid.placeHole(row, col);  // Method to mark a hole
        }
      }
    }

    return grid;
  }

  // Helper to initialize an empty card cell at a specific grid location.
  private void placeEmptyCardCell(int row, int col) {
    grid[row][col] = new ThreeTriosCell(false);  // 'false' means it’s not a hole
  }

  // Helper to mark a cell as a hole in the grid.
  private void placeHole(int row, int col) {
    grid[row][col] = new ThreeTriosCell(true);  // 'true' means it’s a hole
  }

  //boolean isHole;
  //public ICard card;
  public ICell getCell(int row, int col) {
    return new ThreeTriosCell(this.grid[row][col].isHole,
            this.grid[row][col].getCard());
  }
}
