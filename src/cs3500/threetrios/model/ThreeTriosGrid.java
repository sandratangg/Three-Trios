package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a grid in the game.
 */
public class ThreeTriosGrid implements IGrid {
  private final int rows;
  private final int cols;
  private final ThreeTriosCell[][] grid;


  /**
   * Constructs a ThreeTriosGrid with the given dimensions.
   *
   * @param rows the number of rows
   * @param cols the number of columns
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

  /**
   * Helper: Initializes the grid with empty cells.
   */
  private void initializeGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new ThreeTriosCell();
      }
    }
  }

  /**
   * Helper: Places card only if valid
   *
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param card the card to place
   * @throws IllegalArgumentException if the position is invalid
   */
  public void placeCard(int row, int col, ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Need a card value!");
    }

    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid grid index");
    }

    try {
      grid[row][col].setCard(card);
    } catch (Exception e) {
      throw new IllegalStateException("Card is already placed or cell is a hole.");
    }
  }

  /**
   * Helper: Battle logic now encapsulated in the grid class itself
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
            currentPlayer.addToOwned(adjacentCard);  // Update grid ownership
            oppositePlayer.removeFromOwned(adjacentCard);  // Reflect the card change in the grid

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

  /**
   * Returns a string representation of the grid.
   *
   * @param red the red player
   * @return the string representation of the grid
   */
  public String toString(ThreeTriosPlayer red) {
    StringBuilder gridString = new StringBuilder();

    for (int row = 0; row < rows; row++) {
      gridString.append(rowToString(row, cols, red));
    }

    return gridString.toString();
  }

  /**
   * Helper: Converts a row to a string.
   */
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
   * Checks if the grid is fully occupied with no empty cells remaining.
   * A full grid signifies that no further moves can be made, potentially
   * indicating an end state for the game. This method allows for efficient
   * assessment of grid capacity, impacting gameplay decisions and flow.
   *
   * @return true if the grid has no empty cells; false if at least one cell is empty.
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

  private void placeEmptyCardCell(int row, int col) {
    grid[row][col] = new ThreeTriosCell(false);  // 'false' means it’s not a hole
  }

  private void placeHole(int row, int col) {
    grid[row][col] = new ThreeTriosCell(true);  // 'true' means it’s a hole
  }

  // For testing purposes
  // Returns card from cell at position (i, j)
  public ICard getCardFromCell(int i, int j) {
    return grid[i][j].card;
  }


  public ICell getCell(int row, int col) {
    return new ThreeTriosCell(this.grid[row][col].isHole,
            this.grid[row][col].getCard());
  }


}
